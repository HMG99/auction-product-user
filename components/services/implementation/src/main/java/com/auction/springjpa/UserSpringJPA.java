package com.auction.springjpa;

import com.auction.entity.UserEntity;
import com.auction.enums.Role;
import com.auction.enums.Status;
import com.auction.exceptions.userexceptions.UserAlreadyExistException;
import com.auction.exceptions.userexceptions.UserApiException;
import com.auction.exceptions.userexceptions.UserBadRequestException;
import com.auction.exceptions.userexceptions.UserNotFoundException;
import com.auction.helper.EmailSender;
import com.auction.helper.UserCodeGenerator;
import com.auction.repository.UserRepository;
import com.auction.request.UserRequest;
import com.auction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.auction.request.UserRequest.USER_YEAR_REGEX_MSG;

@Service
public class UserSpringJPA implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserRequest getUserById(UUID id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with given ID"));
        UserRequest userRequest = userEntity.toUserRequest();
        hideFields(userRequest);
        return userRequest;
    }

    @Override
    public List<UserRequest> getAllUsers() {
        try {
            return userRepository.findAll()
                    .stream()
                    .map(UserEntity::toUserRequest)
                    .peek(this::hideFields)
                    .toList();
        } catch (Exception e) {
            throw new UserApiException("Problem during getting users", e);
        }
    }

    @Override
    public UserRequest createUser(UserRequest userRequest) {
        if (userRequest.getId() != null) {
            throw new UserBadRequestException("User ID must be null");
        }
        String password = userRequest.getPassword();
        validateFields(userRequest);
        validatePassword(password);
        validateDuplicate(null, userRequest.getEmail());
        userRequest.setPassword(passwordEncoder.encode(password));


        UserEntity userEntity = new UserEntity(userRequest);
        String verifyCode = UserCodeGenerator.generateVerifyCode();
        userEntity.setVerifyCode(verifyCode);
        userEntity.setRole(Role.USER);
        userEntity.setStatus(Status.INACTIVE);

        try {
            userRepository.save(userEntity);
            emailSender.send(userEntity.getEmail(), "", "Verification code: " + verifyCode);
        } catch (Exception e) {
            throw new UserApiException("Problem during creating user", e);
        }
        UserRequest user = userEntity.toUserRequest();
        hideFields(user);
        return user;
    }

    @Override
    public UserRequest updateUser(UUID id, UserRequest userRequest) {

        UserEntity userEntity = userRepository
                .findById(id).orElseThrow(() -> new UserNotFoundException("User not found with given ID"));
        validateFields(userRequest);
        validatePassword(userRequest.getPassword());
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        validateDuplicate(id, userRequest.getEmail());

        userRequest.setRole(userEntity.getRole());
        userRequest.setStatus(userEntity.getStatus());
        userRequest.setVerifyCode(userEntity.getVerifyCode());
        userRequest.setResetToken(userEntity.getResetToken());
        userRequest.setId(id);
        try {
            userEntity = userRepository.save(new UserEntity(userRequest));
            userRequest = userEntity.toUserRequest();
            hideFields(userRequest);
        } catch (Exception e) {
            throw new UserApiException("Problem during user update process. ", e);
        }
        return userRequest;
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with given ID"));
        try {
            userRepository.deleteById(id);
        }catch (Exception e) {
            throw new UserApiException("Problem during deleting user", e);
        }
    }

    private void validateFields(UserRequest userRequest) {
        if (userRequest.getRole() != null || userRequest.getStatus() != null ||
                userRequest.getVerifyCode() != null || userRequest.getResetToken() != null) {
            throw new UserBadRequestException("Unsupported fields are provided");
        }
        if (userRequest.getYear() < 1945 || userRequest.getYear() > 2006) {
            throw new UserBadRequestException(USER_YEAR_REGEX_MSG);
        }
    }

    private void validatePassword(String password) {
        if (password.length() < 8) {
            throw new UserBadRequestException("Password must be 8 characters long");
        }
        int upperCount = 0;
        int digitCount = 0;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) {
                upperCount++;
            }
            if (Character.isDigit(c)) {
                digitCount++;
            }
        }
        if (upperCount < 1 || digitCount < 1) {
            throw new UserBadRequestException("Password must contain at least 1 digit and 1 uppercase");
        }
    }

    private void validateDuplicate(UUID id, String email) {
        UserEntity userEntity;
        try {
            if (id == null) {
                userEntity = userRepository.getByEmail(email);
            } else {
                userEntity = userRepository.getByEmailAndIdNot(email, id);
            }
        } catch (Exception e) {
            throw new UserApiException("Problem during getting email process");
        }
        if (userEntity != null) {
            throw new UserAlreadyExistException("User with given email already exists");
        }
    }

    private void hideFields(UserRequest userRequest) {
        userRequest.setStatus(null);
        userRequest.setVerifyCode(null);
        userRequest.setResetToken(null);
        userRequest.setPassword(null);
    }

}
