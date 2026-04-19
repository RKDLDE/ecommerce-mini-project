package kr.co.javaex.sec23.domain;

public class Cart {
    private Long cartId;            // 장바구니 ID
    private Long userId;            // 사용자 ID

    public Cart() {
    }

    public Cart(Long cartId, Long userId) {
        this.cartId = cartId;
        this.userId = userId;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
