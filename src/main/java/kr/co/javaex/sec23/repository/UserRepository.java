package kr.co.javaex.sec23.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.javaex.sec23.domain.User;
import kr.co.javaex.sec23.domain.UserType;
import kr.co.javaex.sec23.util.DbConfig;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserRepository {

    /**
     * 기존 사용자 목록을 가져오는 메서드
     * @return ArrayList<>() 형태의 사용자 목록
     */
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                User user = new User(
                        rs.getLong("user_id"),
                        rs.getString("user_name"),
                        rs.getString("user_phone"),
                        rs.getString("user_email"),
                        rs.getString("user_pw"),
                        rs.getInt("is_able") == 1, // 1이면 true
                        UserType.valueOf(rs.getString("user_type")) // String -> Enum
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * 회원 저장
     */
    public void save(User user) {
        String sql = "INSERT INTO users (user_name, user_phone, user_email, user_pw, is_able, user_type) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getUserPhone());
            pstmt.setString(3, user.getUserEmail());
            pstmt.setString(4, user.getUserPw());

            // Boolean값으로 사용했으니, 저장시에는 number 형태로
            pstmt.setInt(5, user.getAble() ? 1 : 0);

            // Enum에서 문자열로 저장해야함
            pstmt.setString(6, user.getUserType().name());

            pstmt.executeUpdate();
            System.out.println("회원 가입 완료.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 이메일로 찾기
     */
    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE user_email = ?";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getLong("user_id"),
                        rs.getString("user_name"),
                        rs.getString("user_phone"),
                        rs.getString("user_email"),
                        rs.getString("user_pw"),
                        rs.getInt("is_able") == 1, // 1이면 true
                        UserType.valueOf(rs.getString("user_type")) // 문자열을 다시 Enum으로
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // 못 찾으면 null
    }

    /**
     * 비밀번호 업데이투
     */
    public void updatePassword(Long userId, String newPw) {
        String sql = "UPDATE users SET user_pw = ? WHERE user_id = ?";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newPw);
            pstmt.setLong(2, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 회원정보 수정
     */
    public void updateProfile(Long userId, String name, String phone, String email){
        String sql = "UPDATE users SET user_name = ?, user_phone = ?, user_email = ? WHERE user_id = ?";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, phone);
            pstmt.setString(3, email);
            pstmt.setLong(4, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
