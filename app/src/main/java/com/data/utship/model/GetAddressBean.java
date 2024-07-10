package com.data.utship.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAddressBean {

    /**
     * status : 0
     * message : string
     * data : [{"id":0,"uid":0,"name":"string","mobile":"string","stateID":0,"cityID":0,"state":"string","city":"string","address":"string","landmark":"string","pincode":"string","addressType":"string"}]
     */

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

    public static class DataDTO {
        /**
         * id : 0
         * uid : 0
         * name : string
         * mobile : string
         * stateID : 0
         * cityID : 0
         * state : string
         * city : string
         * address : string
         * landmark : string
         * pincode : string
         * addressType : string
         */

        @SerializedName("id")
        private Integer id;
        @SerializedName("uid")
        private Integer uid;
        @SerializedName("name")
        private String name;
        @SerializedName("mobile")
        private String mobile;
        @SerializedName("stateID")
        private Integer stateID;
        @SerializedName("cityID")
        private Integer cityID;
        @SerializedName("state")
        private String state;
        @SerializedName("city")
        private String city;
        @SerializedName("address")
        private String address;
        @SerializedName("landmark")
        private String landmark;
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

        public Integer getUid() {
            return uid;
        }

        public void setUid(Integer uid) {
            this.uid = uid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public Integer getStateID() {
            return stateID;
        }

        public void setStateID(Integer stateID) {
            this.stateID = stateID;
        }

        public Integer getCityID() {
            return cityID;
        }

        public void setCityID(Integer cityID) {
            this.cityID = cityID;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLandmark() {
            return landmark;
        }

        public void setLandmark(String landmark) {
            this.landmark = landmark;
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
