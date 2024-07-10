package com.data.utship.model;

import com.google.gson.annotations.SerializedName;

public class TotalCartBean {

    @SerializedName("status")
    private Integer status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private DataDTO data;

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

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public static class DataDTO {
        @SerializedName("totalAmount")
        private String totalAmount;
        @SerializedName("delivery")
        private String delivery;
        @SerializedName("useWallet")
        private String useWallet;
        @SerializedName("netAmount")
        private String netAmount;

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getDelivery() {
            return delivery;
        }

        public void setDelivery(String delivery) {
            this.delivery = delivery;
        }

        public String getUseWallet() {
            return useWallet;
        }

        public void setUseWallet(String useWallet) {
            this.useWallet = useWallet;
        }

        public String getNetAmount() {
            return netAmount;
        }

        public void setNetAmount(String netAmount) {
            this.netAmount = netAmount;
        }
    }
}
