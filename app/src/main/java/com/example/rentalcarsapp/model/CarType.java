package com.example.rentalcarsapp.model;

import java.util.Date;

/**
 * Author by HUYNH NHAT MINH (ミン).
 * Email: minhhnce140197@fpt.edu.vn.
 * Date on 6/30/2021.
 * Company: FPT大学.
 */

public class CarType {
    private int carTypeId;
    private String carTypeName;
    private Boolean carTypeStatus;
    private Date carTypeCreatedDate;
    private Date carTypeDeletedDate;



    public CarType(int carTypeId, String carTypeName, Boolean carTypeStatus, Date carTypeCreatedDate, Date carTypeDeletedDate) {
        this.carTypeName = carTypeName;
        this.carTypeStatus = carTypeStatus;
        this.carTypeCreatedDate = carTypeCreatedDate;
        this.carTypeDeletedDate = carTypeDeletedDate;
        this.carTypeId = carTypeId;
    }
    public CarType(){

    }
    public int getCarTypeId() {
        return carTypeId;
    }

    public void setCarTypeId(int carTypeId) {
        this.carTypeId = carTypeId;
    }
    public String getCarTypeName() {
        return carTypeName;
    }

    public void setCarTypeName(String carTypeName) {
        this.carTypeName = carTypeName;
    }

    public Boolean getCarTypeStatus() {
        return carTypeStatus;
    }

    public void setCarTypeStatus(Boolean carTypeStatus) {
        this.carTypeStatus = carTypeStatus;
    }

    public Date getCarTypeCreatedDate() {
        return carTypeCreatedDate;
    }

    public void setCarTypeCreatedDate(Date carTypeCreatedDate) {
        this.carTypeCreatedDate = carTypeCreatedDate;
    }

    public Date getCarTypeDeletedDate() {
        return carTypeDeletedDate;
    }

    public void setCarTypeDeletedDate(Date carTypeDeletedDate) {
        this.carTypeDeletedDate = carTypeDeletedDate;
    }
}
