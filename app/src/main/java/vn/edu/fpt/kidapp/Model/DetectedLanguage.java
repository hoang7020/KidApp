package vn.edu.fpt.kidapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetectedLanguage {

    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("score")
    @Expose
    private Double score;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

}
