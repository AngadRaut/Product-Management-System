package com.pms.models;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Document(collection = "product_details")
public class ProductDetails {

    @Id
    @NotNull(message = "Product details ID cannot be null.")
    private String productDetailsId;

    @NotNull(message = "Product ID cannot be null.")
    @Positive(message = "Product ID must be a positive number.")
    private Long productId;

    @NotEmpty(message = "Description is required.")
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description; // Text describing the product

    private Byte[] images;

    @NotNull(message = "Specifications cannot be null.")
    @NotEmpty(message = "Specifications cannot be empty.")
    private Map<String, String> specifications; // Key-value pairs like "Color: Red", "Weight: 1kg"

    @NotEmpty(message = "Usage instructions are required.")
    @Size(max = 500, message = "Usage instructions cannot exceed 500 characters.")
    private String usageInstructions; // Instructions for using the product

    private List<Map<String, String>> customerFAQ; // List of FAQs with question and answer

    @NotEmpty(message = "Material type is required.")
    private String materialType;

    @NotEmpty(message = "Warranty information is required.")
    private String warrantyInfo;

    private String countryOfOrigin;

    @NotEmpty(message = "Sizes are required.")
    private List<String> sizes;

    private List<String> highlights;

    @NotNull(message = "Features cannot be null.")
    @NotEmpty(message = "Features cannot be empty.")
    private List<String> features;

    @NotNull(message = "Quantity cannot be null.")
    @Positive(message = "Quantity must be a positive number.")
    private Integer quantity;

    // no arg constructor
    public ProductDetails() {
    }
    // all arg constructor
    public ProductDetails(String productDetailsId, Long productId, String description, Byte[] images, Map<String, String> specifications, String usageInstructions, List<Map<String, String>> customerFAQ, String materialType, String warrantyInfo, String countryOfOrigin, List<String> sizes, List<String> highlights, List<String> features, Integer quantity) {
        this.productDetailsId = productDetailsId;
        this.productId = productId;
        this.description = description;
        this.images = images;
        this.specifications = specifications;
        this.usageInstructions = usageInstructions;
        this.customerFAQ = customerFAQ;
        this.materialType = materialType;
        this.warrantyInfo = warrantyInfo;
        this.countryOfOrigin = countryOfOrigin;
        this.sizes = sizes;
        this.highlights = highlights;
        this.features = features;
        this.quantity = quantity;
    }
    // setters and getters

    public @NotNull(message = "Product details ID cannot be null.") String getProductDetailsId() {
        return productDetailsId;
    }

    public void setProductDetailsId(@NotNull(message = "Product details ID cannot be null.") String productDetailsId) {
        this.productDetailsId = productDetailsId;
    }

    public @NotNull(message = "Product ID cannot be null.") @Positive(message = "Product ID must be a positive number.") Long getProductId() {
        return productId;
    }

    public void setProductId(@NotNull(message = "Product ID cannot be null.") @Positive(message = "Product ID must be a positive number.") Long productId) {
        this.productId = productId;
    }

    public @NotEmpty(message = "Description is required.") @Size(max = 1000, message = "Description cannot exceed 1000 characters") String getDescription() {
        return description;
    }

    public void setDescription(@NotEmpty(message = "Description is required.") @Size(max = 1000, message = "Description cannot exceed 1000 characters") String description) {
        this.description = description;
    }

    public Byte[] getImages() {
        return images;
    }

    public void setImages(Byte[] images) {
        this.images = images;
    }

    public @NotNull(message = "Specifications cannot be null.") @NotEmpty(message = "Specifications cannot be empty.") Map<String, String> getSpecifications() {
        return specifications;
    }

    public void setSpecifications(@NotNull(message = "Specifications cannot be null.") @NotEmpty(message = "Specifications cannot be empty.") Map<String, String> specifications) {
        this.specifications = specifications;
    }

    public @NotEmpty(message = "Usage instructions are required.") @Size(max = 500, message = "Usage instructions cannot exceed 500 characters.") String getUsageInstructions() {
        return usageInstructions;
    }

    public void setUsageInstructions(@NotEmpty(message = "Usage instructions are required.") @Size(max = 500, message = "Usage instructions cannot exceed 500 characters.") String usageInstructions) {
        this.usageInstructions = usageInstructions;
    }

    public List<Map<String, String>> getCustomerFAQ() {
        return customerFAQ;
    }

    public void setCustomerFAQ(List<Map<String, String>> customerFAQ) {
        this.customerFAQ = customerFAQ;
    }

    public @NotEmpty(message = "Material type is required.") String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(@NotEmpty(message = "Material type is required.") String materialType) {
        this.materialType = materialType;
    }

    public @NotEmpty(message = "Warranty information is required.") String getWarrantyInfo() {
        return warrantyInfo;
    }

    public void setWarrantyInfo(@NotEmpty(message = "Warranty information is required.") String warrantyInfo) {
        this.warrantyInfo = warrantyInfo;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public @NotEmpty(message = "Sizes are required.") List<String> getSizes() {
        return sizes;
    }

    public void setSizes(@NotEmpty(message = "Sizes are required.") List<String> sizes) {
        this.sizes = sizes;
    }

    public List<String> getHighlights() {
        return highlights;
    }

    public void setHighlights(List<String> highlights) {
        this.highlights = highlights;
    }

    public @NotNull(message = "Features cannot be null.") @NotEmpty(message = "Features cannot be empty.") List<String> getFeatures() {
        return features;
    }

    public void setFeatures(@NotNull(message = "Features cannot be null.") @NotEmpty(message = "Features cannot be empty.") List<String> features) {
        this.features = features;
    }

    public @NotNull(message = "Quantity cannot be null.") @Positive(message = "Quantity must be a positive number.") Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(@NotNull(message = "Quantity cannot be null.") @Positive(message = "Quantity must be a positive number.") Integer quantity) {
        this.quantity = quantity;
    }
    // toString
    @Override
    public String toString() {
        return "ProductDetails{" +
                "productDetailsId='" + productDetailsId + '\'' +
                ", productId=" + productId +
                ", description='" + description + '\'' +
                ", images=" + Arrays.toString(images) +
                ", specifications=" + specifications +
                ", usageInstructions='" + usageInstructions + '\'' +
                ", customerFAQ=" + customerFAQ +
                ", materialType='" + materialType + '\'' +
                ", warrantyInfo='" + warrantyInfo + '\'' +
                ", countryOfOrigin='" + countryOfOrigin + '\'' +
                ", sizes=" + sizes +
                ", highlights=" + highlights +
                ", features=" + features +
                ", quantity=" + quantity +
                '}';
    }
}
