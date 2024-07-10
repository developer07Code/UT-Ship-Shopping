package com.data.utship.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StateBean {

    /**
     * status : 1
     * message : Success
     * data : [{"id":1,"name":"ANDAMAN & NICOBAR"},{"id":2,"name":"ANDHRA PRADESH"},{"id":3,"name":"ARUNACHAL PRADESH"},{"id":4,"name":"ASSAM"},{"id":5,"name":"BIHAR"},{"id":6,"name":"CHANDIGARH"},{"id":7,"name":"CHHATTISGARH"},{"id":8,"name":"DELHI"},{"id":9,"name":"GOA"},{"id":10,"name":"GUJARAT"},{"id":11,"name":"HARYANA"},{"id":12,"name":"HIMACHAL PRADESH"},{"id":13,"name":"JAMMU & KASHMIR"},{"id":14,"name":"JHARKAND"},{"id":15,"name":"KARNATAKA"},{"id":16,"name":"KERALA"},{"id":17,"name":"MADHYA PRADESH"},{"id":18,"name":"MAHARASHTRA"},{"id":19,"name":"MANIPUR"},{"id":20,"name":"MEGHALAYA"},{"id":21,"name":"MIZORAM"},{"id":22,"name":"NAGALAND"},{"id":23,"name":"ODISHA"},{"id":24,"name":"PONDICHERRY"},{"id":25,"name":"PUNJAB"},{"id":26,"name":"RAJASTHAN"},{"id":27,"name":"SIKKIM"},{"id":28,"name":"TAMIL NADU"},{"id":29,"name":"TRIPURA"},{"id":30,"name":"UTTAR PRADESH"},{"id":31,"name":"UTTARANCHAL"},{"id":32,"name":"WEST BENGAL"},{"id":33,"name":"NEWDELHI"}]
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
         * id : 1
         * name : ANDAMAN & NICOBAR
         */

        @SerializedName("id")
        private Integer id;
        @SerializedName("name")
        private String name;

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
    }
}
