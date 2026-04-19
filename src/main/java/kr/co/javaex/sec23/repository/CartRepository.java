package kr.co.javaex.sec23.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.javaex.sec23.domain.Cart;
import kr.co.javaex.sec23.domain.CartItem;
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

public class CartRepository {

    /**
     * 기존 장바구니 목록을 가져오는 메서드
     * @return ArrayList<>() 형태의 상품 목록
     */
    public List<CartItem> findCartItems(Long userId) {
        List<CartItem> list = new ArrayList<>();
        String sql = "SELECT ci.* FROM cart_items ci " +
                "JOIN carts c ON ci.cart_id = c.cart_id " +
                "WHERE c.user_id = ? ORDER BY ci.cart_item_id ASC";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    CartItem item = new CartItem(
                            rs.getLong("cart_item_id"),
                            rs.getLong("cart_quantity"),
                            rs.getInt("is_checked") == 1, // 1이면 true, 0이면 false
                            rs.getLong("cart_id"),
                            rs.getLong("product_id")
                    );
                    list.add(item);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    /**
     * 내 장바구니(본체) ID 찾기
     */
    public Long findCart(Long userId) {
        String sql = "SELECT cart_id FROM carts WHERE user_id = ?";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("cart_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 장바구니 새로 만들기
     * 장바구니가 없다면??? 만들어야 하기 때문
     */
    public Long createCart(Long userId) {
        String sql = "INSERT INTO carts (user_id) VALUES (?)";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, new String[]{"cart_id"})) {

            pstmt.setLong(1, userId);
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 장바구니에 이미 담긴 상품 찾기
     */
    public CartItem findCartItem(Long cartId, Long productId) {
        String sql = "SELECT * FROM cart_items WHERE cart_id = ? AND product_id = ?";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, cartId);
            pstmt.setLong(2, productId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new CartItem(
                            rs.getLong("cart_item_id"),
                            rs.getLong("cart_quantity"),
                            rs.getInt("is_checked") == 1,
                            rs.getLong("cart_id"),
                            rs.getLong("product_id")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 장바구니 아이템 단건 조회
     */
    public CartItem findCartItem(Long cartItemId) {
        String sql = "SELECT * FROM cart_items WHERE cart_item_id = ?";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, cartItemId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new CartItem(
                            rs.getLong("cart_item_id"),
                            rs.getLong("cart_quantity"),
                            rs.getInt("is_checked") == 1,
                            rs.getLong("cart_id"),
                            rs.getLong("product_id")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 장바구니에 새로운 상품 넣기
     */
    public void insertCartItem(Long cartId, Long productId, Long quantity) {
        String sql = "INSERT INTO cart_items (cart_quantity, is_checked, cart_id, product_id) VALUES (?, 1, ?, ?)";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, quantity);
            pstmt.setLong(2, cartId);
            pstmt.setLong(3, productId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 수량 업데이트
     */
    public boolean updateQuantity(Long cartItemId, Long newQuantity) {
        String sql = "UPDATE cart_items SET cart_quantity = ? WHERE cart_item_id = ?";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, newQuantity);
            pstmt.setLong(2, cartItemId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 체크(선택/해제) 상태 업데이트
     */
    public boolean updateCheckStatus(Long cartItemId, boolean isChecked) {
        String sql = "UPDATE cart_items SET is_checked = ? WHERE cart_item_id = ?";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, isChecked ? 1 : 0);
            pstmt.setLong(2, cartItemId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 개별 상품 삭제
     */
    public boolean deleteCartItem(Long cartItemId) {
        String sql = "DELETE FROM cart_items WHERE cart_item_id = ?";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, cartItemId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 장바구니 전체 비우기
     */
    public void deleteAllCartItems(Long userId) {
        String sql = "DELETE FROM cart_items WHERE cart_id = (SELECT cart_id FROM carts WHERE user_id = ?)";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
