package com.auction.exceptions.userexceptions;

import com.auction.ResourceNotFoundException;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
