package kr.co.javaex.sec23.controller;

import kr.co.javaex.sec23.domain.User;
import kr.co.javaex.sec23.domain.UserAuth;
import kr.co.javaex.sec23.domain.UserStatus;
import kr.co.javaex.sec23.service.UserService;
import kr.co.javaex.sec23.util.ConsoleUtil;

import java.util.List;
import java.util.Scanner;

public class UserController {
    private UserService userService = new UserService();
    private ConsoleUtil consoleUtil = new ConsoleUtil();
    // 로그인

    /**
     * 로그인
     */
    public User login() {
        System.out.println("\n=========== 로그인 ===========");
        String email = consoleUtil.readString("이메일 입력: ");
        String pw = consoleUtil.readString("비밀번호 입력: ");

        User loginUser = userService.login(email, pw);

        // 돌아온 객체가 있으면 그대로 menu로 보내기
        if (loginUser != null){
            System.out.println("안녕하세요 " + loginUser.getUserName() + "님!");
            return loginUser;
        } else {
            System.out.println("로그인 실패: 이메일 또는 비밀번호를 확인해주세요.");
            return null;
        }
    }

    /**
     * 회원가입 (이메일, ID, 비밀번호 중복 및 유효성 검사 포함)
     */
    public void signUp() {
        System.out.println("\n=========== 회원가입 ===========");
        String name = consoleUtil.readString("이름 입력: ");
        String phone = consoleUtil.readString("전화번호 입력: ");

        // 이메일 중복 검사
        String email;
        while(true){
            email = consoleUtil.readString("이메일 입력: ");
            if(userService.isPossibleEmail(email)) {
                System.out.println("사용 가능한 이메일 입니다.");
                break;
            } else {
                System.out.println("사용 불가능한 이메일 입니다.");
            }
        }

        // 아이디 중복 및 유효성 검사
        String id;
        while(true){
            id = consoleUtil.readString("ID 입력 (5~15자리): ");
            if(userService.isPossibleID(id)){
                System.out.println("사용 가능한 ID 입니다.");
                break;
            } else {
                System.out.println("사용 불가능한 ID 입니다.");
            }
        }

        // 비밀번호 정규식 검사
        String pw;
        while(true){
            pw = consoleUtil.readString("비밀번호 입력 (영소/대문자, 숫자 포함 5~15자리): ");
            if(userService.isPossiblePw(pw)){
                System.out.println("사용 가능한 비밀번호 입니다.");
                break;
            } else {
                System.out.println("사용 불가능한 비밀번호 입니다.");
            }
        }

        User newUser = new User(id, name, pw, phone, email, UserStatus.ENABLE, UserAuth.USER);

        if (userService.registerUser(newUser)) {
            System.out.println("\n회원가입이 완료되었습니다! 로그인 해주세요.");
        } else {
            System.out.println("\n회원가입 처리 중 문제가 발생했습니다.");
        }
    }

    /**
     * 회원 정보 수정
     * 이름, 전화번호, 이메일
     */
    public void  updateInfo(User user){
        System.out.println("\n=========== 내 정보 수정 ===========");
        String name = consoleUtil.readString("바꿀 이름 입력: ");
        String phone = consoleUtil.readString("바꿀 전화번호 입력: ");
        String email;
        // 이메일 중복 검사
        while(true){
            email = consoleUtil.readString("이메일 입력: ");
            if(userService.isPossibleEmail(email)) {
                System.out.println("사용 가능한 이메일 입니다.");
                break;
            } else {
                System.out.println("사용 불가능한 이메일 입니다.");
            }
        }
        userService.updateProfile(user.getUserID(), name, phone, email);
    }

    /**
     * 비밀번호 수정
     * 기존 비밀번호 확인 후 변경ㄹ
     */
    public void updatePw(User currentUser) {
        System.out.println("\n=========== 비밀번호 변경 ===========");

        // 현재 비밀번호 확인
        String currentPw = consoleUtil.readString("현재 비밀번호 입력: ");
        if (!userService.checkCurrentPassword(currentUser, currentPw)) {
            System.out.println("현재 비밀번호가 일치하지 않습니다. 메인 메뉴로 돌아갑니다.");
            return;
        }

        // 새 비밀번호 입력 및 유효성 검사
        String newPw;
        while (true) {
            newPw = consoleUtil.readString("새 비밀번호 입력 (영소/대문자, 숫자 포함 5~15자리): ");
            if (userService.isPossiblePw(newPw)) {
                break;
            } else {
                System.out.println("사용 불가능한 비밀번호 입니다.");
            }
        }

        userService.updatePassword(currentUser.getUserID(), newPw);
        System.out.println("비밀번호가 성공적으로 변경되었습니다.");
        currentUser.setUserPW(newPw);
    }

    /**
     * 전체 회원 목록 출력 - 관리자
     */
    public void printAllUsers() {
        System.out.println("\n================================================= 전체 회원 목록 =================================================");
        System.out.println("아이디\t\t| 이름\t| 이메일\t\t\t\t| 전화번호\t\t| 상태\t\t| 권한");
        System.out.println("==================================================================================================================");

        // 모든 유저 목록 가져오기
        List<User> users = userService.getAllUsers();

        if (users.isEmpty()) {
            System.out.println("등록된 회원이 없습니다.");
            System.out.println("==================================================================================================================");
            return;
        }

        // 향상된 for문으로 유저 정보 출력
        for (User u : users) {
            System.out.printf("%s\t| %s\t| %s\t| %s\t| %s\t| %s\n",
                    u.getUserID(),
                    u.getUserName(),
                    u.getUserEmail(),
                    u.getUserPhoneNumber(),
                    u.getUserStatus(),
                    u.getUserAuth());
        }
        System.out.println("==================================================================================================================");
    }
}
