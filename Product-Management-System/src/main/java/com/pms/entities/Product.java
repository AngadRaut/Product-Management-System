package com.pms.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @NotNull(message = "Product name field should not be null.")
    @NotEmpty(message = "Product name field is required.")
    @Column(name = "product_name")
    private String productName;

    @NotEmpty(message = "Brand name is required")
    @Size(max = 100, message = "Brand name cannot be longer than 100 characters")
    @Column(name = "brand_name")
    private String brandName;


    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private Double price;

    @NotEmpty(message = "Warranty details are required")
    @Size(max = 500, message = "Warranty details cannot exceed 500 characters")
    @Column(name = "warranty_details")
    private String warrantyDetails;

    private String madeIn;

    @NotNull(message = "Stock is required")
    @Min(value = 0, message = "Stock cannot be negative")
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    @JsonBackReference
    private Seller seller;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference(value = "category-product")
    private Category category;

    // no arg constructor
    public Product() {
    }
    // all arg constructor
    public Product(Long productId, String productName, String brandName, Double price, String warrantyDetails, String madeIn, Integer stock, Seller seller, Category category) {
        this.productId = productId;
        this.productName = productName;
        this.brandName = brandName;
        this.price = price;
        this.warrantyDetails = warrantyDetails;
        this.madeIn = madeIn;
        this.stock = stock;
        this.seller = seller;
        this.category = category;
    }

    // setters and getters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public @NotNull(message = "Product name field should not be null.") @NotEmpty(message = "Product name field is required.") String getProductName() {
        return productName;
    }

    public void setProductName(@NotNull(message = "Product name field should not be null.") @NotEmpty(message = "Product name field is required.") String productName) {
        this.productName = productName;
    }

    public @NotEmpty(message = "Brand name is required") @Size(max = 100, message = "Brand name cannot be longer than 100 characters") String getBrandName() {
        return brandName;
    }

    public void setBrandName(@NotEmpty(message = "Brand name is required") @Size(max = 100, message = "Brand name cannot be longer than 100 characters") String brandName) {
        this.brandName = brandName;
    }

    public @NotNull(message = "Price is required") @Positive(message = "Price must be greater than zero") Double getPrice() {
        return price;
    }

    public void setPrice(@NotNull(message = "Price is required") @Positive(message = "Price must be greater than zero") Double price) {
        this.price = price;
    }

    public @NotEmpty(message = "Warranty details are required") @Size(max = 500, message = "Warranty details cannot exceed 500 characters") String getWarrantyDetails() {
        return warrantyDetails;
    }

    public void setWarrantyDetails(@NotEmpty(message = "Warranty details are required") @Size(max = 500, message = "Warranty details cannot exceed 500 characters") String warrantyDetails) {
        this.warrantyDetails = warrantyDetails;
    }

    public String getMadeIn() {
        return madeIn;
    }

    public void setMadeIn(String madeIn) {
        this.madeIn = madeIn;
    }

    public @NotNull(message = "Stock is required") @Min(value = 0, message = "Stock cannot be negative") Integer getStock() {
        return stock;
    }

    public void setStock(@NotNull(message = "Stock is required") @Min(value = 0, message = "Stock cannot be negative") Integer stock) {
        this.stock = stock;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    // toString
    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", brandName='" + brandName + '\'' +
                ", price=" + price +
                ", warrantyDetails='" + warrantyDetails + '\'' +
                ", madeIn='" + madeIn + '\'' +
                ", stock=" + stock +
                '}';
    }
}
