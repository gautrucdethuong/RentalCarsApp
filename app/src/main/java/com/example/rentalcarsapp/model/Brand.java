package com.example.rentalcarsapp.model;

import java.util.Date;

/**
 * Author by HUYNH NHAT MINH (ミン).
 * Email: minhhnce140197@fpt.edu.vn.
 * Date on 6/30/2021.
 * Company: FPT大学.
 */

public class Brand {
    private String brandId;
    private String brandName;
    private Date brandCreatedDate;
    private Date brandDeletedDate;

    public Brand(){

    }

    public Brand(String brandId, String brandName, Date brandCreatedDate, Date brandDeletedDate) {
        this.brandId = brandId;
        this.brandName = brandName;
        this.brandCreatedDate = brandCreatedDate;
        this.brandDeletedDate = brandDeletedDate;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Date getBrandCreatedDate() {
        return brandCreatedDate;
    }

    public void setBrandCreatedDate(Date brandCreatedDate) {
        this.brandCreatedDate = brandCreatedDate;
    }

    public Date getBrandDeletedDate() {
        return brandDeletedDate;
    }

    public void setBrandDeletedDate(Date brandDeletedDate) {
        this.brandDeletedDate = brandDeletedDate;
    }
}
