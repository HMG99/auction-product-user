package com.auction.exceptions.productexceptions;

import com.auction.ApiException;

public class ProductApiException extends ApiException {
  public ProductApiException(String errorMessage) {
    super(errorMessage);
  }

  public ProductApiException(String errorMessage, Throwable throwable) {
    super(errorMessage, throwable);
  }
}
