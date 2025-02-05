package com.pms.dto;

import com.pms.entities.Category;
import com.pms.entities.Seller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class ProductRequest {
    // product fields
    private String productName;
    private String brandName;
    private Double price;
    private String warrantyDetails;
    private Integer stock;
    private Seller seller;
    private Category category;
    private String madeIn;

    // productDetails field
    private String productDetailsId;
    private String description;
    private Byte[] images;
    private Map<String, String> specifications;
    private String usageInstructions;
    private List<Map<String, String>> customerFAQ;
    private String materialType;
    private String warrantyInfo;
    private String countryOfOrigin;
    private List<String> sizes;
    private List<String> highlights;
    private List<String> features;
    private Integer quantity;

    public ProductRequest() {
    }

    public String getProductDetailsId() {
        return productDetailsId;
    }

    public void setProductDetailsId(String productDetailsId) {
        this.productDetailsId = productDetailsId;
    }

    public ProductRequest(String productDetailsId, String productName, String brandName, Double price, String warrantyDetails, Integer stock, Seller seller, Category category, String madeIn, String description, Byte[] images, Map<String, String> specifications, String usageInstructions, List<Map<String, String>> customerFAQ, String materialType, String warrantyInfo, String countryOfOrigin, List<String> sizes, List<String> highlights, List<String> features, Integer quantity) {
        this.productName = productName;
        this.productDetailsId=productDetailsId;
        this.brandName = brandName;
        this.price = price;
        this.warrantyDetails = warrantyDetails;
        this.stock = stock;
        this.seller = seller;
        this.category = category;
        this.madeIn = madeIn;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getWarrantyDetails() {
        return warrantyDetails;
    }

    public void setWarrantyDetails(String warrantyDetails) {
        this.warrantyDetails = warrantyDetails;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
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

    public String getMadeIn() {
        return madeIn;
    }

    public void setMadeIn(String madeIn) {
        this.madeIn = madeIn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Byte[] getImages() {
        return images;
    }

    public void setImages(Byte[] images) {
        this.images = images;
    }

    public Map<String, String> getSpecifications() {
        return specifications;
    }

    public void setSpecifications(Map<String, String> specifications) {
        this.specifications = specifications;
    }

    public String getUsageInstructions() {
        return usageInstructions;
    }

    public void setUsageInstructions(String usageInstructions) {
        this.usageInstructions = usageInstructions;
    }

    public List<Map<String, String>> getCustomerFAQ() {
        return customerFAQ;
    }

    public void setCustomerFAQ(List<Map<String, String>> customerFAQ) {
        this.customerFAQ = customerFAQ;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getWarrantyInfo() {
        return warrantyInfo;
    }

    public void setWarrantyInfo(String warrantyInfo) {
        this.warrantyInfo = warrantyInfo;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public List<String> getSizes() {
        return sizes;
    }

    public void setSizes(List<String> sizes) {
        this.sizes = sizes;
    }

    public List<String> getHighlights() {
        return highlights;
    }

    public void setHighlights(List<String> highlights) {
        this.highlights = highlights;
    }

    public List<String> getFeatures() {
        return features;
    }

    public void setFeatures(List<String> features) {
        this.features = features;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    // toString
    @Override
    public String toString() {
        return "ProductRequest{" +
                "productDetailsId='" + productDetailsId + '\'' +
                "productName='" + productName + '\'' +
                ", brandName='" + brandName + '\'' +
                ", price=" + price +
                ", warrantyDetails='" + warrantyDetails + '\'' +
                ", stock=" + stock +
                ", seller=" + seller +
                ", category=" + category +
                ", madeIn='" + madeIn + '\'' +
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
