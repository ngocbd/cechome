
package net.cec.mesenger;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sentiment {

    @SerializedName("confidence")
    @Expose
    private Double confidence;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("_entity")
    @Expose
    private String entity;

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

}
