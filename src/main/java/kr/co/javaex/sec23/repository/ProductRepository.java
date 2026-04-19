package kr.co.javaex.sec23.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.javaex.sec23.domain.Product;
import kr.co.javaex.sec23.util.DbConfig;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductRepository {
    /**
     * 기존 상품 목록을 가져오는 메서드
     * @return ArrayList<>() 형태의 상품 목록
     */
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY product_id ASC";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Product product = new Product(
                        rs.getLong("product_id"),
                        rs.getString("product_name"),
                        rs.getString("product_desc"),
                        rs.getBigDecimal("product_price"),
                        rs.getLong("product_stock"),
                        rs.getInt("is_active") == 1, // 1이면 true(판매중), 0이면 false(판매중지)
                        rs.getLong("category_id")
                );
                products.add(product);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return products;
    }

    /**
     * 상품 조회
     */
    public Product findById(Long productId) {
        String sql = "SELECT * FROM products WHERE product_id = ?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, productId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                            rs.getLong("product_id"),
                            rs.getString("product_name"),
                            rs.getString("product_desc"),
                            rs.getBigDecimal("product_price"),
                            rs.getLong("product_stock"),
                            rs.getInt("is_active") == 1,
                            rs.getLong("category_id")
                    );
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null; // 없는 상품이면 null 반환
    }

    /**
     * 상품 추가
     */
    public void save(Product product) {
        String sql = "INSERT INTO products (product_name, product_desc, product_price, product_stock, is_active, category_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, product.getProductName());
            pstmt.setString(2, product.getProductDesc());
            pstmt.setBigDecimal(3, product.getProductPrice());
            pstmt.setLong(4, product.getProductStock());
            pstmt.setInt(5, product.getActive() ? 1 : 0); // true면 1, false면 0
            pstmt.setLong(6, product.getCategoryId());

            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    /**
     * 상품 수정
     */
    public boolean update(Product product) {
        String sql = "UPDATE products SET product_name = ?, product_desc = ?, product_price = ?, product_stock = ?, is_active = ?, category_id = ? WHERE product_id = ?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, product.getProductName());
            pstmt.setString(2, product.getProductDesc());
            pstmt.setBigDecimal(3, product.getProductPrice());
            pstmt.setLong(4, product.getProductStock());
            pstmt.setInt(5, product.getActive() ? 1 : 0);
            pstmt.setLong(6, product.getCategoryId());
            pstmt.setLong(7, product.getProductId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    /**
     * 상품 삭제
     */
    public boolean deleteById(Long productId) {
        String sql = "DELETE FROM products WHERE product_id = ?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, productId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    /**
     * 카테고리 삭제 시 미분류
     */
    public void updateCategoryToDefault(Long oldCategoryId, Long defaultCategoryId) {
        String sql = "UPDATE products SET category_id = ? WHERE category_id = ?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, defaultCategoryId);
            pstmt.setLong(2, oldCategoryId);
            pstmt.executeUpdate();

        } catch (SQLException e) { e.printStackTrace(); }
    }
}
