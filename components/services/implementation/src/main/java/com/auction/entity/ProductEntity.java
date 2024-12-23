package com.auction.entity;

import com.auction.constants.DataBaseConstants;
import com.auction.enums.ProductCategory;
import com.auction.request.ProductRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = DataBaseConstants.SCHEME_NAME, name = DataBaseConstants.PRODUCT_TABLE_NAME)
public class ProductEntity {
    @Id
    @Column(name = "product_id")
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID productId;
    @Column(name = "product_code")
    private String productCode;
    @Column(name = "product_name")
    private String productName;
    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    public ProductRequest toProductRequest() {
        ProductRequest request = new ProductRequest();
        request.setProductId(productId);
        request.setProductCode(productCode);
        request.setProductName(productName);
        request.setCategory(category);
        return request;
    }

    public ProductEntity(ProductRequest productRequest) {
        productId = productRequest.getProductId();
        productCode = productRequest.getProductCode();
        productName = productRequest.getProductName();
        category = productRequest.getCategory();
    }

}
