package com.auction.springjpa;

import com.auction.ProductTableResponse;
import com.auction.entity.ProductEntity;
import com.auction.entity.ProductUserEntity;
import com.auction.entity.UserEntity;
import com.auction.exceptions.productuserexceptions.ProductUserApiException;
import com.auction.exceptions.productuserexceptions.ProductUserBadRequestException;
import com.auction.exceptions.productuserexceptions.ProductUserNotFoundException;
import com.auction.repository.ProductRepository;
import com.auction.repository.ProductUserRepository;
import com.auction.repository.UserRepository;
import com.auction.request.ProductUserRequest;
import com.auction.service.ProductUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductUserSpringJPA implements ProductUserService {

    @Autowired
    private ProductUserRepository productUserRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductUserRequest createProductUser(ProductUserRequest productUserRequest, String email) {
        Optional<ProductEntity> productEntity;
        UserEntity userEntity;
        if(productUserRequest.getProductUserId() != null) {
            throw new ProductUserBadRequestException("Product User ID must be null");
        }
        if(productUserRequest.getProductId() == null) {
            throw new ProductUserBadRequestException("Product ID can not be null");
        }
        try {
            userEntity = userRepository.getByEmail(email);
            productEntity = productRepository.findById(productUserRequest.getProductId());
        }catch (Exception e) {
            throw new ProductUserApiException("Problem during creating product user", e);
        }
        if(productEntity.isEmpty()) {
            throw new ProductUserNotFoundException("Product not found with given ID");
        }


        productUserRequest.setDescription(productUserRequest.getDescription().trim());
        productUserRequest.setUserId(userEntity.getId());
        try {
            return productUserRepository
                    .save(new ProductUserEntity(productUserRequest)).toProductUserRequest();
        }catch (Exception e) {
            throw new ProductUserApiException("Problem during creating product user", e);
        }
    }

    @Override
    public List<ProductUserRequest> getAllProductUsers(String email) {
        try {
            UserEntity userEntity = userRepository.getByEmail(email);
            return productUserRepository.getAllByUserId(userEntity.getId())
                    .stream()
                    .map(ProductUserEntity::toProductUserRequest)
                    .toList();
        }catch (Exception e) {
            throw new ProductUserApiException("Problem during getting all product users", e);
        }
    }

    @Override
    public ProductUserRequest getProductUserById(UUID productUserId) {
        return productUserRepository.findById(productUserId)
                .orElseThrow(() -> new ProductUserNotFoundException("ProductUser not found with given ID"))
                .toProductUserRequest();
    }

    @Override
    public ProductUserRequest updateProductUser(UUID id, ProductUserRequest productUserRequest) {
        ProductUserEntity productUserEntity = productUserRepository.findById(id)
                .orElseThrow(() -> new ProductUserNotFoundException("ProductUser not found with given ID"));

        productRepository.findById(productUserRequest.getProductId())
                .orElseThrow(() -> new ProductUserNotFoundException("Product not found with given Product ID"));

        productUserRequest.setDescription(productUserRequest.getDescription().trim());
        productUserRequest.setUserId(productUserEntity.getUserId());
        productUserRequest.setProductUserId(id);
        try {
            return productUserRepository.save(new ProductUserEntity(productUserRequest)).toProductUserRequest();
        }catch (Exception e) {
            throw new ProductUserApiException("Problem during updating Product user", e);
        }
    }

    @Override
    public void deleteProductUser(UUID productUserId) {
        productUserRepository.findById(productUserId)
                .orElseThrow(() -> new ProductUserNotFoundException("Product User not found with given ID"));
        productUserRepository.deleteById(productUserId);
    }

    @Override
    public List<ProductTableResponse> getTable() {
        return productUserRepository.getProductTable();
    }
}
