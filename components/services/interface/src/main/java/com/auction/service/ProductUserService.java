package com.auction.service;

import com.auction.ProductTableResponse;
import com.auction.request.ProductUserRequest;

import java.util.List;
import java.util.UUID;

public interface ProductUserService {
    ProductUserRequest createProductUser(ProductUserRequest productUserRequest, String email);
    List<ProductUserRequest> getAllProductUsers(String email);
    ProductUserRequest getProductUserById(UUID productUserId);
    ProductUserRequest updateProductUser(UUID id, ProductUserRequest productUserRequest);
    void deleteProductUser(UUID productUserId);

    List<ProductTableResponse> getTable();
}
