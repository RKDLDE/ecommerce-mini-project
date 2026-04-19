package kr.co.javaex.sec23.domain;

import java.math.BigDecimal;

public class OrderItem {
    private Long orderItemId;           // 주문 아이템 id
    private String orderProductName;    // 주문 상품 이름
    private Long orderProductQuantity;  // 주문 상품 수량
    private BigDecimal orderProductPay; // 주문 상품 금액
    private Long orderId;               // 주문 id
    private Long productId;             // 상품 id

    public OrderItem() {
    }

    public OrderItem(Long orderItemId, String orderProductName, Long orderProductQuantity, BigDecimal orderProductPay, Long orderId, Long productId) {
        this.orderItemId = orderItemId;
        this.orderProductName = orderProductName;
        this.orderProductQuantity = orderProductQuantity;
        this.orderProductPay = orderProductPay;
        this.orderId = orderId;
        this.productId = productId;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getOrderProductName() {
        return orderProductName;
    }

    public void setOrderProductName(String orderProductName) {
        this.orderProductName = orderProductName;
    }

    public Long getOrderProductQuantity() {
        return orderProductQuantity;
    }

    public void setOrderProductQuantity(Long orderProductQuantity) {
        this.orderProductQuantity = orderProductQuantity;
    }

    public BigDecimal getOrderProductPay() {
        return orderProductPay;
    }

    public void setOrderProductPay(BigDecimal orderProductPay) {
        this.orderProductPay = orderProductPay;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
