package kr.co.javaex.sec23.controller;

import kr.co.javaex.sec23.domain.Category;
import kr.co.javaex.sec23.domain.Product;
import kr.co.javaex.sec23.domain.User;
import kr.co.javaex.sec23.service.CartService;
import kr.co.javaex.sec23.service.CategoryService;
import kr.co.javaex.sec23.service.ProductService;
import kr.co.javaex.sec23.util.ConsoleUtil;

import java.math.BigDecimal;
import java.util.List;

public class ProductController {
    private ProductService productService = new ProductService();
    private CategoryService categoryService = new CategoryService();
    private ConsoleUtil consoleUtil = new ConsoleUtil();
    private CartService cartService = new CartService();
    /**
     * 일반 사용자용 Product 출력 메서드
     */
    public void printProduct() {
        System.out.println("\n=========================== 상품 목록 ===========================");
        System.out.println("상품ID\t| 카테고리\t| 상품명");
        System.out.println("=================================================================");

        List<Product> products = productService.getAllProducts();
        List<Category> categories = categoryService.getAllCategories();

        if (products.isEmpty()) {
            System.out.println("등록된 상품이 없습니다.");
            System.out.println("=================================================================");
            return;
        }

        // 카테고리별 출력
        // 활성화된 상품만 출력
        for(Category category : categories){
            for(Product product : products){
                if(product.getCategoryId().equals(category.getCategoryId())){
                    if(product.getActive()){
                        System.out.printf("%d\t| %s\t|%s\t| %,d원\n",
                                product.getProductId(),
                                category.getCategoryName(),
                                product.getProductName(),
                                product.getProductPrice().longValue());
                    }
                }
            }
        }
        System.out.println("=================================================================");
    }

    /**
     * 관리자용.. 상품 출력 (판매중지인것도 보기)
     */
    public void printAllProduct() {
        System.out.println("\n=========================== 상품 목록 ===========================");
        System.out.println("상품ID\t| 카테고리\t| 상품명\t| 상태");
        System.out.println("=================================================================");

        List<Product> products = productService.getAllProducts();
        List<Category> categories = categoryService.getAllCategories();

        if (products.isEmpty()) {
            System.out.println("등록된 상품이 없습니다.");
            System.out.println("=================================================================");
            return;
        }

        for(Category category : categories){
            for(Product product : products){
                if(product.getCategoryId().equals(category.getCategoryId())){
                    String statusStr = product.getActive() ? "판매중" : "판매중지";
                        System.out.printf("%d\t| %s\t| %s\t| %s\n",
                                product.getProductId(),
                                category.getCategoryName(),
                                product.getProductName(),
                                statusStr);
                    }
                }
            }
        System.out.println("=================================================================");
    }
    /**
     * 상세 페이지..예쁘게
     */
    private void printProductDetail(Product product) {
        System.out.println("\n======================== 상품 상세 정보 ========================");
        System.out.println("상품번호 : " + product.getProductId());
        System.out.println("상품명 : " + product.getProductName());
        System.out.println("가격 : " + product.getProductPrice().longValue() + "원");
        System.out.println("남은재고 : " + product.getProductStock() + "개");
        System.out.println("상태값 : " + (product.getActive() ? "판매중" : "판매중지"));
        System.out.println("=================================================================");
        System.out.println(product.getProductDesc());
        System.out.println("=================================================================\n");
    }

    /**
     * 쇼핑 메서드
     * 목록 확인하고 상세 보기 후 장바구니 담기 여부
     */
    public void showShoppingMenu(User loginUser) {
        while (true) {
            printProduct(); // 상품 목록 띄우기

            long targetId = consoleUtil.readLong("\n상세 정보를 볼 상품 ID 입력 (0: 이전 메뉴로 돌아가기): ");

            if (targetId == 0) {
                System.out.println("쇼핑 메뉴를 종료합니다.");
                return;
            }

            if (productService.isProductID(targetId)) {
                Product targetProduct = productService.getProduct(targetId);
                printProductDetail(targetProduct); // 상세 정보 띄우기

                // 장바구니 여부
                System.out.println("1. 장바구니에 담기 | 0. 목록으로 돌아가기");
                int choice = consoleUtil.readInt("선택: ");

                if (choice == 1) {
                    addToCart(loginUser, targetProduct);
                }
            } else {
                System.out.println("해당하는 상품이 없습니다. 번호를 다시 확인해주세요.");
            }
        }
    }

