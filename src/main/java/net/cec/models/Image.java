package net.cec.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {

@SerializedName("height")
@Expose
private int height;
@SerializedName("width")
@Expose
private int width;
@SerializedName("src")
@Expose
private String src;

public int getHeight() {
return height;
}

public void setHeight(int height) {
this.height = height;
}

public int getWidth() {
return width;
}

public void setWidth(int width) {
this.width = width;
}

public String getSrc() {
return src;
}

public void setSrc(String src) {
this.src = src;
}

}