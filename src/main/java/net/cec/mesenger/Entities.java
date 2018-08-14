
package net.cec.mesenger;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Entities {

    @SerializedName("location")
    @Expose
    private List<Location> location = null;
    @SerializedName("sentiment")
    @Expose
    private List<Sentiment> sentiment = null;

    public List<Location> getLocation() {
        return location;
    }

    public void setLocation(List<Location> location) {
        this.location = location;
    }

    public List<Sentiment> getSentiment() {
        return sentiment;
    }

    public void setSentiment(List<Sentiment> sentiment) {
        this.sentiment = sentiment;
    }

}
