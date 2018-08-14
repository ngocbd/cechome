
package net.cec.mesenger;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("mid")
    @Expose
    private String mid;
    @SerializedName("seq")
    @Expose
    private Integer seq;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("nlp")
    @Expose
    private Nlp nlp;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Nlp getNlp() {
        return nlp;
    }

    public void setNlp(Nlp nlp) {
        this.nlp = nlp;
    }

}
