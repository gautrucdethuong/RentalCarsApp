package com.example.rentalcarsapp.model;

import java.util.Date;

/**
 * Author by HUYNH NHAT MINH (ミン).
 * Email: minhhnce140197@fpt.edu.vn.
 * Date on 6/30/2021.
 * Company: FPT大学.
 */

public class Booking {
    public Date bookingPickUpDate;//declare variable bookingPickUpDate
    public Date bookingDropOffDate;//declare variable bookingDropOffDate
    public float bookingTotal;//declare variable bookingTotal
    public int bookingStatus;//declare variable bookingStatus
    public String carId;//declare variable carId
    public String userId;//declare variable userId
    public String bookingId;//declare variable bookingId
    /**
     * Create a constructor with no parameters
     */
    public Booking() {

    }

    /**
     * Create a constructor with full parameters
     * @param bookingPickUpDate pickup date of booking to store in database
     * @param bookingDropOffDate drop off date of booking to store in database
     * @param bookingTotal total of booking to store in database
     * @param bookingStatus status of booking to store in database
     * @param carId id of car to store in database
     * @param userId id of user to store in database
     * @param bookingId id of booking to store in database
     */
    public Booking(Date bookingPickUpDate, Date bookingDropOffDate, float bookingTotal, int bookingStatus, String carId, String userId, String bookingId) {
        this.bookingPickUpDate = bookingPickUpDate;
        this.bookingDropOffDate = bookingDropOffDate;
        this.bookingTotal = bookingTotal;
        this.bookingStatus = bookingStatus;
        this.carId=carId;
        this.userId=userId;
        this.bookingId=bookingId;
    }

    /**
     * Get id of user
     * @return userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Set id of user
     * @param userId id of user to store in database
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Get id of car
     * @return carId
     */
    public String getCarId() {
        return carId;
    }

    /**
     * Set id of car
     * @param carId id of car to store in database
     */
    public void setCarId(String carId) {
        this.carId = carId;
    }

    /**
     * Get pickup date of booking
     * @return bookingPickUpDate
     */
    public Date getBookingPickUpDate() {
        return bookingPickUpDate;
    }

    /**
     * Set pickup date of booking
     * @param bookingPickUpDate pickup date of booking to store in database
     */
    public void setBookingPickUpDate(Date bookingPickUpDate) {
        this.bookingPickUpDate = bookingPickUpDate;
    }

    /**
     * Get drop off date of booking
     * @return bookingDropOffDate
     */
    public Date getBookingDropOffDate() {
        return bookingDropOffDate;
    }

    /**
     * Set drop off date off booking
     * @param bookingDropOffDate drop off date of booking to store in database
     */
    public void setBookingDropOffDate(Date bookingDropOffDate) {
        this.bookingDropOffDate = bookingDropOffDate;
    }

    /**
     * Get total of booking
     * @return bookingTotal
     */
    public float getBookingTotal() {
        return bookingTotal;
    }

    /**
     * Set total of booking
     * @param bookingTotal total of booking to store in database
     */
    public void setBookingTotal(float bookingTotal) {
        this.bookingTotal = bookingTotal;
    }

    /**
     * Get status of booking
     * @return bookingStatus
     */
    public int getBookingStatus() {
        return bookingStatus;
    }

    /**
     * Set status of booking
     * @param bookingStatus status of booking to store in database
     */
    public void setBookingStatus(int bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    /**
     * Get id of booking
     * @return bookingId
     */
    public String getBookingId() {
        return bookingId;
    }

    /**
     * Set id of booking
     * @param bookingId id of booking to store in database
     */
    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }
}
