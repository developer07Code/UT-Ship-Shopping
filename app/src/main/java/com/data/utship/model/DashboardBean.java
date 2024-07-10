package com.data.utship.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DashboardBean implements Serializable {

    /**
     * status : 0
     * message : string
     * data : {"sliders":[{"id":0,"img":"string"}],"categories":[{"id":0,"name":"string","img":"string","description":"string"}],"products":[{"id":0,"cid":0,"scid":0,"sscid":0,"name":"string","description":"string","skuid":"string","img":"string","mrp":"string","price":"string","discountType":"string","discount":"string","rating":"string","isStock":0,"isWishlist":0}],"dealofDay":[{"id":0,"cid":0,"scid":0,"sscid":0,"name":"string","description":"string","skuid":"string","img":"string","mrp":"string","price":"string","discountType":"string","discount":"string","rating":"string","isStock":0,"isWishlist":0}],"findStyle":[{"id":0,"name":"string","products":[{"id":0,"cid":0,"scid":0,"sscid":0,"name":"string","description":"string","skuid":"string","img":"string","mrp":"string","price":"string","discountType":"string","discount":"string","rating":"string","isStock":0,"isWishlist":0}]}],"banner1":"string","totalCart":"string"}
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
         * sliders : [{"id":0,"img":"string"}]
         * categories : [{"id":0,"name":"string","img":"string","description":"string"}]
         * products : [{"id":0,"cid":0,"scid":0,"sscid":0,"name":"string","description":"string","skuid":"string","img":"string","mrp":"string","price":"string","discountType":"string","discount":"string","rating":"string","isStock":0,"isWishlist":0}]
         * dealofDay : [{"id":0,"cid":0,"scid":0,"sscid":0,"name":"string","description":"string","skuid":"string","img":"string","mrp":"string","price":"string","discountType":"string","discount":"string","rating":"string","isStock":0,"isWishlist":0}]
         * findStyle : [{"id":0,"name":"string","products":[{"id":0,"cid":0,"scid":0,"sscid":0,"name":"string","description":"string","skuid":"string","img":"string","mrp":"string","price":"string","discountType":"string","discount":"string","rating":"string","isStock":0,"isWishlist":0}]}]
         * banner1 : string
         * totalCart : string
         */

        @SerializedName("banner1")
        private String banner1;



        @SerializedName("banner2")
        private String banner2;
        @SerializedName("totalCart")
        private String totalCart;
        @SerializedName("sliders")
        private List<SlidersDTO> sliders;
        @SerializedName("categories")
        private List<CategoriesDTO> categories;
        @SerializedName("products")
        private List<ProductsDTO> products;
        @SerializedName("dealofDay")
        private List<DealofDayDTO> dealofDay;
        @SerializedName("findStyle")
        private List<FindStyleDTO> findStyle;

        public String getBanner2() {
            return banner2;
        }

        public void setBanner2(String banner2) {
            this.banner2 = banner2;
        }
        public String getBanner1() {
            return banner1;
        }

        public void setBanner1(String banner1) {
            this.banner1 = banner1;
        }

        public String getTotalCart() {
            return totalCart;
        }

        public void setTotalCart(String totalCart) {
            this.totalCart = totalCart;
        }

        public List<SlidersDTO> getSliders() {
            return sliders;
        }

        public void setSliders(List<SlidersDTO> sliders) {
            this.sliders = sliders;
        }

        public List<CategoriesDTO> getCategories() {
            return categories;
        }

        public void setCategories(List<CategoriesDTO> categories) {
            this.categories = categories;
        }

        public List<ProductsDTO> getProducts() {
            return products;
        }

        public void setProducts(List<ProductsDTO> products) {
            this.products = products;
        }

        public List<DealofDayDTO> getDealofDay() {
            return dealofDay;
        }

        public void setDealofDay(List<DealofDayDTO> dealofDay) {
            this.dealofDay = dealofDay;
        }

        public List<FindStyleDTO> getFindStyle() {
            return findStyle;
        }

        public void setFindStyle(List<FindStyleDTO> findStyle) {
            this.findStyle = findStyle;
        }

        public static class SlidersDTO implements Serializable{
            /**
             * id : 0
             * img : string
             */

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

        public static class CategoriesDTO implements Serializable{
            /**
             * id : 0
             * name : string
             * img : string
             * description : string
             */

            @SerializedName("id")
            private Integer id;
            @SerializedName("name")
            private String name;
            @SerializedName("img")
            private String img;
            @SerializedName("description")
            private String description;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }

        public static class ProductsDTO implements Serializable{
            /**
             * id : 0
             * cid : 0
             * scid : 0
             * sscid : 0
             * name : string
             * description : string
             * skuid : string
             * img : string
             * mrp : string
             * price : string
             * discountType : string
             * discount : string
             * rating : string
             * isStock : 0
             * isWishlist : 0
             */

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

        public static class DealofDayDTO implements Serializable{
            /**
             * id : 0
             * cid : 0
             * scid : 0
             * sscid : 0
             * name : string
             * description : string
             * skuid : string
             * img : string
             * mrp : string
             * price : string
             * discountType : string
             * discount : string
             * rating : string
             * isStock : 0
             * isWishlist : 0
             */

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


            @SerializedName("category")
            private String category;
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
            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
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

        public static class FindStyleDTO implements Serializable{
            /**
             * id : 0
             * name : string
             * products : [{"id":0,"cid":0,"scid":0,"sscid":0,"name":"string","description":"string","skuid":"string","img":"string","mrp":"string","price":"string","discountType":"string","discount":"string","rating":"string","isStock":0,"isWishlist":0}]
             */

            @SerializedName("id")
            private Integer id;
            @SerializedName("name")
            private String name;
            @SerializedName("products")
            private List<ProductsDTO> products;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<ProductsDTO> getProducts() {
                return products;
            }

            public void setProducts(List<ProductsDTO> products) {
                this.products = products;
            }

            public static class ProductsDTO implements Serializable{
                /**
                 * id : 0
                 * cid : 0
                 * scid : 0
                 * sscid : 0
                 * name : string
                 * description : string
                 * skuid : string
                 * img : string
                 * mrp : string
                 * price : string
                 * discountType : string
                 * discount : string
                 * rating : string
                 * isStock : 0
                 * isWishlist : 0
                 */

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
}
