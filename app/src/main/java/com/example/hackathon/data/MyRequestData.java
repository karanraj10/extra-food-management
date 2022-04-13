package com.example.hackathon.data;

public class MyRequestData {

    String requestNo, ngoName, foodDetails, status, needyArea, needyContact;

    public String getRequestNo() {
        return requestNo;
    }

    public MyRequestData setRequestNo(String requestNo) {
        this.requestNo = requestNo;
        return this;
    }

    public String getNgoName() {
        return ngoName;
    }

    public MyRequestData setNgoName(String ngoName) {
        this.ngoName = ngoName;
        return this;
    }

    public String getFoodDetails() {
        return foodDetails;
    }

    public MyRequestData setFoodDetails(String foodDetails) {
        this.foodDetails = foodDetails;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public MyRequestData setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getNeedyArea() {
        return needyArea;
    }

    public MyRequestData setNeedyArea(String needyArea) {
        this.needyArea = needyArea;
        return this;
    }

    public String getNeedyContact() {
        return needyContact;
    }

    public MyRequestData setNeedyContact(String needyContact) {
        this.needyContact = needyContact;
        return this;
    }

    public MyRequestData(String requestNo, String ngoName, String foodDetails, String status, String needyArea, String needyContact) {
        this.requestNo = requestNo;
        this.ngoName = ngoName;
        this.foodDetails = foodDetails;
        this.status = status;
        this.needyArea = needyArea;
        this.needyContact = needyContact;
    }
}
