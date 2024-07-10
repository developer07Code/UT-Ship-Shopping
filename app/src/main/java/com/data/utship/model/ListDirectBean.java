package com.data.utship.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListDirectBean {

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
        @SerializedName("introName")
        private String introName;
        @SerializedName("introMobile")
        private String introMobile;
        @SerializedName("introProfile")
        private String introProfile;
        @SerializedName("introDOJ")
        private String introDOJ;

        public String getIntroName() {
            return introName;
        }

        public void setIntroName(String introName) {
            this.introName = introName;
        }

        public String getIntroMobile() {
            return introMobile;
        }

        public void setIntroMobile(String introMobile) {
            this.introMobile = introMobile;
        }

        public String getIntroProfile() {
            return introProfile;
        }

        public void setIntroProfile(String introProfile) {
            this.introProfile = introProfile;
        }

        public String getIntroDOJ() {
            return introDOJ;
        }

        public void setIntroDOJ(String introDOJ) {
            this.introDOJ = introDOJ;
        }
    }
}
