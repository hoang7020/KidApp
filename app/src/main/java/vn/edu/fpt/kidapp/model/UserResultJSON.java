package vn.edu.fpt.kidapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserResultJSON {
    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("data")
    @Expose
    private Data data;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("date_create")
        @Expose
        private String dateCreate;
        @SerializedName("address")
        @Expose
        private String address;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getDateCreate() {
            return dateCreate;
        }

        public void setDateCreate(String dateCreate) {
            this.dateCreate = dateCreate;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

    }

    public class Status {
        @SerializedName("code")
        @Expose
        private Integer code;
        @SerializedName("message")
        @Expose
        private String message;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}



