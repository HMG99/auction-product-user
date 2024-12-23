package com.auction.security;

import com.auction.ForbiddenException;

public class SecurityForbiddenException extends ForbiddenException {
    public SecurityForbiddenException(String errorMessage) {
        super(errorMessage);
    }
}
