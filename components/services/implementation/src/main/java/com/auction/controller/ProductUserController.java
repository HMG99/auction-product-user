package com.auction.controller;

import com.auction.ProductTableResponse;
import com.auction.constants.RouteConstants;
import com.auction.request.ProductUserRequest;
import com.auction.service.ProductUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(RouteConstants.BASE_URL + "${product.user.service.version}" + RouteConstants.PRODUCTS_USERS)
public class ProductUserController {

    @Autowired
    private ProductUserService productUserService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductUserRequest createProductUser(@RequestBody @Valid ProductUserRequest productUserRequest, Principal principal) {
        String email = principal.getName();
        return productUserService.createProductUser(productUserRequest, email);
    }

    @GetMapping
    public List<ProductUserRequest> getAllProductUsers(Principal principal) {
        String email = principal.getName();
        return productUserService.getAllProductUsers(email);
    }

    @GetMapping("/{productUserId}")
    public ProductUserRequest getProductuserById(@PathVariable(name = "productUserId") UUID productUserId) {
        return productUserService.getProductUserById(productUserId);
    }

    @PutMapping("/{productUserId}")
    public ProductUserRequest updateProductuser(@PathVariable(name = "productUserId") UUID productUserId,
                                                @RequestBody ProductUserRequest productUserRequest) {
        return productUserService.updateProductUser(productUserId, productUserRequest);
    }

    @DeleteMapping("/{productUserId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductUser(@PathVariable(name = "productUserId") UUID productUserId) {
        productUserService.deleteProductUser(productUserId);
    }

    @GetMapping("/table")
    public List<ProductTableResponse> getTable() {
        return productUserService.getTable();
    }

}
