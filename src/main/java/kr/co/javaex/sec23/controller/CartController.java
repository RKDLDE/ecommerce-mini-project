package kr.co.javaex.sec23.controller;

import kr.co.javaex.sec23.domain.Cart;
import kr.co.javaex.sec23.domain.CartItem;
import kr.co.javaex.sec23.domain.Product;
import kr.co.javaex.sec23.domain.User;
import kr.co.javaex.sec23.service.CartService;
import kr.co.javaex.sec23.service.OrderService;
import kr.co.javaex.sec23.service.ProductService;
import kr.co.javaex.sec23.util.ConsoleUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartController {
    private CartService cartService = new CartService();
    private ProductService productService = new ProductService();
    private OrderService orderService = new OrderService();
    private ConsoleUtil consoleUtil = new ConsoleUtil();

    /**
     * 장바구니 메인 메뉴
     */
    public void showMenu(User loginUser) {
        while (true) {
            System.out.println("\n[ " + loginUser.getUserName() + "님의 장바구니 ]");
            System.out.println("1. 장바구니 조회 | 2. 상품 선택/해제 | 3. 수량 변경 | 4. 상품 삭제 | 5. 장바구니 비우기 | 6. 선택 상품 주문 | 0. 이전 메뉴");
            int choice = consoleUtil.readInt("선택: ");

            switch (choice) {
                case 1:
                    printCart(loginUser);
                    break;
                case 2:
                    toggleCartItem(loginUser);
                    break;
                case 3:
                    updateCartQuantity(loginUser);
                    break;
                case 4:
                    deleteCartItem(loginUser);
                    break;
                case 5:
                    clearCart(loginUser);
                    break;
                case 6:
                    orderCheckedItems(loginUser);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("잘못된 입력입니다.");
            }
        }
    }

    /**
     * 장바구니 출력
     */
    public void printCart(User loginUser) {
        System.out.println("\n======================================= 장바구니 목록 =======================================");
        System.out.println("선택\t| 번호\t| 상품명\t\t\t\t\t\t\t| 수량\t| 단가\t\t| 총액");
        System.out.println("=============================================================================================");

        List<CartItem> cartItems = cartService.getMyCartItems(loginUser.getUserId());
        // 비어있으면
        if (cartItems.isEmpty()) {
            System.out.println("장바구니가 비어있습니다.");
            System.out.println("=============================================================================================");
            return;
        }

        BigDecimal totalCheckedPrice = BigDecimal.ZERO;

        for (CartItem item: cartItems) {
            Product product = productService.getProduct(item.getProductId());

            if (product != null) {
                BigDecimal itemTotal = product.getProductPrice().multiply(BigDecimal.valueOf(item.getCartQuantity()));                String checkMark = item.getChecked() ? "[ V ]" : "[   ]";

                // 체크된 상품 총액 계산
                if (item.getChecked()) {
                    totalCheckedPrice = totalCheckedPrice.add(itemTotal);
                }

                System.out.printf("%s\t| %d\t| %s\t| %d개\t| %,d원\t| %,d원\n",
                        checkMark,
                        item.getCartItemId(),
                        product.getProductName(),
                        item.getCartQuantity(),
                        product.getProductPrice().longValue(),
                        itemTotal.longValue());
            }
        }
        System.out.println("=============================================================================================");
        System.out.printf("현재 선택된 상품 총 결제 예정 금액: %,d원\n", totalCheckedPrice.longValue());
        System.out.println("=============================================================================================\n");
    }

    /**
     * 상품 선택/해제
     */
    private void toggleCartItem(User loginUser) {
        printCart(loginUser);
        List<CartItem> cartItems = cartService.getMyCartItems(loginUser.getUserId());
        if (cartItems.isEmpty()) return;

        long targetId = consoleUtil.readLong("선택 상태를 변경할 장바구니 번호 입력 (0: 취소): ");
        if (targetId == 0) return;

        boolean isToggled = cartService.toggleCheck(targetId);
        if (isToggled) {
            System.out.println("선택 상태가 변경되었습니다.");
        } else {
            System.out.println("해당하는 장바구니 상품이 없습니다.");
        }
    }

    /**
     * 선택 상품 주문
     */
    private void orderCheckedItems(User loginUser) {
        printCart(loginUser);

        List<CartItem> myCartItems = cartService.getMyCartItems(loginUser.getUserId());
        List<CartItem> checkedItems = new ArrayList<>();
        BigDecimal totalOrderPrice = BigDecimal.ZERO;

        // 체크된 상품 가져오기
        for (CartItem item : myCartItems) {
            if (item.getChecked()) {
                checkedItems.add(item);
            }
        }

        // 체크 안했으면
        if (checkedItems.isEmpty()) {
            System.out.println("선택된 상품이 없습니다. 먼저 결제할 상품을 체크해주세요.");
            return;
        }

        // 재고 확인
        for (CartItem item : checkedItems) {
            Product product = productService.getProduct(item.getProductId());
            if (item.getCartQuantity() > product.getProductStock()) {
                System.out.println("[" + product.getProductName() + "] 상품의 재고가 부족하여 결제를 진행할 수 없습니다.");
                return;
            }
            BigDecimal itemTotal = product.getProductPrice().multiply(BigDecimal.valueOf(item.getCartQuantity()));
            totalOrderPrice = totalOrderPrice.add(itemTotal);
        }

        // 결제 확인
        String confirm = consoleUtil.readString("정말 결제하시겠습니까? (Y/N): ");

        if (confirm.equalsIgnoreCase("Y")) {
            for (CartItem item : checkedItems) {
                Product product = productService.getProduct(item.getProductId());

                // 재고 차감
                product.setProductStock(product.getProductStock() - item.getCartQuantity());
                productService.updateProduct(product);

                // 영수증 생성
                BigDecimal itemTotal = product.getProductPrice().multiply(BigDecimal.valueOf(item.getCartQuantity()));
                orderService.addOrder(loginUser.getUserId(), product.getProductId(), item.getCartQuantity(), itemTotal);

                // 장바구니 삭제
                cartService.deleteCartItem(item.getCartId());
            }

            System.out.println("선택하신 상품의 결제가 완료되었습니다.");
        } else {
            System.out.println("결제가 취소되었습니다.");
        }
    }

    /**
     * 장바구니 수량 변경
     */
    private void updateCartQuantity(User loginUser) {
        printCart(loginUser);

        List<CartItem> cartItems = cartService.getMyCartItems(loginUser.getUserId());
        if (cartItems.isEmpty()) return;

        long targetId = consoleUtil.readLong("수량을 변경할 장바구니 ID 입력: ");

        CartItem targetItem = null;
        for (CartItem item : cartItems) {
            if (item.getCartItemId().equals(targetId)) {
                targetItem = item;
                break;
            }
        }

        if (targetItem == null) {
            System.out.println("해당하는 상품이 없습니다.");
            return;
        }

        long newQuantity = consoleUtil.readLong("변경할 수량 입력: ");

        // 수량 유효성 검사
        if (newQuantity <= 0) {
            System.out.println("수량은 1개 이상이어야 합니다.");
            return;
        }

        // 재고 확인
        Product product = productService.getProduct(targetItem.getProductId());
        if (newQuantity > product.getProductStock()) {
            System.out.println("남은 재고가 부족합니다. (현재 재고: " + product.getProductStock() + "개)");
            return;
        }

        boolean isUpdated = cartService.updateQuantity(targetId, newQuantity);

        if (isUpdated) {
            System.out.println("수량이 성공적으로 변경되었습니다.");
        } else {
            System.out.println("수량 변경에 실패했습니다.");
        }
    }

    /**
     * 장바구니 개별 상품 삭제
     */
    private void deleteCartItem(User loginUser) {
        printCart(loginUser);

        List<CartItem> cartItems = cartService.getMyCartItems(loginUser.getUserId());
        if (cartItems.isEmpty()) return;

        long targetId = consoleUtil.readLong("삭제할 장바구니 ID 입력: ");

        String confirm = consoleUtil.readString("정말 삭제하시겠습니까? (Y/N): ");
        if (confirm.equalsIgnoreCase("Y")) {
            boolean isDeleted = cartService.deleteCartItem(targetId);
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
     * 장바구니 전체 비우기
     */
    private void clearCart(User loginUser) {
        List<CartItem> cartItems = cartService.getMyCartItems(loginUser.getUserId());

        if (cartItems.isEmpty()) {
            System.out.println("장바구니가 이미 비어있습니다.");
            return;
        }

        String confirm = consoleUtil.readString("장바구니를 모두 비우시겠습니까? (Y/N): ");
        if (confirm.equalsIgnoreCase("Y")) {
            cartService.clearCart(loginUser.getUserId());
            System.out.println("장바구니가 완전히 비워졌습니다.");
        } else {
            System.out.println("비우기가 취소되었습니다.");
        }
    }
}