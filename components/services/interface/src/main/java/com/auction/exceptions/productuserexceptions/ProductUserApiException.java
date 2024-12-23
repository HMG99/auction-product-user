package com.auction.exceptions.productuserexceptions;

import com.auction.ApiException;

public class ProductUserApiException extends ApiException {
    public ProductUserApiException(String errorMessage) {
        super(errorMessage);
    }

    public ProductUserApiException(String errorMessage, Throwable throwable) {
        super(errorMessage, throwable);
    }
}
