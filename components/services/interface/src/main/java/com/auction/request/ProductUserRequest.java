package com.auction.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductUserRequest {

    public static final String PRODUCT_PRICE_REGEX = "^\\d{1,3}(\\.\\d{3})*[A-Z]{1,3}$";

    @Schema(hidden = true)
    private UUID productUserId;
    @Schema(description = "Product ID of product user", example = "00000000-0000-0000-0000-000000000000")
    private UUID productId;
    @Schema(hidden = true)
    private UUID userId;
    @Schema(description = "Description of product user", example = "bla... bla...")
    @NotEmpty(message = "Description cannot be null")
    private String description;
    @Schema(description = "Count of product user", example = "15")
    private int count;
    @Schema(description = "Price of product user", example = "200AMD")
    @Pattern(regexp = PRODUCT_PRICE_REGEX, message = "Wrong format of price")
    private String price;
}
