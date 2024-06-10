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

    public BinDetailHistory() {
    }

    public BinDetailHistory(String time, String name, String accuracy) {
        this.time = time;
        this.name = name;
        this.accuracy = accuracy;
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
