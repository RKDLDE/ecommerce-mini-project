package kr.co.javaex.sec23.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Order {
    private Long orderID;          // 주문 번호
    private String userID;         // 주문한 사람 ID
    private Long productID;        // 주문한 상품 ID
    private int quantity;          // 주문 수량
    private int paymentPrice;      // 총 금액
    private String orderDate;      // 결제 시각

    public Order() {
    }

    public Order(Long orderID, String userID, Long productID, int quantity, int paymentPrice) {
        this.orderID = orderID;
        this.userID = userID;
        this.productID = productID;
        this.quantity = quantity;
        this.paymentPrice = paymentPrice;
        this.orderDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));    }

    public Long getOrderID() {
        return orderID;
    }

    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPaymentPrice() {
        return paymentPrice;
    }

    public void setPaymentPrice(int paymentPrice) {
        this.paymentPrice = paymentPrice;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
