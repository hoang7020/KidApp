package vn.edu.fpt.kidapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TranslateResult {
    @SerializedName("detectedLanguage")
    @Expose
    private DetectedLanguage detectedLanguage;
    @SerializedName("translations")
    @Expose
    private List<Translation> translations = null;

    public DetectedLanguage getDetectedLanguage() {
        return detectedLanguage;
    }

    public void setDetectedLanguage(DetectedLanguage detectedLanguage) {
        this.detectedLanguage = detectedLanguage;
    }

    public List<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<Translation> translations) {
        this.translations = translations;
    }
}
