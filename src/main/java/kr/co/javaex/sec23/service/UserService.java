package kr.co.javaex.sec23.service;

import kr.co.javaex.sec23.domain.User;
import kr.co.javaex.sec23.repository.UserRepository;

import java.util.List;

public class UserService {
    private UserRepository userRepository = new UserRepository();

    /**
     * 전체 유저 가져오기
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * 비밀번호 유효성 검사
     * 정규식 만족하는가?
     */
    public boolean isPossiblePw(String pw){
        return validatePassword(pw);
    }

    /**
     * 이메일 중복 여부
     */
    public boolean isPossibleEmail(String email){
        return userRepository.findByEmail(email) == null;
    }

    /**
     * 사용자 등록
     */
    public boolean registerUser(User newUser) {
        userRepository.save(newUser);
        return true;
    }

    /**
     * 로그인 로직
     * 이메일과 비밀번호가 일치하는지 확인한다.
     */
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user != null && user.getUserPw().equals(password)){
            return user;
        }
        return null;
    }

    /**
     * 정보 수정 로직
     * ID로 회원을 찾고
     * name, phone, email을 입력하여
     * 정보를 수정한다.
     */
    public void updateProfile(Long userId, String name, String phone, String email) {
        userRepository.updateProfile(userId, name, phone, email);
    }

    /**
     * 비밀번호 정규식 검사 메서드
     */
    private boolean validatePassword(String pw) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{5,15}$";
        return pw.matches(regex);
    }

    /**
     * 현재 비밀번호 확인
     */
    public boolean checkCurrentPassword(User currentUser, String inputPw) {
        // 현재 로그인한 객체의 비밀번호와 방금 입력한 비밀번호가 같은지 비교
        return currentUser.getUserPw().equals(inputPw);
    }

    /**
     * t새로운 비밀번호로 덮어쓰고 저장
     */
    public void updatePassword(Long userId, String newPw) {
        userRepository.updatePassword(userId, newPw);
    }
}
