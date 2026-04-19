package kr.co.javaex.sec23.service;

import kr.co.javaex.sec23.domain.Cart;
import kr.co.javaex.sec23.domain.CartItem;
import kr.co.javaex.sec23.repository.CartRepository;

import java.util.ArrayList;
import java.util.List;

public class CartService {
    private CartRepository cartRepository = new CartRepository();

    /**
     * 내 장바구니 목록만 가져오기
     */
    public List<CartItem> getMyCartItems(Long userId) {
        return cartRepository.findCartItems(userId);
    }

    /**
     * 장바구니에 상품 추가
     * 기존 상품 확인 하고 수량을 더하거나
     * 기존 상품이 없다며 ㄴ신규 생성
     */
    public void addCart(Long userId, Long productId, Long quantity) {
        // 내 장바구니를 찾는데, 없으면 만들어야 함
        Long cartId = cartRepository.findCart(userId);
        if (cartId == null) {
            cartId = cartRepository.createCart(userId);
        }

        // 이미 장바구니에 상품이 있는가?
        CartItem existingItem = cartRepository.findCartItem(cartId, productId);

        // 이미 담겨있으면..
        if (existingItem != null) {
            // 기존에 담긴  수량이랑 넘어온 수량을 더해얗함
            Long newQuantity = existingItem.getCartQuantity() + quantity;
            cartRepository.updateQuantity(existingItem.getCartItemId(), newQuantity);
        } else {
            // 없던 상품이라면 새롭게 추가
            cartRepository.insertCartItem(cartId, productId, quantity);
        }
    }

    /**
     * 덮어쓰기
     */
    public boolean updateQuantity(Long targetId, Long newQuantity) {
        return cartRepository.updateQuantity(targetId, newQuantity);
    }

    /**
     * 장바구니 개별 상품 삭제
     */
    public boolean deleteCartItem(Long targetId) {
        return cartRepository.deleteCartItem(targetId);
    }

    /**
     * 장바구니 전체 비우기
     */
    public void clearCart(Long userId) {
        cartRepository.deleteAllCartItems(userId);
    }

    /**
     * 장바구니 선택/해제 상태 변경
     */
    public boolean toggleCheck(Long targetId) {
        CartItem item = cartRepository.findCartItem(targetId);
        boolean isUpdated = false;

        if (item != null) {
            // 체크 바꿔야지
            return cartRepository.updateCheckStatus(targetId, !item.getChecked());
        }
        return false;
    }
}