package kr.co.javaex.sec23.service;

import kr.co.javaex.sec23.domain.Product;
import kr.co.javaex.sec23.domain.ProductStatus;
import kr.co.javaex.sec23.repository.ProductRepository;

import java.util.List;

public class ProductService {
    private ProductRepository productRepository = new ProductRepository();
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    /**
     * .해당 ID가 존재하는지
     */
    public boolean isProductID(long targetId) {
        List<Product> allProducts = productRepository.findAll();
        boolean isFound = false;

        for(Product product : allProducts){
            if(product.getProductID().equals(targetId)){
                isFound = true;
                break;
            }
        }
        return isFound;
    }

    /**
     * 해당 객체 가져오기
     */
    public Product getProduct(long targetId) {
        List<Product> allProducts = productRepository.findAll();

        for(Product product : allProducts){
            if(product.getProductID().equals(targetId)){
                return product;
            }
        }
        return null;
    }

    /**
     * 덮어쓰기
     */
    public boolean updateProduct(Product targetProduct) {
        List<Product> allProducts = productRepository.findAll();
        boolean isUpdated = false;

        for (Product product : allProducts) {
            if (product.getProductID().equals(targetProduct.getProductID())) {
                int index = allProducts.indexOf(product);

                allProducts.set(index, targetProduct);
                isUpdated = true;
                break;
            }
        }

        if (isUpdated) {
            productRepository.saveAll(allProducts);
        }
        return isUpdated;
    }

    /**
     * 상품 추가
     */
    public void addProduct(Long categoryId, String productName, String productDescription, int productPrice, int productStock) {
        List<Product> allProducts = productRepository.findAll();

        // ID 자동 증가
        long maxId = 0L;

        // 가장 큰 값 찾아서
        for (Product p : allProducts) {
            if (p.getProductID() > maxId) {
                maxId = p.getProductID();
            }
        }
        // + 1하기
        long nextId = maxId + 1L;

        Product newProduct = new Product(categoryId, nextId, productName, productDescription, productPrice, productStock, ProductStatus.ACTIVE);

        // 추가하고
        allProducts.add(newProduct);
        // 덮어쓰기
        productRepository.saveAll(allProducts);
    }

    /**
     * 상품 삭제
     */
    public boolean deleteProduct(Long targetId) {
        List<Product> allProducts = productRepository.findAll();
        boolean isRemoved = false;

        for (Product product : allProducts) {
            if (product.getProductID().equals(targetId)) {

                int index = allProducts.indexOf(product);
                allProducts.remove(index);
                isRemoved = true;
                break;
            }
        }

        if (isRemoved) {
            productRepository.saveAll(allProducts);
        }

        return isRemoved;
    }
}
