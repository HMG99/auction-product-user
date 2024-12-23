package com.auction.springjpa;

import com.auction.entity.ProductEntity;
import com.auction.exceptions.productexceptions.ProductAlreadyExistException;
import com.auction.exceptions.productexceptions.ProductApiException;
import com.auction.exceptions.productexceptions.ProductBadRequestException;
import com.auction.exceptions.productexceptions.ProductNotFoundException;
import com.auction.repository.ProductRepository;
import com.auction.request.ProductRequest;
import com.auction.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductSpringJPA implements ProductService {

    private static final String PRODUCT_CODE_PREFIX = "0000";

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductRequest createProduct(ProductRequest productRequest) {
        ProductEntity existingProduct;
        if (productRequest.getProductId() != null) {
            throw new ProductBadRequestException("Product ID must be null");
        }
        String productCode = generateProductCode();

        try {
            existingProduct = productRepository
                    .getByProductNameAndCategory(productRequest.getProductName(),
                            productRequest.getCategory());
        } catch (Exception e) {
            throw new ProductApiException("Problem during creating code", e);
        }

        if (existingProduct != null) {
            throw new ProductAlreadyExistException("Product already exist with given name and category");
        }
        try {
            return productRepository
                    .save(new ProductEntity(null, productCode,
                            productRequest.getProductName(), productRequest.getCategory()))
                    .toProductRequest();
        } catch (Exception e) {
            throw new ProductApiException("Problem during creating code", e);
        }
    }

    @Override
    public ProductRequest getProductById(UUID productId) {
        ProductEntity productEntity = productRepository.findById(productId).orElseThrow(() ->
                new ProductNotFoundException("Product not found with given ID"));
        return productEntity.toProductRequest();
    }

    @Override
    public List<ProductRequest> getAllProducts(String productCode) {
        List<ProductEntity> productEntities;
        try {
            if (productCode == null) {
                productEntities = productRepository.findAll();

            } else {
               productEntities = productRepository.getByProductCode(productCode);
            }
        } catch (Exception e) {
            throw new ProductApiException("Problem during getting products.", e);
        }
        return productEntities.stream()
                .map(ProductEntity::toProductRequest)
                .toList();
    }

    @Override
    public ProductRequest updateProduct(UUID productId, ProductRequest productRequest) {
        ProductEntity productEntity = productRepository.findById(productId).orElseThrow(() ->
                new ProductNotFoundException("Product not found with given ID"));
        ProductEntity existingProductEntity = null;
        try {
            existingProductEntity = productRepository.getByProductNameAndCategoryAndProductIdNot(
                    productRequest.getProductName(), productRequest.getCategory(), productId);
        } catch (Exception e) {
            throw new ProductApiException("Problem during updating product", e);
        }
        if (existingProductEntity != null) {
            throw new ProductAlreadyExistException("Product with given name and category already exists");
        }
        productEntity.setProductName(productRequest.getProductName());
        productEntity.setCategory(productRequest.getCategory());
        try {
            return productRepository.save(productEntity).toProductRequest();
        } catch (Exception e) {
            throw new ProductApiException("Problem during updating product", e);
        }

    }

    @Override
    public void deleteProduct(UUID productId) {
        productRepository.findById(productId).orElseThrow(() ->
                new ProductNotFoundException("Product not found with given ID"));
        productRepository.deleteById(productId);
    }

    private String generateProductCode() {
        try {
            List<ProductEntity> productEntities = productRepository.findAll();
            if (productEntities.isEmpty()) {
                return PRODUCT_CODE_PREFIX + 1;
            }
            String productCode = productEntities.get(productEntities.size() - 1).getProductCode();
            String newProductCode = PRODUCT_CODE_PREFIX + (Integer.parseInt(productCode) + 1);
            return newProductCode.substring(newProductCode.length() - 5);
        } catch (Exception e) {
            throw new ProductApiException("Problem during generating product code", e);
        }
    }


}
