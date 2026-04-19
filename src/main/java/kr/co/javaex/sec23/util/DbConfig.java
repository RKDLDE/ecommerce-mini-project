package kr.co.javaex.sec23.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConfig {
    private static Connection connection = null;  // 커넥션 null 초기화
    private static Properties props = new Properties(); // properties 객체

    // 설정 파일 읽기
    static {
        try (InputStream input = DbConfig.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new RuntimeException("db.properties 파일 없음");
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("db.properties 읽기 실패", e);
        }
    }

    /**
     * Connection 객체 생성 함수
     * @return connection
     */
    public static Connection getConnection(){
        try {
            if (connection == null || connection.isClosed()) {
                String url = props.getProperty("db.url");
                String user = props.getProperty("db.user");
                String password = props.getProperty("db.password");

                connection = DriverManager.getConnection(url, user, password);
//                System.out.println("DB 연결");
            }
        } catch (SQLException e){
            throw new RuntimeException("DB 연결 실패", e);
        }
        return connection;
    }

    /**
     * Connection close 함수
     */
    public static void closeConnection(){
        try {
            connection.close();
            connection = null;
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}

