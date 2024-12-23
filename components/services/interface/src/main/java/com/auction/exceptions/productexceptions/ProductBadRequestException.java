package com.auction.exceptions.productexceptions;

import com.auction.BadRequestException;

public class ProductBadRequestException extends BadRequestException {
    public ProductBadRequestException(String errorMessage) {
        super(errorMessage);
    }
}
