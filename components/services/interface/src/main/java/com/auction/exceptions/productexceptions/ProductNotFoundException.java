package com.auction.exceptions.productexceptions;

import com.auction.ResourceNotFoundException;

public class ProductNotFoundException extends ResourceNotFoundException {
    public ProductNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
