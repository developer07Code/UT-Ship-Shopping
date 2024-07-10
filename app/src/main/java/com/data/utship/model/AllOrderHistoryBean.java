package com.data.utship.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AllOrderHistoryBean implements Serializable {

    @SerializedName("status")
    private Integer status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<DataDTO> data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public static class DataDTO implements Serializable{
        @SerializedName("id")
        private Integer id;
        @SerializedName("orderID")
        private Integer orderID;
        @SerializedName("invoiceNo")
        private String invoiceNo;
        @SerializedName("img")
        private String img;
        @SerializedName("name")
        private String name;
        @SerializedName("skuid")
        private String skuid;
        @SerializedName("color")
        private String color;
        @SerializedName("size")
        private String size;
        @SerializedName("price")
        private String price;
        @SerializedName("qty")
        private String qty;
        @SerializedName("netAmount")
        private String netAmount;
        @SerializedName("adddate")
        private String adddate;
        @SerializedName("status")
        private String status;
        @SerializedName("paymentMode")
        private String paymentMode;
        @SerializedName("shippingName")
        private String shippingName;
        @SerializedName("shippingMobile")
        private String shippingMobile;
        @SerializedName("stateName")
        private String stateName;
        @SerializedName("cityName")
        private String cityName;
        @SerializedName("address")
        private String address;
        @SerializedName("pincode")
        private String pincode;
        @SerializedName("addressType")
        private String addressType;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getOrderID() {
            return orderID;
        }

        public void setOrderID(Integer orderID) {
            this.orderID = orderID;
        }

        public String getInvoiceNo() {
            return invoiceNo;
        }

        public void setInvoiceNo(String invoiceNo) {
            this.invoiceNo = invoiceNo;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSkuid() {
            return skuid;
        }

        public void setSkuid(String skuid) {
            this.skuid = skuid;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getNetAmount() {
            return netAmount;
        }

        public void setNetAmount(String netAmount) {
            this.netAmount = netAmount;
        }

        public String getAdddate() {
            return adddate;
        }

        public void setAdddate(String adddate) {
            this.adddate = adddate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPaymentMode() {
            return paymentMode;
        }

        public void setPaymentMode(String paymentMode) {
            this.paymentMode = paymentMode;
        }

        public String getShippingName() {
            return shippingName;
        }

        public void setShippingName(String shippingName) {
            this.shippingName = shippingName;
        }

        public String getShippingMobile() {
            return shippingMobile;
        }

        public void setShippingMobile(String shippingMobile) {
            this.shippingMobile = shippingMobile;
        }

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        public String getAddressType() {
            return addressType;
        }

        public void setAddressType(String addressType) {
            this.addressType = addressType;
        }
    }
}
