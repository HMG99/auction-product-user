package com.auction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductTableResponse {
    private String nameOfUser;
    private String nameOfProduct;
    private int count;
    private String price;
}
