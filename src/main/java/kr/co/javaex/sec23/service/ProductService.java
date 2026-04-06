package kr.co.javaex.sec23.service;

import kr.co.javaex.sec23.domain.Product;
import kr.co.javaex.sec23.repository.ProductRepository;

import java.util.List;

public class ProductService {
    private ProductRepository productRepository = new ProductRepository();
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
