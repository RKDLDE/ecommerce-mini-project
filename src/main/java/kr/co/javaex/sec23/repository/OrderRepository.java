package kr.co.javaex.sec23.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.javaex.sec23.domain.Order;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderRepository {
    // jackson 라이브러리
    private ObjectMapper mapper = new ObjectMapper();

    // 파일 불러오기
    private final String FILE_PATH = "data/orders.json";


    /**
     * 기존 주문 목록을 가져오는 메서드
     * @return ArrayList<>() 형태의 주문 목록
     */
    public List<Order> findAll() {
        File file = new File(FILE_PATH);

        // 파일이 존재하지 않으면 빈 배열 반환
        if (!file.exists()) {
            return new ArrayList<>();
        }
        // 존재한다면
        try {
            // Orders를 담는 배열 초기화
            Order[] orders = mapper.readValue(file, Order[].class);
            // arraylist 형태로 반환
            return new ArrayList<>(Arrays.asList(orders));
        } catch (IOException e) {
            // 파일 읽기 오류 시 출력 -> 빈 배열 반환
            System.out.println("파일 읽기 오류: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void saveAll(List<Order> orders){
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), orders);
        } catch (IOException e) {
            System.out.println("파일 저장 오류: " + e.getMessage());
        }
    }
}
