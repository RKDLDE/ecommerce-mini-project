package kr.co.javaex.sec23.domain;

import java.math.BigDecimal;

public class Product {
    private Long productId;                 // 상품 ID
    private String productName;             // 상품명
    private String productDesc;             // 상세 설명
    private BigDecimal productPrice;        // 가격
    private Long productStock;              // 재고수량
    private Boolean isActive;               // 상태(정상, 판매중지)
    private Long categoryId;                // 카테고리 ID

    public Product() {
    }

    public Product(Long productId, String productName, String productDesc, BigDecimal productPrice, Long productStock, Boolean isActive, Long categoryId) {
        this.productId = productId;
        this.productName = productName;
        this.productDesc = productDesc;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.isActive = isActive;
        this.categoryId = categoryId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Long getProductStock() {
        return productStock;
    }

    public void setProductStock(Long productStock) {
        this.productStock = productStock;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
