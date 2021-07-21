package com.example.rentalcarsapp.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Author by HUYNH NHAT MINH (ミン).
 * Email: minhhnce140197@fpt.edu.vn.
 * Date on 6/29/2021.
 * Company: FPT大学.
 */

public class Car implements Serializable {
    private int carId;
    private String carName;
    private float carPrice;
    private String carColor;
    private String carLicensePlates;
    private String carSeat;
    private String carImage;
    private float carRating;
    private String carBrand;
    private int carStatus;
    private String carDescription;
    private Date carCreatedDate;
    private Date carDeletedDate;

    public int getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(int carStatus) {
        this.carStatus = carStatus;
    }

    public void setCarStatus(int carStatus) {
        this.carStatus = carStatus;
    }
    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public float getCarRating() {
        return carRating;
    }

    public void setCarRating(float carRating) {
        this.carRating = carRating;
    }



    public Car(){

    }

    public Car(int carId, String carName, float carPrice, String carColor, String carLicensePlates, String carSeat, String carImage, String carDescription, Date carCreatedDate, Date carDeletedDate, float carRating, String carBrand, int carStatus) {
        this.carId = carId;
        this.carName = carName;
        this.carPrice = carPrice;
        this.carColor = carColor;
        this.carLicensePlates = carLicensePlates;
        this.carSeat = carSeat;
        this.carImage = carImage;
        this.carDescription = carDescription;
        this.carCreatedDate = carCreatedDate;
        this.carDeletedDate = carDeletedDate;
        this.carRating = carRating;
        this.carBrand = carBrand;
        this.carStatus = carStatus;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public float getCarPrice() {
        return carPrice;
    }

    public void setCarPrice(float carPrice) {
        this.carPrice = carPrice;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getCarLicensePlates() {
        return carLicensePlates;
    }

    public void setCarLicensePlates(String carLicensePlates) {
        this.carLicensePlates = carLicensePlates;
    }

    public String getCarSeat() {
        return carSeat;
    }

    public void setCarSeat(String carSeat) {
        this.carSeat = carSeat;
    }

    public String getCarImage() {
        return carImage;
    }

    public void setCarImage(String carImage) {
        this.carImage = carImage;
    }

    public String getCarDescription() {
        return carDescription;
    }

    public void setCarDescription(String carDescription) {
        this.carDescription = carDescription;
    }

    public Date getCarCreatedDate() {
        return carCreatedDate;
    }

    public void setCarCreatedDate(Date carCreatedDate) {
        this.carCreatedDate = carCreatedDate;
    }

    public Date getCarDeletedDate() {
        return carDeletedDate;
    }

    public void setCarDeletedDate(Date carDeletedDate) {
        this.carDeletedDate = carDeletedDate;
    }
}
