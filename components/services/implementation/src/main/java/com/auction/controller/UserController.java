package com.auction.controller;

import com.auction.ErrorDetails;
import com.auction.constants.RouteConstants;
import com.auction.request.UserRequest;
import com.auction.security.RequiredAdminUser;
import com.auction.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(RouteConstants.BASE_URL + "${user.service.version}" + RouteConstants.USERS)
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    @RequiredAdminUser
    public UserRequest getUserById(@PathVariable UUID id) {
        return userService.getUserById(id);
    }

    @GetMapping
    public List<UserRequest> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Create user with specified parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserRequest.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid request to sent endpoint", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
            }),
            @ApiResponse(responseCode = "409", description = "User already exists", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserRequest.class))
            }),
            @ApiResponse(responseCode = "500", description = "Error occurred while updating user", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserRequest.class))
            }),

    })
    @PostMapping
    public UserRequest createUser(@RequestBody @Valid UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @PutMapping("/{productId}")
    public UserRequest updateUser(@PathVariable (name = "productId") UUID productId, @RequestBody UserRequest userRequest) {
        return userService.updateUser(productId, userRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable (name = "id") UUID id) {
        userService.deleteUser(id);
    }

}