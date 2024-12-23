package com.auction.exceptions.productuserexceptions;

import com.auction.ResourceNotFoundException;

public class ProductUserNotFoundException extends ResourceNotFoundException {
  public ProductUserNotFoundException(String errorMessage) {
    super(errorMessage);
  }
}
