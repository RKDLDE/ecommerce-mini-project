package kr.co.javaex.sec23.domain;

public class Cart {
    private Long cartID;            // 장바구니 ID
    private String userID;          // 사용자 ID
    private Long productID;         // 상품 ID
    private int quantity;           // 재고수량
    private boolean isChecked;      // 선택 여부

    public Cart() {
    }

    public Cart(Long cartID, String userID, Long productID, int quantity, boolean isChecked) {
        this.cartID = cartID;
        this.userID = userID;
        this.productID = productID;
        this.quantity = quantity;
        this.isChecked = isChecked;
    }

    public Long getCartID() {
        return cartID;
    }

    public void setCartID(Long cartID) {
        this.cartID = cartID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
