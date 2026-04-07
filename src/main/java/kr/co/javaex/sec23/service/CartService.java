package kr.co.javaex.sec23.service;

import kr.co.javaex.sec23.domain.Cart;
import kr.co.javaex.sec23.repository.CartRepository;

import java.util.ArrayList;
import java.util.List;

public class CartService {
    private CartRepository cartRepository = new CartRepository();

    /**
     * 내 장바구니 목록만 가져오기
     */
    public List<Cart> getMyCart(String userId) {
        List<Cart> allCarts = cartRepository.findAll();
        List<Cart> myCarts = new ArrayList<>();

        for (Cart cart : allCarts) {
            if (cart.getUserID().equals(userId)) {
                myCarts.add(cart);
            }
        }
        return myCarts;
    }

    /**
     * 장바구니에 상품 추가
     * 기존 상품 확인 하고 수량을 더하거나
     * 기존 상품이 없다며 ㄴ신규 생성
     */
    public void addCart(String userId, Long productId, int quantity) {
        List<Cart> allCarts = cartRepository.findAll();
        boolean isFound = false;

        // 이미 담겨있으면..
        for (Cart cart : allCarts) {
            if (cart.getUserID().equals(userId) && cart.getProductID().equals(productId)) {
                int index = allCarts.indexOf(cart);

                // 가지고 있는 수량에 + 넘어온 수량
                cart.setQuantity(cart.getQuantity() + quantity);
                allCarts.set(index, cart);

                isFound = true;
                break;
            }
        }

        // 장바구니에 없던 상품이라면
        if (!isFound) {
            // ID 자동 증가 로직
            long maxId = 0L;
            for (Cart c : allCarts) {
                if (c.getCartID() > maxId) {
                    maxId = c.getCartID();
                }
            }
            long nextId = maxId + 1L;

            // 새롭게 추가......
            Cart newCart = new Cart(nextId, userId, productId, quantity, true);
            allCarts.add(newCart);
        }

        // 최종적으로 파일에 덮어쓰기
        cartRepository.saveAll(allCarts);
    }

    /**
     * 덮어쓰기
     */
    public boolean updateQuantity(Long targetId, int newQuantity) {
        List<Cart> allCarts = cartRepository.findAll();
        boolean isUpdated = false;

        for (Cart cart : allCarts) {
            if (cart.getCartID().equals(targetId)) {
                int index = allCarts.indexOf(cart);

                cart.setQuantity(newQuantity);
                allCarts.set(index, cart);

                isUpdated = true;
                break;
            }
        }

        if (isUpdated) {
            cartRepository.saveAll(allCarts);
        }
        return isUpdated;
    }

    /**
     * 장바구니 개별 상품 삭제
     */
    public boolean deleteItem(Long targetId) {
        List<Cart> allCarts = cartRepository.findAll();
        boolean isRemoved = false;


        for (Cart cart : allCarts) {
            if (cart.getCartID().equals(targetId)) {
                int index = allCarts.indexOf(cart);

                allCarts.remove(index);
                isRemoved = true;
                break;
            }
        }

        if (isRemoved) {
            cartRepository.saveAll(allCarts);
        }
        return isRemoved;
    }

    /**
     * 장바구니 전체 비우기
     */
    public void clearCart(String userId) {
        List<Cart> allCarts = cartRepository.findAll();
        List<Cart> remainingCarts = new ArrayList<>();

        // userId의 것만 지워야 하기 때문에...
        // userId가 아닌애들것만 새롭게 추가
        for (Cart cart : allCarts) {
            if (!cart.getUserID().equals(userId)) {
                remainingCarts.add(cart);
            }
        }

        // 덮어쓰기....
        cartRepository.saveAll(remainingCarts);
    }

    /**
     * 장바구니 선택/해제 상태 변경
     */
    public boolean toggleCheck(Long targetId) {
        List<Cart> allCarts = cartRepository.findAll();
        boolean isUpdated = false;

        for (Cart cart : allCarts) {
            if (cart.getCartID().equals(targetId)) {
                int index = allCarts.indexOf(cart);

                // 현재 상태의 반대값으로
                cart.setChecked(!cart.isChecked());

                allCarts.set(index, cart);
                isUpdated = true;
                break;
            }
        }

        if (isUpdated) {
            cartRepository.saveAll(allCarts);
        }
        return isUpdated;
    }
}