package kr.co.javaex.sec23.service;

import kr.co.javaex.sec23.domain.User;
import kr.co.javaex.sec23.repository.UserRepository;

import java.util.List;

public class UserService {
    private UserRepository userRepository = new UserRepository();

    /**
     * ID 유효성 검사
     * 정규식 만족 + 중복 X
     */
    public boolean isPossibleID(String id){
        List<User> allUsers = userRepository.findAll();

        // 정규식
        if (!validateID(id)) return false;

        // 중복
        for (User user : allUsers) {
            if (user.getUserID().equals(id)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 비밀번호 유효성 검사
     * 정규식 만족하는가?
     */
    public boolean isPossiblePw(String pw){
        if (!validatePassword(pw)) {
            return false;
        }
        return true;
    }

    /**
     * 이메일 중복 여부
     */
    public boolean isPossibleEmail(String email){
        List<User> allUsers = userRepository.findAll();
        for (User user : allUsers) {
            if (user.getUserEmail().equals(email)) {
                return false;
            }
        }
        return true;
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
     * 비밀번호 정규식 검사 메서드
     */
    private boolean validatePassword(String pw) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{5,15}$";
        return pw.matches(regex);
    }

    /**
     * ID 정규식 검사 메서드
     */
    private boolean validateID(String id){
        String regex = "^[a-zA-Z0-9]{5,15}$";
        return id.matches(regex);
    }

    /**
     * 현재 비밀번호 확인
     */
    public boolean checkCurrentPassword(User currentUser, String inputPw) {
        // 현재 로그인한 객체의 비밀번호와 방금 입력한 비밀번호가 같은지 비교
        return currentUser.getUserPW().equals(inputPw);
    }

    /**
     * t새로운 비밀번호로 덮어쓰고 저장
     */
    public void updatePassword(String userId, String newPw) {
        List<User> allUsers = userRepository.findAll();

        for (User user : allUsers) {
            // id 동일한지 확인하고
            if (user.getUserID().equals(userId)) {
                user.setUserPW(newPw); // 새 비밀번호로 교체
                break;
            }
        }
        // 수정된 리스트 파일에 다시 저장
        userRepository.saveAll(allUsers);
    }
}
