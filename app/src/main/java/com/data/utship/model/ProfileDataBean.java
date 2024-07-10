package com.data.utship.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProfileDataBean  implements Serializable{

    /**
     * status : 0
     * message : string
     * data : {"id":0,"name":"string","email":"string","mobile":"string","password":"string","addDate":"string","profileIMG":"string"}
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

    public static class DataDTO implements Serializable {
        /**
         * id : 0
         * name : string
         * email : string
         * mobile : string
         * password : string
         * addDate : string
         * profileIMG : string
         */

        @SerializedName("id")
        private Integer id;
        @SerializedName("name")
        private String name;
        @SerializedName("email")
        private String email;
        @SerializedName("mobile")
        private String mobile;
        @SerializedName("password")
        private String password;
        @SerializedName("addDate")
        private String addDate;
        @SerializedName("profileIMG")
        private String profileIMG;

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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getAddDate() {
            return addDate;
        }

        public void setAddDate(String addDate) {
            this.addDate = addDate;
        }

        public String getProfileIMG() {
            return profileIMG;
        }

        public void setProfileIMG(String profileIMG) {
            this.profileIMG = profileIMG;
        }
    }
}
