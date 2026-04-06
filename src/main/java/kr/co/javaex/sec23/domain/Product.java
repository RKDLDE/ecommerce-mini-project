package kr.co.javaex.sec23.domain;

public class Product {
    private String productID;                  // 상품 ID
    private String productName;             // 상품명
    private String productDescription;      // 상세 설명
    private int productPrice;               // 가격
    private int productStock;               // 재고수량
    private ProductStatus productStatus;    // 상태(정상, 판매중지)

    public Product() {
    }

    public Product(String productID, String productName, String productDescription, int productPrice, int productStock, ProductStatus productStatus) {
        this.productID = productID;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productStatus = productStatus;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductStock() {
        return productStock;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }
}
