package com.data.utship.model;

import com.google.gson.annotations.SerializedName;

public class GetBalanceBean {

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
        @SerializedName("balance")
        private String balance;
        @SerializedName("totalCart")
        private String totalCart;

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getTotalCart() {
            return totalCart;
        }

        public void setTotalCart(String totalCart) {
            this.totalCart = totalCart;
        }
    }
}
