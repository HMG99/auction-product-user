package com.auction.repository;

import com.auction.ProductTableResponse;
import com.auction.entity.ProductUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductUserRepository extends JpaRepository<ProductUserEntity, UUID> {

    List<ProductUserEntity> getAllByUserId(UUID userId);

    @Query("SELECT new com.auction.ProductTableResponse(" +
            "   u.name, " +
            "   p.productName, " +
            "   pu.count, " +
            "   pu.price) " +
            "FROM ProductUserEntity pu " +
            "JOIN ProductEntity p ON p.productId = pu.productId " +
            "JOIN UserEntity u ON u.id = pu.userId")
    List<ProductTableResponse> getProductTable();

    @Query(nativeQuery = true, value = "select COUNT(product_user_id) from product_user")
    int getCount();

}