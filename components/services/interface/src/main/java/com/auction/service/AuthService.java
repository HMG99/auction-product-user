package com.auction.service;

import com.auction.request.LoginRequest;

public interface AuthService {
    String login(LoginRequest loginRequest);
}
