
package net.cec.mesenger;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Nlp {

    @SerializedName("entities")
    @Expose
    private Entities entities;

    public Entities getEntities() {
        return entities;
    }

    public void setEntities(Entities entities) {
        this.entities = entities;
    }

}
