package kr.co.javaex.sec23.domain;

public class CartItem {
    private Long cartItemId;    // 장바구니 아이템 id
    private Long cartQuantity;  // 상품 개별 수량
    private Boolean isChecked;  // 상품 선택?
    private Long cartId;        // 장바구니 id
    private Long productId;     // 상품 id

    public CartItem() {
    }

    public CartItem(Long cartItemId, Long cartQuantity, Boolean isChecked, Long cartId, Long productId) {
        this.cartItemId = cartItemId;
        this.cartQuantity = cartQuantity;
        this.isChecked = isChecked;
        this.cartId = cartId;
        this.productId = productId;
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Long getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(Long cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
