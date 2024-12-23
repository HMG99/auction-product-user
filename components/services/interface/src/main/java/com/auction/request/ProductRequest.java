package com.auction.request;

import com.auction.enums.ProductCategory;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductRequest {

    @Schema(hidden = true)
    private UUID productId;
    @Schema(example = "00012", description = "Code for specific product", hidden = true)
    private String productCode;
    @Schema(example = "Toyota", description = "Name for specific product")
    private String productName;
    @Schema(example = "CAR", description = "Category for product")
    private ProductCategory category;
}
