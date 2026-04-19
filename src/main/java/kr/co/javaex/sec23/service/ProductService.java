package kr.co.javaex.sec23.service;

import kr.co.javaex.sec23.domain.Product;
import kr.co.javaex.sec23.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;

public class ProductService {
    private ProductRepository productRepository = new ProductRepository();

    /**
     * 상품 전체 목록
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    /**
     * .해당 ID가 존재하는지
     */
    public boolean isProductID(long targetId) {
        return productRepository.findById(targetId) != null;
    }

    /**
     * 해당 객체 가져오기
     */
    public Product getProduct(long targetId) {
        return productRepository.findById(targetId);
    }

    /**
     * 덮어쓰기
     */
    public boolean updateProduct(Product targetProduct) {
        return productRepository.update(targetProduct);
    }

    /**
     * 상품 추가
     */
    public void addProduct(Long categoryId, String productName, String productDesc, BigDecimal productPrice, Long productStock) {
        Product newProduct = new Product(null, productName, productDesc, productPrice, productStock, true, categoryId);

        productRepository.save(newProduct);
    }

    /**
     * 상품 삭제
     */
    public boolean deleteProduct(Long targetId) {
        return productRepository.deleteById(targetId);
    }

    /**
     * 삭제되는 카테고리의 상품들을 미분류(0)로 이동
     */
    public void moveProductsToDefault(Long categoryId) {
        productRepository.updateCategoryToDefault(categoryId, 1L); // DB상 미분류는 1..
    }
}
