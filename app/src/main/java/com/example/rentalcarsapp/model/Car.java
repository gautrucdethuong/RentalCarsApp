package com.example.rentalcarsapp.model;

import java.util.Date;

public class Car {
    private int carId;
    private String carName;
    private int brandId;
    private int carTypeId;
    private int storeId;
    private double carPrice;
    private String carColor;
    private Date carCreatedDate;
    private Date carDeleteDate;
    private String carLicensePlates;
    private int carSeat;
    private boolean carStatus;
    private String carDescription;
    private String carImage;
    private int staffId;


    public int getCarId() {
        return this.carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getCarName() {
        return this.carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public double getCarPrice() {
        return this.carPrice;
    }

    public void setCarPrice(double carPrice) {
        this.carPrice = carPrice;
    }

    public String getCarColor() {
        return this.carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public Date getCarCreatedDate() {
        return this.carCreatedDate;
    }

    public void setCarCreatedDate(Date carCreatedDate) {
        this.carCreatedDate = carCreatedDate;
    }

    public Date getCarDeleteDate() {
        return this.carDeleteDate;
    }

    public void setCarDeleteDate(Date carDeleteDate) {
        this.carDeleteDate = carDeleteDate;
    }

    public String getCarLicensePlates() {
        return this.carLicensePlates;
    }

    public void setCarLicensePlates(String aCarLicensePlates) {
        this.carLicensePlates = aCarLicensePlates;
    }

    public int getCarSeat() {
        return this.carSeat;
    }
    public void setCarSeat(int aCarSeat) {
        this.carSeat = aCarSeat;
    }
    public Car() {
    }
    public boolean getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(boolean carStatus) {
        this.carStatus = carStatus;
    }

    public String getCarDescription() {
        return this.carDescription;
    }

    public void setCarDescription(String carDescription) {
        this.carDescription = carDescription;
    }

    public String getCarImage() {
        return this.carImage;
    }

    public void setCarImage(String carImage) {
        this.carImage = carImage;
    }

    public int getDetailsCar(int carId) {
        return carId;
    }
    public void receiveAds(){
        
    }

}