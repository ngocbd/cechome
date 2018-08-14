
package net.cec.mesenger;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hook {

    @SerializedName("object")
    @Expose
    private String object;
    @SerializedName("entry")
    @Expose
    private List<Entry> entry = null;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public List<Entry> getEntry() {
        return entry;
    }

    public void setEntry(List<Entry> entry) {
        this.entry = entry;
    }

}
