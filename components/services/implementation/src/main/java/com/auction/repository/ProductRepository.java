package com.auction.repository;

import com.auction.entity.ProductEntity;
import com.auction.enums.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {

    ProductEntity getByProductNameAndCategory(String name, ProductCategory category);

    ProductEntity getByProductNameAndCategoryAndProductIdNot(String name, ProductCategory category, UUID id);

    List<ProductEntity> getByProductCode(String productCode);

}
