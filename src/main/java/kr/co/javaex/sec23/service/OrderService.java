package kr.co.javaex.sec23.service;

import kr.co.javaex.sec23.domain.Order;
import kr.co.javaex.sec23.domain.Product;
import kr.co.javaex.sec23.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private OrderRepository orderRepository = new OrderRepository();
    private ProductService productService = new ProductService();
    /**
     *  내 주문 내역만 가져오기
     */
    public List<Order> getMyOrders(Long userId) {
        return orderRepository.findOrdersByUserId(userId);
    }

    /**
     * 결제 후 영수증 생성
     */
    public void addOrder(Long userId, Long productId, Long quantity, BigDecimal paymentPrice) {
        Product product = productService.getProduct(productId);
        String productName = (product != null) ? product.getProductName() : "알 수 없는 상품";

        Long newOrderId = orderRepository.createOrder(userId, paymentPrice);

        if (newOrderId != null) {
            orderRepository.createOrderItem(newOrderId, productId, productName, quantity, paymentPrice);
        }
    }
}