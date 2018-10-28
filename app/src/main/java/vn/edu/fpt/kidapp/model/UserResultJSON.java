package vn.edu.fpt.kidapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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
        @SerializedName("Pictures")
        @Expose
        private List<Picture> pictures = null;

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

        public List<Picture> getPictures() {
            return pictures;
        }

        public void setPictures(List<Picture> pictures) {
            this.pictures = pictures;
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

    public class Picture {

        @SerializedName("image_id")
        @Expose
        private String imageId;
        @SerializedName("image_name")
        @Expose
        private String imageName;
        @SerializedName("time_shoot")
        @Expose
        private Double timeShoot;
        @SerializedName("eng_sub")
        @Expose
        private EngSub engSub;
        @SerializedName("vie_sub")
        @Expose
        private VieSub vieSub;

        public String getImageId() {
            return imageId;
        }

        public void setImageId(String imageId) {
            this.imageId = imageId;
        }

        public String getImageName() {
            return imageName;
        }

        public void setImageName(String imageName) {
            this.imageName = imageName;
        }

        public Double getTimeShoot() {
            return timeShoot;
        }

        public void setTimeShoot(Double timeShoot) {
            this.timeShoot = timeShoot;
        }

        public EngSub getEngSub() {
            return engSub;
        }

        public void setEngSub(EngSub engSub) {
            this.engSub = engSub;
        }

        public VieSub getVieSub() {
            return vieSub;
        }

        public void setVieSub(VieSub vieSub) {
            this.vieSub = vieSub;
        }

    }

    public class EngSub {

        @SerializedName("eng_1")
        @Expose
        private String eng1;
        @SerializedName("eng_2")
        @Expose
        private String eng2;
        @SerializedName("eng_3")
        @Expose
        private String eng3;

        public String getEng1() {
            return eng1;
        }

        public void setEng1(String eng1) {
            this.eng1 = eng1;
        }

        public String getEng2() {
            return eng2;
        }

        public void setEng2(String eng2) {
            this.eng2 = eng2;
        }

        public String getEng3() {
            return eng3;
        }

        public void setEng3(String eng3) {
            this.eng3 = eng3;
        }

    }

    public class VieSub {

        @SerializedName("vie_1")
        @Expose
        private String vie1;
        @SerializedName("vie_2")
        @Expose
        private String vie2;
        @SerializedName("vie_3")
        @Expose
        private String vie3;

        public String getVie1() {
            return vie1;
        }

        public void setVie1(String vie1) {
            this.vie1 = vie1;
        }

        public String getVie2() {
            return vie2;
        }

        public void setVie2(String vie2) {
            this.vie2 = vie2;
        }

        public String getVie3() {
            return vie3;
        }

        public void setVie3(String vie3) {
            this.vie3 = vie3;
        }

    }

}



