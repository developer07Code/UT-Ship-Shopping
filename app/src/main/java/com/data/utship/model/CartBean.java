package com.data.utship.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CartBean implements Serializable {

    /**
     * status : 1
     * message : Success
     * data : {"carts":[{"id":9,"pid":1,"product":"Plain Regular Fit Formal Shirt","img":"https://auth.casheze.co.in/Upload/Product/IMG_1_1.png","sizeID":3,"size":null,"colorID":1,"color":"#FF0000","qty":"2","price":"299.00","mrp":"500.00"},{"id":8,"pid":1,"product":"Plain Regular Fit Formal Shirt","img":"https://auth.casheze.co.in/Upload/Product/IMG_1_1.png","sizeID":3,"size":null,"colorID":2,"color":"#000000","qty":"2","price":"299.00","mrp":"500.00"}],"cartTotal":{"totalAmount":"598.00","delivery":"0","useWallet":"0","netAmount":"598.00"}}
     */

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

    public static class DataDTO implements Serializable{
        /**
         * carts : [{"id":9,"pid":1,"product":"Plain Regular Fit Formal Shirt","img":"https://auth.casheze.co.in/Upload/Product/IMG_1_1.png","sizeID":3,"size":null,"colorID":1,"color":"#FF0000","qty":"2","price":"299.00","mrp":"500.00"},{"id":8,"pid":1,"product":"Plain Regular Fit Formal Shirt","img":"https://auth.casheze.co.in/Upload/Product/IMG_1_1.png","sizeID":3,"size":null,"colorID":2,"color":"#000000","qty":"2","price":"299.00","mrp":"500.00"}]
         * cartTotal : {"totalAmount":"598.00","delivery":"0","useWallet":"0","netAmount":"598.00"}
         */

        @SerializedName("cartTotal")
        private CartTotalDTO cartTotal;
        @SerializedName("carts")
        private List<CartsDTO> carts;

        public CartTotalDTO getCartTotal() {
            return cartTotal;
        }

        public void setCartTotal(CartTotalDTO cartTotal) {
            this.cartTotal = cartTotal;
        }

        public List<CartsDTO> getCarts() {
            return carts;
        }

        public void setCarts(List<CartsDTO> carts) {
            this.carts = carts;
        }

        public static class CartTotalDTO implements Serializable{
            /**
             * totalAmount : 598.00
             * delivery : 0
             * useWallet : 0
             * netAmount : 598.00
             */

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

        public static class CartsDTO implements Serializable {
            /**
             * id : 9
             * pid : 1
             * product : Plain Regular Fit Formal Shirt
             * img : https://auth.casheze.co.in/Upload/Product/IMG_1_1.png
             * sizeID : 3
             * size : null
             * colorID : 1
             * color : #FF0000
             * qty : 2
             * price : 299.00
             * mrp : 500.00
             */

            @SerializedName("id")
            private Integer id;
            @SerializedName("pid")
            private Integer pid;
            @SerializedName("product")
            private String product;
            @SerializedName("img")
            private String img;
            @SerializedName("sizeID")
            private Integer sizeID;
            @SerializedName("size")
            private String size;
            @SerializedName("colorID")
            private Integer colorID;
            @SerializedName("color")
            private String color;
            @SerializedName("qty")
            private String qty;
            @SerializedName("price")
            private String price;
            @SerializedName("mrp")
            private String mrp;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public Integer getPid() {
                return pid;
            }

            public void setPid(Integer pid) {
                this.pid = pid;
            }

            public String getProduct() {
                return product;
            }

            public void setProduct(String product) {
                this.product = product;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public Integer getSizeID() {
                return sizeID;
            }

            public void setSizeID(Integer sizeID) {
                this.sizeID = sizeID;
            }

            public String getSize() {
                return size;
            }

            public void setSize(String size) {
                this.size = size;
            }

            public Integer getColorID() {
                return colorID;
            }

            public void setColorID(Integer colorID) {
                this.colorID = colorID;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public String getQty() {
                return qty;
            }

            public void setQty(String qty) {
                this.qty = qty;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getMrp() {
                return mrp;
            }

            public void setMrp(String mrp) {
                this.mrp = mrp;
            }
        }
    }
}
