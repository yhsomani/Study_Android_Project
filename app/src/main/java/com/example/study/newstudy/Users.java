package com.example.study.newstudy;

public class Users {

    private static final String DEFAULT_PROFILE_IMAGE_URL = "https://firebasestorage.googleapis.com/v0/b/study-5ab42.appspot.com/o/man-user-color-icon.png?alt=media&token=0998c3b8-8db6-4db0-ae74-669d2d3b57b1";

    // Default constructor required for Firebase
    public Users() {
        // Firebase uses this default constructor for deserialization
    }

    // Parameterized constructor for creating user instances
    public Users(String userId, String userName, String mail, String password, String profilepic, String status) {
        this.userId = userId;
        this.userName = userName;
        this.mail = mail;
        this.password = password;
        this.profilepic = profilepic;
        this.status = status;
    }

    private String profilepic, mail, userName, password, userId, status;

    public String getProfilepic() {
        return (profilepic != null && !profilepic.isEmpty()) ? profilepic : DEFAULT_PROFILE_IMAGE_URL;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Users{" +
                "profilepic='" + profilepic + '\'' +
                ", mail='" + mail + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", userId='" + userId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
