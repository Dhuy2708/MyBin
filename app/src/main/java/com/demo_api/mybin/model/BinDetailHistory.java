package com.demo_api.mybin.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class BinDetailHistory implements Serializable {
    @SerializedName("date_fill")
    private String time;
    @SerializedName("prediction")
    private String name;
    @SerializedName("accuracy")
    private String accuracy;
    @SerializedName("img")
    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }
}
