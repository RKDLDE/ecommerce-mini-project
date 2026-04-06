package kr.co.javaex.sec23.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.javaex.sec23.domain.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserRepository {
    // jackson 라이브러리
    private ObjectMapper mapper = new ObjectMapper();

    // 파일 불러오기
    private final String FILE_PATH = "src/main/java/kr/co/javaex/sec23/util/users.json";


    /**
     * 기존 사용자 목록을 가져오는 메서드
     * @return ArrayList<>() 형태의 사용자 목록
     */
    public List<User> findAll() {
        File file = new File(FILE_PATH);

        // 파일이 존재하지 않으면 빈 배열 반환
        if (!file.exists()) {
            return new ArrayList<>();
        }
        // 존재한다면
        try {
            // User를 담는 배열 초기화
            User[] users = mapper.readValue(file, User[].class);
            // arraylist 형태로 반환
            return new ArrayList<>(Arrays.asList(users));
        } catch (IOException e) {
            // 파일 읽기 오류 시 출력 -> 빈 배열 반환
            System.out.println("파일 읽기 오류: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void save(User newUser) {
        List<User> users = findAll();
        users.add(newUser);
        saveAll(users);
    }

    public void saveAll(List<User> users) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), users);
        } catch (IOException e) {
            System.out.println("파일 저장 오류: " + e.getMessage());
        }
    }
}
