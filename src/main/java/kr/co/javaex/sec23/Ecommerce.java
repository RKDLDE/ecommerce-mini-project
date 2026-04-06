package kr.co.javaex.sec23;

import kr.co.javaex.sec23.controller.MenuManager;

public class Ecommerce {
    public static void main(String[] args) {
        // 프로그램의 흐름을 제어할 MenuManager(교통경찰) 객체 생성
        MenuManager menuManager = new MenuManager();

        // 프로그램 시작!
        menuManager.start();
    }
}