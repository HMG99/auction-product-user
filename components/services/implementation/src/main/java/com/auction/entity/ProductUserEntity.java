package com.auction.entity;

import com.auction.constants.DataBaseConstants;
import com.auction.request.ProductUserRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = DataBaseConstants.SCHEME_NAME, name = DataBaseConstants.PRODUCT_USER_TABLE_NAME)
public class ProductUserEntity {
    @Id
    @Column(name = "product_user_id")
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID productUserId;
    @Column(name = "product_id")
    private UUID productId;
    @Column(name = "user_id")
    private UUID userId;
    private String description;
    private int count;
    private String price;

    public ProductUserEntity(ProductUserRequest request) {
        productUserId = request.getProductUserId();
        productId = request.getProductId();
        userId = request.getUserId();
        description = request.getDescription();
        count = request.getCount() <= 0 ? 1 : request.getCount();
        price = request.getPrice();
    }

    public ProductUserRequest toProductUserRequest() {
        return new ProductUserRequest(productUserId, productId, userId, description, count, price);
    }

}
