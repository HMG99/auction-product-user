package com.auction.exceptions.userexceptions;

import com.auction.ResourceAlreadyExistException;

public class UserAlreadyExistException extends ResourceAlreadyExistException {
    public UserAlreadyExistException(String errorMessage) {
        super(errorMessage);
    }
}
