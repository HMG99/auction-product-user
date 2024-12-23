package com.auction.service;

import com.auction.request.UserRequest;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserRequest getUserById(UUID id);
    List<UserRequest> getAllUsers();
    UserRequest createUser(UserRequest userRequest);
    UserRequest updateUser(UUID id, UserRequest userRequest);
    void deleteUser(UUID id);
}
