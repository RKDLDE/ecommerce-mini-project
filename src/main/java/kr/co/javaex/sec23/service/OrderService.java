package kr.co.javaex.sec23.service;

import kr.co.javaex.sec23.domain.Order;
import kr.co.javaex.sec23.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private OrderRepository orderRepository = new OrderRepository();

    /**
     *  내 주문 내역만 가져오기
     */
    public List<Order> getMyOrders(String userId) {
        List<Order> allOrders = orderRepository.findAll();
        List<Order> myOrders = new ArrayList<>();

        // 로그인한 사용자 것만 가져오기
        for (Order order : allOrders) {
            if (order.getUserID().equals(userId)) {
                myOrders.add(order);
            }
        }
        return myOrders;
    }

    /**
     * 결제 후 영수증 생성
     */
    public void addOrder(String userId, Long productId, int quantity, int paymentPrice) {
        List<Order> allOrders = orderRepository.findAll();

        // 도은님 시그니처: 영수증 번호(ID) 자동 증가 로직
        long maxId = 0L;
        for (Order o : allOrders) {
            if (o.getOrderID() > maxId) {
                maxId = o.getOrderID();
            }
        }
        long nextId = maxId + 1L;

        Order newOrder = new Order(nextId, userId, productId, quantity, paymentPrice);

        // 창고 리스트에 넣고
        allOrders.add(newOrder);

        // 파일에 덮어쓰기!
        orderRepository.saveAll(allOrders);
    }
}