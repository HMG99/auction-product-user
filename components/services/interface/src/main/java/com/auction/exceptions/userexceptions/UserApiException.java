package com.auction.exceptions.userexceptions;

import com.auction.ApiException;

public class UserApiException extends ApiException {
    public UserApiException(String errorMessage) {
        super(errorMessage);
    }
    public UserApiException(String errorMessage, Throwable throwable) {
        super(errorMessage, throwable);
    }
}
