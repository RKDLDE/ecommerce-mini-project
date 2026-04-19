package kr.co.javaex.sec23.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.javaex.sec23.domain.Category;
import kr.co.javaex.sec23.util.DbConfig;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryRepository {

    /**
     * 기존 상품 목록을 가져오는 메서드
     */
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        // 대분류 정렬 다음은 중분류 정렬대로
        String sql = "SELECT * FROM categories ORDER BY category_top_id NULLS FIRST, category_sort ASC";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Long topId = rs.getLong("category_top_id");
                if (rs.wasNull()) {
                    topId = null; // DB의 NULL -> null로 바꿔야 한다네유..
                }

                Category category = new Category(
                        rs.getLong("category_id"),
                        rs.getString("category_name"),
                        rs.getLong("category_sort"),
                        topId
                );
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    /**
     * 카테고리 추가
     */
    public void save(String name, Long topId, int sortOrder) {
        String sql = "INSERT INTO categories (category_name, category_sort, category_top_id) VALUES (?, ?, ?)";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setInt(2, sortOrder);

            if (topId == null) { // 대분류라면
                pstmt.setNull(3, Types.NUMERIC);
            } else { // 중분류라면
                pstmt.setLong(3, topId);
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 카테고리 조회...
     */
    public Category findById(Long categoryId) {
        String sql = "SELECT * FROM categories WHERE category_id = ?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, categoryId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Long topId = rs.getLong("category_top_id");
                if (rs.wasNull()) topId = null;
                return new Category(
                        rs.getLong("category_id"), rs.getString("category_name"),
                        rs.getLong("category_sort"), topId
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 카테고리 수정
     */
    public boolean update(Long categoryId, String newName, int newSortOrder) {
        String sql = "UPDATE categories SET category_name = ?, category_sort = ? WHERE category_id = ?";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newName);
            pstmt.setInt(2, newSortOrder);
            pstmt.setLong(3, categoryId);
            return pstmt.executeUpdate() > 0; // 업데이트 성공 여부 반환
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 하위 카테고리 개수 세기
     */
    public int countByTopId(Long topId) {
        String sql = "SELECT COUNT(*) FROM categories WHERE category_top_id = ?";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, topId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 카테고리 삭제
     */
    public void deleteById(Long categoryId) {
        String sql = "DELETE FROM categories WHERE category_id = ?";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, categoryId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
