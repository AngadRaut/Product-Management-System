package com.pms.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Entity
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seller_id")
    private Long sellerId;

    @NotNull(message = "Seller name field should not be null.")
    @NotEmpty(message = "Seller name field is required.")
    @Column(name = "seller_name")
    private String sellerName;

    @NotBlank(message = "Mobile number is required!")
    @Pattern(regexp = "^[1-9]\\d{9}$", message = "Mobile number must be exactly 10 digits and not start with 0!")
    @Column(name = "mobile_no")
    private String mobileNo;

    @Column(unique = true)
    @Email(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Invalid Email format!")
    @NotEmpty(message = "Email cannot be empty!!!")
    private String email;

    @NotNull(message = "seller companyName-field should not be null!!")
    @NotEmpty(message = "Seller companyName field is required!")
    @Column(name = "company_name")
    private String companyName;

    @Column(name = "is_verified")
    private boolean isVerifies = true;


    @NotNull(message = "Address1 field should not be null.")
    @NotEmpty(message = "Address1 field is required.")
    @Size(min = 5, max = 200, message = "Address1 must be between 5 and 200 characters.")
    private String address1;

    private String address2;

    @OneToMany(mappedBy = "seller",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Product> product;

/*    @Version
    @Column(name = "version")
    private Long version;  // Optimistic Locking version field

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }*/

    // no arg constructor
    public Seller() {
    }

    // all arg constructor
    public Seller(Long sellerId, String sellerName, String mobileNo, String email, String companyName, boolean isVerifies, String address1, String address2, List<Product> product) {
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.mobileNo = mobileNo;
        this.email = email;
        this.companyName = companyName;
        this.isVerifies = isVerifies;
        this.address1 = address1;
        this.address2 = address2;
        this.product = product;
    }

    // setter and getters
    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public @NotNull(message = "Seller name field should not be null.") @NotEmpty(message = "Seller name field is required.") String getSellerName() {
        return sellerName;
    }

    public void setSellerName(@NotNull(message = "Seller name field should not be null.") @NotEmpty(message = "Seller name field is required.") String sellerName) {
        this.sellerName = sellerName;
    }

    public @NotBlank(message = "Mobile number is required!") @Pattern(regexp = "^[1-9]\\d{9}$", message = "Mobile number must be exactly 10 digits and not start with 0!") String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(@NotBlank(message = "Mobile number is required!") @Pattern(regexp = "^[1-9]\\d{9}$", message = "Mobile number must be exactly 10 digits and not start with 0!") String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public @Email(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Invalid Email format!") @NotEmpty(message = "Email cannot be empty!!!") String getEmail() {
        return email;
    }

    public void setEmail(@Email(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Invalid Email format!") @NotEmpty(message = "Email cannot be empty!!!") String email) {
        this.email = email;
    }

    public @NotNull(message = "seller companyName-field should not be null!!") @NotEmpty(message = "Seller companyName field is required!") String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(@NotNull(message = "seller companyName-field should not be null!!") @NotEmpty(message = "Seller companyName field is required!") String companyName) {
        this.companyName = companyName;
    }

    public boolean isVerifies() {
        return isVerifies;
    }

    public void setVerifies(boolean verifies) {
        isVerifies = verifies;
    }

    public @NotNull(message = "Address1 field should not be null.") @NotEmpty(message = "Address1 field is required.") @Size(min = 5, max = 200, message = "Address1 must be between 5 and 200 characters.") String getAddress1() {
        return address1;
    }

    public void setAddress1(@NotNull(message = "Address1 field should not be null.") @NotEmpty(message = "Address1 field is required.") @Size(min = 5, max = 200, message = "Address1 must be between 5 and 200 characters.") String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    // toString
    @Override
    public String toString() {
        return "Seller{" +
                "sellerId=" + sellerId +
                ", sellerName='" + sellerName + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", email='" + email + '\'' +
                ", companyName='" + companyName + '\'' +
                ", isVerifies=" + isVerifies +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", product=" + product +
                '}';
    }
}
