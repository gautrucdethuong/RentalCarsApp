package com.example.rentalcarsapp.dao;

public interface Callback{
    void isLogin(boolean status);

    void isLogin(String roleName);

     void isRegister(boolean status);

}