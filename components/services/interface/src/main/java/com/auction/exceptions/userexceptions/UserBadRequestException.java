package com.auction.exceptions.userexceptions;

import com.auction.BadRequestException;

public class UserBadRequestException extends BadRequestException {
  public UserBadRequestException(String errorMessage) {
    super(errorMessage);
  }
}
