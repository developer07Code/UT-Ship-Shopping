package com.data.utship.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchProductBean {

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
        @SerializedName("id")
        private Integer id;
        @SerializedName("cid")
        private Integer cid;
        @SerializedName("scid")
        private Integer scid;
        @SerializedName("sscid")
        private Integer sscid;
        @SerializedName("name")
        private String name;
        @SerializedName("description")
        private String description;
        @SerializedName("skuid")
        private String skuid;
        @SerializedName("img")
        private String img;
        @SerializedName("mrp")
        private String mrp;
        @SerializedName("price")
        private String price;
        @SerializedName("discountType")
        private String discountType;
        @SerializedName("discount")
        private String discount;
        @SerializedName("rating")
        private String rating;
        @SerializedName("isStock")
        private Integer isStock;
        @SerializedName("isWishlist")
        private Integer isWishlist;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getCid() {
            return cid;
        }

        public void setCid(Integer cid) {
            this.cid = cid;
        }

        public Integer getScid() {
            return scid;
        }

        public void setScid(Integer scid) {
            this.scid = scid;
        }

        public Integer getSscid() {
            return sscid;
        }

        public void setSscid(Integer sscid) {
            this.sscid = sscid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getSkuid() {
            return skuid;
        }

        public void setSkuid(String skuid) {
            this.skuid = skuid;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getMrp() {
            return mrp;
        }

        public void setMrp(String mrp) {
            this.mrp = mrp;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDiscountType() {
            return discountType;
        }

        public void setDiscountType(String discountType) {
            this.discountType = discountType;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public Integer getIsStock() {
            return isStock;
        }

        public void setIsStock(Integer isStock) {
            this.isStock = isStock;
        }

        public Integer getIsWishlist() {
            return isWishlist;
        }

        public void setIsWishlist(Integer isWishlist) {
            this.isWishlist = isWishlist;
        }
    }
}
