package com.data.utship.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class demo {

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
        @SerializedName("id")
        private Integer id;
        @SerializedName("category")
        private String category;
        @SerializedName("subCategory")
        private String subCategory;
        @SerializedName("subToSubCategory")
        private String subToSubCategory;
        @SerializedName("productname")
        private String productname;
        @SerializedName("description")
        private String description;
        @SerializedName("skuid")
        private String skuid;
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
        @SerializedName("imgs")
        private List<ImgsDTO> imgs;
        @SerializedName("size")
        private List<?> size;
        @SerializedName("color")
        private List<?> color;
        @SerializedName("material")
        private List<?> material;
        @SerializedName("style")
        private List<?> style;
        @SerializedName("reletedProduct")
        private List<ReletedProductDTO> reletedProduct;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getSubCategory() {
            return subCategory;
        }

        public void setSubCategory(String subCategory) {
            this.subCategory = subCategory;
        }

        public String getSubToSubCategory() {
            return subToSubCategory;
        }

        public void setSubToSubCategory(String subToSubCategory) {
            this.subToSubCategory = subToSubCategory;
        }

        public String getProductname() {
            return productname;
        }

        public void setProductname(String productname) {
            this.productname = productname;
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

        public List<ImgsDTO> getImgs() {
            return imgs;
        }

        public void setImgs(List<ImgsDTO> imgs) {
            this.imgs = imgs;
        }

        public List<?> getSize() {
            return size;
        }

        public void setSize(List<?> size) {
            this.size = size;
        }

        public List<?> getColor() {
            return color;
        }

        public void setColor(List<?> color) {
            this.color = color;
        }

        public List<?> getMaterial() {
            return material;
        }

        public void setMaterial(List<?> material) {
            this.material = material;
        }

        public List<?> getStyle() {
            return style;
        }

        public void setStyle(List<?> style) {
            this.style = style;
        }

        public List<ReletedProductDTO> getReletedProduct() {
            return reletedProduct;
        }

        public void setReletedProduct(List<ReletedProductDTO> reletedProduct) {
            this.reletedProduct = reletedProduct;
        }

        public static class ImgsDTO {
            @SerializedName("id")
            private Integer id;
            @SerializedName("img")
            private String img;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }

        public static class ReletedProductDTO {
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
}
