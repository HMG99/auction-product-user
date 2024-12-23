package com.auction.entity;

import com.auction.constants.DataBaseConstants;
import com.auction.enums.Role;
import com.auction.enums.Status;
import com.auction.request.UserRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = DataBaseConstants.SCHEME_NAME, name = DataBaseConstants.USER_TABLE_NAME)
public class UserEntity {
    @Id
    @Column(name = "user_id")
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;
    @Column(name = "first_name")
    private String name;
    @Column(name = "last_name")
    private String surname;
    private Integer year;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "verification_code")
    private String verifyCode;
    @Column(name = "reset_token")
    private String resetToken;

    public UserEntity(UserRequest userRequest) {
        id = userRequest.getId();
        name = userRequest.getName();
        surname = userRequest.getSurname();
        year = userRequest.getYear();
        email = userRequest.getEmail();
        password = userRequest.getPassword();
        role = userRequest.getRole();
        status = userRequest.getStatus();
        verifyCode = userRequest.getVerifyCode();
        resetToken = userRequest.getResetToken();
    }

    public UserRequest toUserRequest() {
        return new UserRequest(id, name, surname, year, email, password, role, status, verifyCode, resetToken);
    }

}
