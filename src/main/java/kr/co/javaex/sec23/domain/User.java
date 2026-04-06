package kr.co.javaex.sec23.domain;

public class User {
    private String userID;          // ID
    private String userName;        // 회원명
    private String userPW;          // 비밀번호
    private String userPhoneNumber; // 휴대전화
    private String userEmail;       // 이메일
    private UserStatus userStatus;  // 상태
    private UserAuth userAuth;      // 회원구분


    public User() {
    }

    public User(String userID, String userName, String userPW, String userPhoneNumber, String userEmail, UserStatus userStatus, UserAuth userAuth) {
        this.userID = userID;
        this.userName = userName;
        this.userPW = userPW;
        this.userPhoneNumber = userPhoneNumber;
        this.userEmail = userEmail;
        this.userStatus = userStatus;
        this.userAuth = userAuth;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPW() {
        return userPW;
    }

    public void setUserPW(String userPW) {
        this.userPW = userPW;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public UserAuth getUserAuth() {
        return userAuth;
    }

    public void setUserAuth(UserAuth userAuth) {
        this.userAuth = userAuth;
    }
}
