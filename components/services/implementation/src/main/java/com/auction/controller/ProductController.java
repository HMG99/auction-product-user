package com.auction.controller;

import com.auction.constants.RouteConstants;
import com.auction.request.ProductRequest;
import com.auction.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(RouteConstants.BASE_URL + "${product.service.version}" + RouteConstants.PRODUCTS)
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ProductRequest createProduct(@RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    @GetMapping("/{productId}")
    public ProductRequest getProductById(@PathVariable(name = "productId") UUID productId) {
        return productService.getProductById(productId);
    }

    @GetMapping
    public List<ProductRequest> getAllProducts(@RequestParam (name = "productCode", required = false) String productCode) {
        return productService.getAllProducts(productCode);
    }

    @PutMapping("/{productId}")
    public ProductRequest updateProduct(@PathVariable (name = "productId") UUID productId,
                                        @RequestBody ProductRequest productRequest) {
        return productService.updateProduct(productId, productRequest);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable (name = "productId") UUID productId) {
        productService.deleteProduct(productId);
    }

}




//@RequestMapping(method = {RequestMethod.GET}, value = {"/api/v1/users"})