package kr.co.javaex.sec23.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.javaex.sec23.domain.Order;
import kr.co.javaex.sec23.util.DbConfig;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderRepository {
    /**
     * 주문생성
     */
    public Long createOrder(Long userId, BigDecimal totalPay) {
        String sql = "INSERT INTO de_orders (order_total_pay, user_id) VALUES (?, ?)";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, new String[]{"order_id"})) {

            pstmt.setBigDecimal(1, totalPay);
            pstmt.setLong(2, userId);
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
     * 주문 상세에도 저장
     */
    public void createOrderItem(Long orderId, Long productId, String productName, Long quantity, BigDecimal pay) {
        String sql = "INSERT INTO de_order_items (order_product_name, order_product_quantity, order_product_pay, order_id, product_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, productName);
            pstmt.setLong(2, quantity);
            pstmt.setBigDecimal(3, pay);
            pstmt.setLong(4, orderId);
            pstmt.setLong(5, productId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 주문 상세 조회
     */
    public List<Order> findOrdersByUserId(Long userId) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM de_orders WHERE user_id = ? ORDER BY order_date DESC";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order(
                            rs.getLong("order_id"),
                            rs.getBigDecimal("order_total_pay"),
                            rs.getTimestamp("order_date").toLocalDateTime(),
                            rs.getLong("user_id")
                    );
                    list.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
