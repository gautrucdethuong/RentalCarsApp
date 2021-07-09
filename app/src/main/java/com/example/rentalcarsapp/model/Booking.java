package com.example.rentalcarsapp.model;

import java.util.Date;

/**
 * Author by HUYNH NHAT MINH (ミン).
 * Email: minhhnce140197@fpt.edu.vn.
 * Date on 6/30/2021.
 * Company: FPT大学.
 */

public class Booking {
    public Date bookingPickUpDate;
    public Date bookingDropOffDate;
    public Double bookingTotal;
    public int bookingStatus;
    public String carId;
    public String userId;
    public String bookingId;

    public Booking() {

    }



    public Booking(Date bookingPickUpDate, Date bookingDropOffDate, Double bookingTotal, int bookingStatus, String carId, String userId) {
        this.bookingPickUpDate = bookingPickUpDate;
        this.bookingDropOffDate = bookingDropOffDate;
        this.bookingTotal = bookingTotal;
        this.bookingStatus = bookingStatus;
        this.carId=carId;
        this.userId=userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public Date getBookingPickUpDate() {
        return bookingPickUpDate;
    }

    public void setBookingPickUpDate(Date bookingPickUpDate) {
        this.bookingPickUpDate = bookingPickUpDate;
    }

    public Date getBookingDropOffDate() {
        return bookingDropOffDate;
    }

    public void setBookingDropOffDate(Date bookingDropOffDate) {
        this.bookingDropOffDate = bookingDropOffDate;
    }

    public Double getBookingTotal() {
        return bookingTotal;
    }

    public void setBookingTotal(Double bookingTotal) {
        this.bookingTotal = bookingTotal;
    }

    public int getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(int bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }
}
