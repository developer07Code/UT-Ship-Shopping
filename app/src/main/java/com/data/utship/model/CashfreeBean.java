package com.data.utship.model;

import com.google.gson.annotations.SerializedName;

public class CashfreeBean {

    /**
     * status : 1
     * message : Your Order Successfully
     * token : null
     * type : null
     * order_id : null
     * cf_order_id : null
     * payment_session_id : null
     * isPay : 0
     */

    @SerializedName("status")
    private Integer status;
    @SerializedName("message")
    private String message;
    @SerializedName("token")
    private Object token;
    @SerializedName("type")
    private Object type;
    @SerializedName("order_id")
    private Object orderId;
    @SerializedName("cf_order_id")
    private Object cfOrderId;
    @SerializedName("payment_session_id")
    private Object paymentSessionId;
    @SerializedName("isPay")
    private Integer isPay;

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

    public Object getToken() {
        return token;
    }

    public void setToken(Object token) {
        this.token = token;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public Object getOrderId() {
        return orderId;
    }

    public void setOrderId(Object orderId) {
        this.orderId = orderId;
    }

    public Object getCfOrderId() {
        return cfOrderId;
    }

    public void setCfOrderId(Object cfOrderId) {
        this.cfOrderId = cfOrderId;
    }

    public Object getPaymentSessionId() {
        return paymentSessionId;
    }

    public void setPaymentSessionId(Object paymentSessionId) {
        this.paymentSessionId = paymentSessionId;
    }

    public Integer getIsPay() {
        return isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }
}
