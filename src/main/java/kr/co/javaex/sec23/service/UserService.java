package kr.co.javaex.sec23.service;

import kr.co.javaex.sec23.domain.User;
import kr.co.javaex.sec23.repository.UserRepository;

import java.util.List;

public class UserService {
    private UserRepository userRepository = new UserRepository();

    /**
     * 회원가입 로직
     * 중복된 아이디나 이미 등록된 메일을 확인
     * 비밀번호 유효성 확인
     *
     * @param newUser
     * @return 모든통과시 true
     */
    public boolean registerUser(User newUser) {
        // 레파지토리에서 꺼내오기
        List<User> allUsers = userRepository.findAll();

        // 중복 검사
        for (User user : allUsers) {
            if (user.getUserID().equals(newUser.getUserID())) {
                System.out.println("중복된 아이디가 존재합니다.");
                return false;
            }
            if (user.getUserEmail().equals(newUser.getUserEmail())) {
                System.out.println("이미 등록된 이메일입니다.");
                return false;
            }
        }

        // 비밀번호 유효성 검사
        if (!validatePassword(newUser.getUserPW())) {
            System.out.println("비밀번호 형식이 올바르지 않습니다.");
            return false;
        }

        // 모든 통과 시 저장 요청
        userRepository.save(newUser);
        return true;
    }

    /**
     * 로그인 로직
     * 이메일과 비밀번호가 일치하는지 확인한다.
     * @param email
     * @param password
     * @return 일치시 user 객체 없을 시 null
     */
    public User login(String email, String password) {
        List<User> allUsers = userRepository.findAll();

        for (User user : allUsers) {
            // 이메일과 비밀번호가 모두 일치하는지 확인
            if (user.getUserEmail().equals(email) && user.getUserPW().equals(password)) {
                return user; // 일치하는 유저 객체 반환
            }
        }
        return null; // 일치하는 유저 없음
    }

    /**
     * 정보 수정 로직
     * ID로 회원을 찾고
     * name, phone, email을 입력하여
     * 정보를 수정한다.
     */
    public void updateProfile(String userId, String name, String phone, String email) {
        // 유저 다 가져오고
        List<User> allUsers = userRepository.findAll();

        // 해당 id와 일치하면 set 한다.
        for (User user : allUsers) {
            if (user.getUserID().equals(userId)) {
                user.setUserName(name);
                user.setUserPhoneNumber(phone);
                user.setUserEmail(email);
                break;
            }
        }
        // 수정된 리스트 전체를 다시 파일에 저장
        userRepository.saveAll(allUsers);
    }

    /**
     * 비밀번호 유효성 검사 메서드
     * @param pw
     * @return 정규식에 일치하는가?
     */
    private boolean validatePassword(String pw) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{5,15}$";
        return pw.matches(regex);
    }
}
