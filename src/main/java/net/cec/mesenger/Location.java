
package net.cec.mesenger;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName("suggested")
    @Expose
    private Boolean suggested;
    @SerializedName("confidence")
    @Expose
    private Double confidence;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("_entity")
    @Expose
    private String entity;
    @SerializedName("_body")
    @Expose
    private String body;
    @SerializedName("_start")
    @Expose
    private Integer start;
    @SerializedName("_end")
    @Expose
    private Integer end;

    public Boolean getSuggested() {
        return suggested;
    }

    public void setSuggested(Boolean suggested) {
        this.suggested = suggested;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

}
