package kr.co.javaex.sec23.service;

import kr.co.javaex.sec23.domain.Product;
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
}
