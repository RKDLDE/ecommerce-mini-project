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
        System.out.println("\n=============================== 상품 목록 ===============================");
        System.out.println("상품ID\t| 카테고리\t| 상품명\t\t\t\t\t\t\t| 가격\t\t| 재고\t| 상품 설명");
        System.out.println("========================================================================");

        List<Product> products = productService.getAllProducts();
        List<Category> categories = categoryService.getAllCategories();
        if (products.isEmpty()) {
            System.out.println("상품이 없습니다.");
            System.out.println("=========================================================================");
            return;
        }

        for(Category category : categories){
            for(Product product : products){
                if(product.getCategoryID().equals(category.getCategoryID())){
                    if(product.getProductStatus() == ProductStatus.ACTIVE){
                        System.out.printf("%d\t %s\t| %s\t| %,d원\t| %d개\t| %s\n",
                                product.getProductID(),
                                category.getCategoryName(),
                                product.getProductName(),
                                product.getProductPrice(),
                                product.getProductStock(),
                                product.getProductDescription());
                    }
                }
            }
        }

        System.out.println("========================================================================");
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
//                    addProduct();
                    break;
                case 4:
//                    deleteProduct();
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

        /**
         * 상품 삭제 메서드
         */
//    private void deleteProduct() {
//    }
//
        /**
         * 상품 추가 메서드
         */
//    private void addProduct() {
//
//    }

    }
}
