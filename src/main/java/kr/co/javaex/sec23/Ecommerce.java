package kr.co.javaex.sec23;

import kr.co.javaex.sec23.controller.MenuManager;
import kr.co.javaex.sec23.util.DbConfig;

public class Ecommerce {
    public static void main(String[] args) {
        MenuManager menuManager = new MenuManager();
        try {
            DbConfig.getConnection();
            menuManager.start();
        } catch (Exception e) {
            System.err.println("DB 연결에 실패" + e.getMessage());
            return;
        } finally {
            DbConfig.closeConnection(); // 종료 시점에 무조건 Connection 닫기
        }
    }
}