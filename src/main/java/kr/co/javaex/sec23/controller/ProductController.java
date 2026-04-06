package kr.co.javaex.sec23.controller;

import kr.co.javaex.sec23.domain.Product;
import kr.co.javaex.sec23.domain.ProductStatus;
import kr.co.javaex.sec23.service.ProductService;

import java.util.List;

public class ProductController {
    private ProductService productService = new ProductService();

    public void showProductList() {
        System.out.println("\n============================= 상품 목록 =============================");
        System.out.println("ID\t| 카테고리\t| 상품명\t\t\t| 가격\t\t| 재고\t| 상태");
        System.out.println("\n====================================================================");

        List<Product> products = productService.getAllProducts();

        for(Product product : products){
            if(product.getProductStatus() == ProductStatus.ACTIVE){
                System.out.printf("%s\t| %s\t| %s\t| %,d원\t| %d개\t| %s\n",
                    product.getProductID(),
                    product.getCategoryID(),
                    product.getProductName(),
                    product.getProductPrice(),
                    product.getProductStock(),
                    product.getProductStatus());
            }
        }
        System.out.println("=====================================================================");
    }
}
