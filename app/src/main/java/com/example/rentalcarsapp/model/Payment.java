package com.example.rentalcarsapp.model;

import java.util.Date;

/**
 * Author by HUYNH NHAT MINH (ミン).
 * Email: minhhnce140197@fpt.edu.vn.
 * Date on 6/30/2021.
 * Company: FPT大学.
 */

public class Payment {
    private int paymentId;
    private String paymentName;
    private int Status;
    private Date paymentCreatedDate;
    private Date paymentDeletedDate;

    public Payment() {

    }

    public Payment(int paymentId, String paymentName, int status, Date paymentCreatedDate, Date paymentDeletedDate) {
        this.paymentId = paymentId;
        this.paymentName = paymentName;
        Status = status;
        this.paymentCreatedDate = paymentCreatedDate;
        this.paymentDeletedDate = paymentDeletedDate;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public Date getPaymentCreatedDate() {
        return paymentCreatedDate;
    }

    public void setPaymentCreatedDate(Date paymentCreatedDate) {
        this.paymentCreatedDate = paymentCreatedDate;
    }

    public Date getPaymentDeletedDate() {
        return paymentDeletedDate;
    }

    public void setPaymentDeletedDate(Date paymentDeletedDate) {
        this.paymentDeletedDate = paymentDeletedDate;
    }
}
