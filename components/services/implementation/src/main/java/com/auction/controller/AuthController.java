package com.auction.controller;

import com.auction.constants.RouteConstants;
import com.auction.request.LoginRequest;
import com.auction.service.AuthService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
@RequestMapping(RouteConstants.BASE_URL + RouteConstants.AUTH)
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping
    public String login(@RequestBody LoginRequest loginRequest) {
        return service.login(loginRequest);
    }

}
