package com.auction.exceptions.productexceptions;

import com.auction.ResourceAlreadyExistException;

public class ProductAlreadyExistException extends ResourceAlreadyExistException {
    public ProductAlreadyExistException(String errorMessage) {
        super(errorMessage);
    }
}
