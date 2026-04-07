package kr.co.javaex.sec23.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.javaex.sec23.domain.Category;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryRepository {
    // jackson 라이브러리
    private ObjectMapper mapper = new ObjectMapper();

    // 파일 불러오기
    private final String FILE_PATH = "data/categories.json";


    /**
     * 기존 상품 목록을 가져오는 메서드
     * @return ArrayList<>() 형태의 상품 목록
     */
    public List<Category> findAll() {
        File file = new File(FILE_PATH);

        // 파일이 존재하지 않으면 빈 배열 반환
        if (!file.exists()) {
            return new ArrayList<>();
        }
        // 존재한다면
        try {
            // Categories를 담는 배열 초기화
            Category[] categories = mapper.readValue(file, Category[].class);
            // arraylist 형태로 반환
            return new ArrayList<>(Arrays.asList(categories));
        } catch (IOException e) {
            // 파일 읽기 오류 시 출력 -> 빈 배열 반환
            System.out.println("파일 읽기 오류: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * 새로운 categories 배열을 파일에 저장
     * @param categories
     */
    public void saveAll(List<Category> categories){
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), categories);
        } catch (IOException e) {
            System.out.println("파일 저장 오류: " + e.getMessage());
        }
    }
}
