package kr.co.javaex.sec23.domain;

public class User {
    private Long userId;            // ID
    private String userName;        // 회원명
    private String userPhone;       // 휴대전화
    private String userEmail;       // 이메일
    private String userPw;          // 비밀번호
    private Boolean isAble;         // 상태
    private UserType userType;      // 회원구분

    public User() {
    }

    public User(Long userId, String userName, String userPhone, String userEmail, String userPw, Boolean isAble, UserType userType) {
        this.userId = userId;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.userPw = userPw;
        this.isAble = isAble;
        this.userType = userType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public Boolean getAble() {
        return isAble;
    }

    public void setAble(Boolean able) {
        isAble = able;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
