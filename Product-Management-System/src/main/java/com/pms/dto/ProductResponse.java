package com.pms.dto;

import com.pms.entities.Product;
import com.pms.models.ProductDetails;

public class ProductResponse {
    private Product product;
    private ProductDetails productDetails;
    //no arg constructor
    public ProductResponse() {
    }
    // all arg constructor
    public ProductResponse(Product product, ProductDetails productDetails) {
        this.product = product;
        this.productDetails = productDetails;
    }
    // getters and setters
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductDetails getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(ProductDetails productDetails) {
        this.productDetails = productDetails;
    }
    //toString
    @Override
    public String toString() {
        return "{" +
                "product=" + product +"\n"+
                ", productDetails=" + productDetails +
                '}'+"\n\n";
    }
}
