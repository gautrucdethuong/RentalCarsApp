package com.example.rentalcarsapp.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Author by HUYNH NHAT MINH (ミン).
 * Email: minhhnce140197@fpt.edu.vn.
 * Date on 6/9/2021.
 * Company: FPT大学.
 */

public class User implements Serializable {

    private int userId;
    private String userEmail;
    private String fullName;
    private String userPassword;
    private int userGender;
    private Date userBirthday;
    private String userAddress;
    private String userPhoneNumber;
    private String userImages;
    private Date userCreatedDate;
    private Date userDeletedDate;
    private String roleName;
    private boolean userStatus;
    private String staffCode;
    private int storeId;

    public User(String userEmail, String fullName, String userPhoneNumber, String roleName, int userGender, Date userBirthday, Date userCreatedDate) {
        this.userEmail = userEmail;
        this.fullName = fullName;
        this.userPhoneNumber = userPhoneNumber;
        this.roleName = roleName;
        this.userCreatedDate = userCreatedDate;
        this.userBirthday = userBirthday;
        this.userGender = userGender;
    }
    public User(String userEmail, String fullName, String userPhoneNumber, String roleName, int userGender) {
        this.userEmail = userEmail;
        this.fullName = fullName;
        this.userPhoneNumber = userPhoneNumber;
        this.roleName = roleName;

        this.userGender = userGender;
    }
    public User() {
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int isUserGender() {
        return userGender;
    }

    public void setUserGender(int userGender) {
        this.userGender = userGender;
    }

    public Date getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(Date userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserImages() {
        return userImages;
    }

    public void setUserImages(String userImages) {
        this.userImages = userImages;
    }

    public Date getUserCreatedDate() {
        return userCreatedDate;
    }

    public void setUserCreatedDate(Date userCreatedDate) {
        this.userCreatedDate = userCreatedDate;
    }

    public Date getUserDeletedDate() {
        return userDeletedDate;
    }

    public void setUserDeletedDate(Date userDeletedDate) {
        this.userDeletedDate = userDeletedDate;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleId) {
        this.roleName = roleName;
    }

    public boolean isUserStatus() {
        return userStatus;
    }

    public void setUserStatus(boolean userStatus) {
        this.userStatus = userStatus;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }


}