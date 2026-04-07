package kr.co.javaex.sec23.controller;

import kr.co.javaex.sec23.domain.Cart;
import kr.co.javaex.sec23.domain.Product;
import kr.co.javaex.sec23.domain.User;
import kr.co.javaex.sec23.service.CartService;
import kr.co.javaex.sec23.service.ProductService;
import kr.co.javaex.sec23.util.ConsoleUtil;

import java.util.List;

public class CartController {
    private CartService cartService = new CartService();
    private ProductService productService = new ProductService();
    private ConsoleUtil consoleUtil = new ConsoleUtil();

    /**
     * 장바구니 메뉴 - 사용자용
     */
    public void showMenu(User loginUser) {
        while (true) {
            System.out.println("\n[ " + loginUser.getUserName() + "님의 장바구니 ]");
            System.out.println("1. 장바구니 조회 | 2. 수량 변경 | 3. 상품 삭제 | 4. 장바구니 비우기 | 5. 주문하기 | 0. 이전 메뉴");
            int choice = consoleUtil.readInt("선택: ");

            switch (choice) {
                case 1:
                    printCart(loginUser);
                    break;
                case 2:
                    updateCartQuantity(loginUser);
                    break;
                case 3:
                    deleteCartItem(loginUser);
                    break;
                case 4:
                    clearCart(loginUser);
                    break;
                case 5:
                    System.out.println("주문 기능은 곧 업데이트 됩니다!");
                    break;
                case 0:
                    System.out.println("이전 메뉴로 돌아갑니다.");
                    return;
                default:
                    System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
            }
        }
    }

    /**
     * 장바구니 출력 메서드
     */
    public void printCart(User loginUser) {
        System.out.println("\n================================= 장바구니 목록 =================================");
        System.out.println("장바구니ID\t| 상품명\t\t\t\t\t\t\t| 수량\t| 단가\t\t| 총액");
        System.out.println("=================================================================================");

        // 현재 로그인한 유저의 장바구니 목록
        List<Cart> carts = cartService.getMyCart(loginUser.getUserID());

        // 비어있으면
        if (carts.isEmpty()) {
            System.out.println("장바구니가 비어있습니다.");
            System.out.println("=================================================================================");
            return;
        }

        int totalPrice = 0;

        for (Cart cart : carts) {
            // 장바구니의 productID를 이용해서 다른 정보 가져오기
            Product product = productService.getProduct(cart.getProductID());

            if (product != null) {
                // 가격은 가격과ㅣ.. cart의 수량을 곱해서 가져오기
                int itemTotal = product.getProductPrice() * cart.getQuantity();

                // 총금액
                totalPrice += itemTotal;

                System.out.printf("%d\t\t| %s\t| %d개\t| %,d원\t| %,d원\n",
                        cart.getCartID(),
                        product.getProductName(),
                        cart.getQuantity(),
                        product.getProductPrice(),
                        itemTotal);
            }
        }
        System.out.println("=================================================================================");
        System.out.printf("▶ 총 결제 예정 금액: %,d원\n", totalPrice);
        System.out.println("=================================================================================\n");
    }

    /**
     * 장바구니 수량 변경 메서드
     */
    private void updateCartQuantity(User loginUser) {
        printCart(loginUser);

        List<Cart> carts = cartService.getMyCart(loginUser.getUserID());
        // 장바구니가 비어있다면?:? 돌아가기
        if (carts.isEmpty())
            return;

        long targetId = consoleUtil.readLong("수량을 변경할 장바구니 ID 입력: ");
        // 변경할 장바구니를 가져오기..
        Cart targetCart = null;
        for (Cart cart : carts) {
            if (cart.getCartID().equals(targetId)) {
                targetCart = cart;
                break;
            }
        }

        int newQuantity = consoleUtil.readInt("변경할 수량 입력: ");

        // 수량 음수 확인
        if (newQuantity <= 0) {
            System.out.println("수량은 1개 이상이어야 합니다.");
            return;
        }

        // q변경된 수량이 재고를 초과하진 않는지
        Product product = productService.getProduct(targetCart.getProductID());
        if (newQuantity > product.getProductStock()) {
            System.out.println("남은 재고가 부족합니다. (현재 재고: " + product.getProductStock() + "개)");
            return;
        }
        boolean isUpdated = cartService.updateQuantity(targetId, newQuantity);

        if (isUpdated) {
            System.out.println("수량이 성공적으로 변경되었습니다.");
        } else {
            System.out.println("해당하는 장바구니 상품이 없습니다.");
        }
    }

    /**
     * 장바구니 상품 삭제
     */
    private void deleteCartItem(User loginUser) {
        printCart(loginUser);

        List<Cart> carts = cartService.getMyCart(loginUser.getUserID());
        if (carts.isEmpty()) return;

        long targetId = consoleUtil.readLong("삭제할 장바구니 ID 입력: ");

        String confirm = consoleUtil.readString("정말 삭제하시겠습니까? (Y/N): ");
        if (confirm.equalsIgnoreCase("Y")) {
            boolean isDeleted = cartService.deleteItem(targetId);
            if (isDeleted) {
                System.out.println("장바구니에서 상품이 삭제되었습니다.");
            } else {
                System.out.println("해당하는 장바구니 상품이 없습니다.");
            }
        } else {
            System.out.println("삭제가 취소되었습니다.");
        }
    }

    /**
     * 장바구니 전체 비우기 메서드
     */
    private void clearCart(User loginUser) {
        List<Cart> carts = cartService.getMyCart(loginUser.getUserID());

        // 장바구니가 비어있다면
        if (carts.isEmpty()) {
            System.out.println("장바구니가 이미 비어있습니다.");
            return;
        }

        String confirm = consoleUtil.readString("장바구니를 모두 비우시겠습니까? (Y/N): ");
        if (confirm.equalsIgnoreCase("Y")) {
            cartService.clearCart(loginUser.getUserID());
            System.out.println("장바구니가 완전히 비워졌습니다.");
        } else {
            System.out.println("비우기가 취소되었습니다.");
        }
    }
}