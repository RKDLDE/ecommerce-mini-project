package kr.co.javaex.sec23.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Order {
    private Long orderId;              // 주문 번호
    private BigDecimal orderTotalPay;  // 총 금액
    private LocalDateTime orderDate;   // 결제 시각
    private Long userId;               // 주문한 사람 ID

    public Order() {
    }

    public Order(Long orderId, BigDecimal orderTotalPay, LocalDateTime orderDate, Long userId) {
        this.orderId = orderId;
        this.orderTotalPay = orderTotalPay;
        this.orderDate = orderDate;
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getOrderTotalPay() {
        return orderTotalPay;
    }

    public void setOrderTotalPay(BigDecimal orderTotalPay) {
        this.orderTotalPay = orderTotalPay;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
