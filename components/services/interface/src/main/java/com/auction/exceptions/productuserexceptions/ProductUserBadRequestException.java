package com.auction.exceptions.productuserexceptions;

import com.auction.BadRequestException;

public class ProductUserBadRequestException extends BadRequestException {
    public ProductUserBadRequestException(String errorMessage) {
        super(errorMessage);
    }
}
