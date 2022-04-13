package com.example.hackathon.data;

public class RequestData {

    String rno,donorName, donorMobile, donorAddress, donorCity, donorFoodDetails;

    public String getDonorName() {
        return donorName;
    }

    public RequestData setDonorName(String donorName) {
        this.donorName = donorName;
        return this;
    }

    public String getDonorMobile() {
        return donorMobile;
    }

    public RequestData setDonorMobile(String donorMobile) {
        this.donorMobile = donorMobile;
        return this;
    }

    public String getDonorAddress() {
        return donorAddress;
    }

    public RequestData setDonorAddress(String donorAddress) {
        this.donorAddress = donorAddress;
        return this;
    }

    public String getDonorCity() {
        return donorCity;
    }

    public RequestData setDonorCity(String donorCity) {
        this.donorCity = donorCity;
        return this;
    }

    public String getDonorFoodDetails() {
        return donorFoodDetails;
    }

    public RequestData setDonorFoodDetails(String donorFoodDetails) {
        this.donorFoodDetails = donorFoodDetails;
        return this;
    }

    public String getRno() {
        return rno;
    }

    public RequestData setRno(String rno) {
        this.rno = rno;
        return this;
    }

    public RequestData(String rno,String donorName, String donorMobile, String donorAddress, String donorCity, String donorFoodDetails) {
        this.rno = rno;
        this.donorName = donorName;
        this.donorMobile = donorMobile;
        this.donorAddress = donorAddress;
        this.donorCity = donorCity;
        this.donorFoodDetails = donorFoodDetails;
    }
}
