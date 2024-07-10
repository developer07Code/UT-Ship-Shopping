package com.data.utship.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAllCategoryBean {

    /**
     * status : 1
     * message : Success
     * data : [{"id":51,"cid":36,"name":"All sarees","img":"https://auth.casheze.co.in/Upload/Category/51_SubCAT.jpg","description":"","subToSubCategories":[{"id":3,"cid":36,"scid":51,"name":"Georgette Sarees","img":"https://auth.casheze.co.in/Upload/Category/3_SubToSubCAT.jpg","description":""},{"id":4,"cid":36,"scid":51,"name":"Cotton Sarees","img":"https://auth.casheze.co.in/Upload/Category/4_SubToSubCAT.jpg","description":""},{"id":5,"cid":36,"scid":51,"name":"Net Sarees","img":"https://auth.casheze.co.in/Upload/Category/5_SubToSubCAT.jpg","description":""},{"id":6,"cid":36,"scid":51,"name":"Silk Sarees","img":"https://auth.casheze.co.in/Upload/Category/6_SubToSubCAT.jpg","description":""},{"id":7,"cid":36,"scid":51,"name":"Bridal Sarees","img":"https://auth.casheze.co.in/Upload/Category/7_SubToSubCAT.jpg","description":""}]},{"id":52,"cid":36,"name":"Kurtis","img":"https://auth.casheze.co.in/Upload/Category/52_SubCAT.jpg","description":"","subToSubCategories":[{"id":8,"cid":36,"scid":52,"name":"Anarkali Kurtis","img":"https://auth.casheze.co.in/Upload/Category/8_SubToSubCAT.jpg","description":""},{"id":9,"cid":36,"scid":52,"name":"Cotton Kurtis","img":"https://auth.casheze.co.in/Upload/Category/9_SubToSubCAT.jpg","description":""},{"id":10,"cid":36,"scid":52,"name":"Straight Kurtis","img":"https://auth.casheze.co.in/Upload/Category/10_SubToSubCAT.jpg","description":""},{"id":11,"cid":36,"scid":52,"name":"Long Kurtis","img":"https://auth.casheze.co.in/Upload/Category/11_SubToSubCAT.jpg","description":""}]},{"id":53,"cid":36,"name":"Kurta Sets","img":"https://auth.casheze.co.in/Upload/Category/53_SubCAT.jpg","description":"","subToSubCategories":[{"id":12,"cid":36,"scid":53,"name":"Kurta Palazzo Sets","img":"https://auth.casheze.co.in/Upload/Category/12_SubToSubCAT.jpg","description":""},{"id":13,"cid":36,"scid":53,"name":"Kurta Pant Sets","img":"https://auth.casheze.co.in/Upload/Category/13_SubToSubCAT.jpg","description":""},{"id":14,"cid":36,"scid":53,"name":"Sharara Sets","img":"https://auth.casheze.co.in/Upload/Category/14_SubToSubCAT.jpg","description":""},{"id":15,"cid":36,"scid":53,"name":"Anarkali Kurta Sets","img":"https://auth.casheze.co.in/Upload/Category/15_SubToSubCAT.jpg","description":""},{"id":16,"cid":36,"scid":53,"name":"Cotton Kurta Sets","img":"https://auth.casheze.co.in/Upload/Category/16_SubToSubCAT.jpg","description":""}]},{"id":54,"cid":36,"name":"Dupatta Sets","img":"https://auth.casheze.co.in/Upload/Category/54_SubCAT.jpg","description":"","subToSubCategories":[{"id":17,"cid":36,"scid":54,"name":"Cotton Sets","img":"https://auth.casheze.co.in/Upload/Category/17_SubToSubCAT.jpg","description":""},{"id":18,"cid":36,"scid":54,"name":"Rayon Sets","img":"https://auth.casheze.co.in/Upload/Category/18_SubToSubCAT.jpg","description":""},{"id":19,"cid":36,"scid":54,"name":"Suits & Dress Material","img":"https://auth.casheze.co.in/Upload/Category/19_SubToSubCAT.jpg","description":""}]},{"id":55,"cid":36,"name":"Lehengas","img":"https://auth.casheze.co.in/Upload/Category/55_SubCAT.jpg","description":"","subToSubCategories":[{"id":20,"cid":36,"scid":55,"name":"Net Lehengas","img":"https://auth.casheze.co.in/Upload/Category/20_SubToSubCAT.jpg","description":""},{"id":21,"cid":36,"scid":55,"name":"Partywear Lehenga","img":"https://auth.casheze.co.in/Upload/Category/21_SubToSubCAT.jpg","description":""}]}]
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
         * id : 51
         * cid : 36
         * name : All sarees
         * img : https://auth.casheze.co.in/Upload/Category/51_SubCAT.jpg
         * description :
         * subToSubCategories : [{"id":3,"cid":36,"scid":51,"name":"Georgette Sarees","img":"https://auth.casheze.co.in/Upload/Category/3_SubToSubCAT.jpg","description":""},{"id":4,"cid":36,"scid":51,"name":"Cotton Sarees","img":"https://auth.casheze.co.in/Upload/Category/4_SubToSubCAT.jpg","description":""},{"id":5,"cid":36,"scid":51,"name":"Net Sarees","img":"https://auth.casheze.co.in/Upload/Category/5_SubToSubCAT.jpg","description":""},{"id":6,"cid":36,"scid":51,"name":"Silk Sarees","img":"https://auth.casheze.co.in/Upload/Category/6_SubToSubCAT.jpg","description":""},{"id":7,"cid":36,"scid":51,"name":"Bridal Sarees","img":"https://auth.casheze.co.in/Upload/Category/7_SubToSubCAT.jpg","description":""}]
         */

        @SerializedName("id")
        private Integer id;
        @SerializedName("cid")
        private Integer cid;
        @SerializedName("name")
        private String name;
        @SerializedName("img")
        private String img;
        @SerializedName("description")
        private String description;
        @SerializedName("subToSubCategories")
        private List<SubToSubCategoriesDTO> subToSubCategories;

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

        public List<SubToSubCategoriesDTO> getSubToSubCategories() {
            return subToSubCategories;
        }

        public void setSubToSubCategories(List<SubToSubCategoriesDTO> subToSubCategories) {
            this.subToSubCategories = subToSubCategories;
        }

        public static class SubToSubCategoriesDTO {
            /**
             * id : 3
             * cid : 36
             * scid : 51
             * name : Georgette Sarees
             * img : https://auth.casheze.co.in/Upload/Category/3_SubToSubCAT.jpg
             * description :
             */

            @SerializedName("id")
            private Integer id;
            @SerializedName("cid")
            private Integer cid;
            @SerializedName("scid")
            private Integer scid;
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
    }
}