    /**
     * 장바구니 담기
     */
    private void addToCart(User loginUser, Product product) {
        if (!product.getActive()) {
            System.out.println("현재 판매가 중지된 상품입니다.");
            return;
        }

        long quantity = consoleUtil.readLong("몇 개를 담으시겠습니까? : ");

        if (quantity <= 0) {
            System.out.println("수량은 1개 이상이어야 합니다.");
            return;
        }

        if (quantity > product.getProductStock()) {
            System.out.println("남은 재고가 부족합니다. (현재 재고: " + product.getProductStock() + "개)");
            return;
        }

        cartService.addCart(loginUser.getUserId(), product.getProductId(), quantity);
        System.out.println("[" + product.getProductName() + "] " + quantity + "개가 장바구니에 성공적으로 담겼습니다.");
    }
    /**
     * 상품 메뉴 - 관리자
     */
    public void showMenu() {
        while(true) {
            System.out.println("\n[ 관리자 - 상품 관리 ]");
            System.out.println("1. 상품 출력 | 2. 상품 수정 | 3. 상품 추가 | 4. 상품 삭제 | 0. 이전 메뉴");
            int choice = consoleUtil.readInt("선택: ");

            switch (choice){
                case 1:
                    printAllProduct();
                    break;
                case 2:
                    updateProduct();
                    break;
                case 3:
                    addProduct();
                    break;
                case 4:
                    deleteProduct();
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
     * 상품 수정 메서드
     */
    private void updateProduct() {
        printAllProduct();
        long targetId;
        Product targetProduct;
        // 상품 존재 여부
        while (true) {
            targetId = consoleUtil.readLong("수정할 상품 ID 입력: ");
            if (productService.isProductID(targetId)) {
                targetProduct = productService.getProduct(targetId);
                break;
            } else {
                System.out.println("해당하는 상품이 없습니다. 다시 입력해주세요.");
            }
        }

        // 상품에 대한 수정 진행하기
        while (true) {
            System.out.println("\n해당 상품 [" + targetProduct.getProductName() + "] 수정을 진행합니다.");
            System.out.println("1. 상품명 | 2. 상품 설명 | 3. 가격 | 4. 재고 | 5. 상태 | 0. 이전 메뉴");

            int choice = consoleUtil.readInt("선택: ");

            switch (choice) {
                case 1:
                    String newName = consoleUtil.readString("바꿀 상품명 입력: ");
                    targetProduct.setProductName(newName);
                    System.out.println("상품명이 변경되었습니다.");
                    break;
                case 2:
                    String newDesc = consoleUtil.readString("바꿀 상품 설명 입력: ");
                    targetProduct.setProductDesc(newDesc);
                    System.out.println("상품 설명이 변경되었습니다.");
                    break;
                case 3:
                    long newPrice = consoleUtil.readLong("바꿀 가격 입력: ");
                    targetProduct.setProductPrice(BigDecimal.valueOf(newPrice));
                    System.out.println("가격이 변경되었습니다.");
                    break;
                case 4:
                    long newStock = consoleUtil.readLong("바꿀 재고 입력: ");
                    targetProduct.setProductStock(newStock);
                    System.out.println("재고가 변경되었습니다.");
                    break;
                case 5:
                    System.out.println("1. 판매중(ACTIVE) | 2. 판매중지(DEACTIVATED)");
                    int statusChoice = consoleUtil.readInt("상태 선택: ");
                    if (statusChoice == 1) {
                        targetProduct.setActive(true);
                        System.out.println("상태가 판매중으로 변경되었습니다.");
                    } else if (statusChoice == 2) {
                        targetProduct.setActive(false);
                        System.out.println("상태가 판매중지로 변경되었습니다.");
                    } else {
                        System.out.println("잘못된 입력입니다.");
                    }
                    break;
                case 0:
                    boolean isSuccess = productService.updateProduct(targetProduct);
                    if (isSuccess) {
                        System.out.println("상품 수정이 완료되어 이전 메뉴로 돌아갑니다.");
                    } else {
                        System.out.println("저장 중 오류가 발생했습니다.");
                    }
                    return;
                default:
                    System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
            }
        }
    }

        /**
         * 상품 추가 메서드
         */
        private void addProduct() {
            System.out.println("\n========== 상품 추가 ==========");

            // 카테고리 여부 확인
            List<Category> categories = categoryService.getAllCategories();
            if (categories.isEmpty()) {
                System.out.println("등록된 카테고리가 없습니다.");
                return;
            }

            System.out.println("\n[ 등록 가능한 카테고리 목록 ]");
            for (Category c : categories) {
                System.out.println("ID: " + c.getCategoryId() + " | 카테고리명: " + c.getCategoryName());
            }

            // 카테고리 입력
            long categoryId;
            while(true) {
                categoryId = consoleUtil.readLong("\n등록할 카테고리 ID 입력: ");
                if (categoryService.isCategoryID(categoryId)) {
                    break;
                } else {
                    System.out.println("존재하지 않는 카테고리 ID입니다. 다시 입력해주세요.");
                }
            }

            // 상품 정보 입력
            String productName = consoleUtil.readString("상품명: ");
            String productDescription = consoleUtil.readString("상품 설명: ");
            long productPrice = consoleUtil.readLong("가격: ");
            long productStock = consoleUtil.readLong("재고: ");

            productService.addProduct(categoryId, productName, productDescription, BigDecimal.valueOf(productPrice), productStock);
            System.out.println("새 상품이 성공적으로 추가되었습니다!");
        }

    /**
     * 상품 삭제 메서드
     */
    private void deleteProduct() {
        System.out.println("\n========== 상품 삭제 ==========");
        printAllProduct();

        long targetId = consoleUtil.readLong("삭제할 상품 ID 입력: ");

        // 존재하는 ID인가?
        if (!productService.isProductID(targetId)) {
            System.out.println("해당 ID의 상품이 존재하지 않습니다.");
            return;
        }

        // 삭제 여부 한번더
        String confirm = consoleUtil.readString("정말 삭제하시겠습니까? (Y/N): ");
        if (confirm.equalsIgnoreCase("Y")) {
            boolean isDeleted = productService.deleteProduct(targetId);
            if (isDeleted) {
                System.out.println("상품이 성공적으로 삭제되었습니다.");
            } else {
                System.out.println("삭제 처리 중 오류가 발생했습니다.");
            }
        } else {
            System.out.println("삭제가 취소되었습니다.");
        }
    }
}
