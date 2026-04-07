package kr.co.javaex.sec23.controller;

import kr.co.javaex.sec23.domain.Category;
import kr.co.javaex.sec23.domain.Product;
import kr.co.javaex.sec23.domain.ProductStatus;
import kr.co.javaex.sec23.service.CategoryService;
import kr.co.javaex.sec23.service.ProductService;
import kr.co.javaex.sec23.util.ConsoleUtil;

import java.util.List;

public class ProductController {
    private ProductService productService = new ProductService();
    private CategoryService categoryService = new CategoryService();
    private ConsoleUtil consoleUtil = new ConsoleUtil();
    /**
     * Product 출력 메서드
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

        for(Category category : categories){
            for(Product product : products){
                if(product.getCategoryID().equals(category.getCategoryID())){
                    if(product.getProductStatus() == ProductStatus.ACTIVE){
                        System.out.printf("%d\t| %s\t| %s\n",
                                product.getProductID(),
                                category.getCategoryName(),
                                product.getProductName());
                    }
                }
            }
        }
        System.out.println("=================================================================");

        // 상세...페이지 보기
        while (true) {
            long targetId = consoleUtil.readLong("\n상세 정보를 볼 상품 ID 입력 (0: 이전 메뉴로 돌아가기): ");

            if (targetId == 0) {
                System.out.println("목록 조회를 종료합니다.");
                return;
            }

            // ID 존재하는가?
            if (productService.isProductID(targetId)) {
                Product targetProduct = productService.getProduct(targetId);
                printProductDetail(targetProduct);
                break;
            } else {
                System.out.println("해당하는 상품이 없습니다. 번호를 다시 확인해주세요.");
            }
        }
    }

    /**
     * 상세 페이지..
     */
    private void printProductDetail(Product product) {
        System.out.println("\n======================== 상품 상세 정보 ========================");
        System.out.println("▶ 상품번호 : " + product.getProductID());
        System.out.println("▶ 상품명 : " + product.getProductName());
        System.out.println("▶ 가격 : " + product.getProductPrice() + "원");
        System.out.println("▶ 남은재고 : " + product.getProductStock() + "개");
        System.out.println("▶ 상태값 : " + (product.getProductStatus() == ProductStatus.ACTIVE ? "판매중" : "판매중지"));
        System.out.println("=================================================================");
        System.out.println(product.getProductDescription());
        System.out.println("=================================================================\n");
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
                    printProduct();
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
        printProduct();
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
                    targetProduct.setProductDescription(newDesc);
                    System.out.println("상품 설명이 변경되었습니다.");
                    break;
                case 3:
                    int newPrice = consoleUtil.readInt("바꿀 가격 입력: ");
                    targetProduct.setProductPrice(newPrice);
                    System.out.println("가격이 변경되었습니다.");
                    break;
                case 4:
                    int newStock = ConsoleUtil.readInt("바꿀 재고 입력: ");
                    targetProduct.setProductStock(newStock);
                    System.out.println("재고가 변경되었습니다.");
                    break;
                case 5:
                    System.out.println("1. 판매중(ACTIVE) | 2. 판매중지(DEACTIVATED)");
                    int statusChoice = consoleUtil.readInt("상태 선택: ");
                    if (statusChoice == 1) {
                        targetProduct.setProductStatus(ProductStatus.ACTIVE);
                        System.out.println("상태가 판매중으로 변경되었습니다.");
                    } else if (statusChoice == 2) {
                        targetProduct.setProductStatus(ProductStatus.DEACTIVATED);
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
         * 상품 삭제 메서드
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
                System.out.println("ID: " + c.getCategoryID() + " | 카테고리명: " + c.getCategoryName());
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
            int productPrice = consoleUtil.readInt("가격: ");
            int productStock = consoleUtil.readInt("재고: ");

            productService.addProduct(categoryId, productName, productDescription, productPrice, productStock);
            System.out.println("새 상품이 성공적으로 추가되었습니다!");
        }

    /**
     * 상품 삭제 메서드
     */
    private void deleteProduct() {
        System.out.println("\n========== 상품 삭제 ==========");
        printProduct();

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
