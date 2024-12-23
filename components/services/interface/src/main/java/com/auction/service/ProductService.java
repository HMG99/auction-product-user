package com.auction.service;

import com.auction.request.ProductRequest;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductRequest createProduct(ProductRequest productRequest);
    ProductRequest getProductById(UUID productId);
    List<ProductRequest> getAllProducts(String productCode);
    ProductRequest updateProduct(UUID productId, ProductRequest productRequest);
    void deleteProduct(UUID productId);
}
