package kr.co.javaex.sec23.controller;

import kr.co.javaex.sec23.domain.User;
import kr.co.javaex.sec23.service.UserService;
import kr.co.javaex.sec23.util.ConsoleUtil;

import java.util.Scanner;

public class UserController {
    private UserService userService = new UserService();
    private ConsoleUtil consoleUtil = new ConsoleUtil();
    // 로그인
    public User login() {
        String email = consoleUtil.readString("이메일 입력: ");
        String pw = consoleUtil.readString("비밀번호 입력: ");

        User loginUser = userService.login(email, pw);

        if (loginUser != null){
            System.out.println("로그인 성공");
            return loginUser;
        } else {
            System.out.println("로그인 실패: 이메일 또는 비밀번호를 확인해주세요.");
            return null;
        }
    }

    // 회원 가입
    public void signUp() {

    }

    // 정보 수정
    public void  updateInfo(){

    }
}
