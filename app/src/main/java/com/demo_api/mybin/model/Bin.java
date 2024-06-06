package com.demo_api.mybin.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Bin implements Serializable {
    @SerializedName("numtime")
    private int numtime;
    @SerializedName("metal")
    private int metal;

    @SerializedName("plastic")
    private int plastic;

    @SerializedName("paper")
    private int paper;

    @SerializedName("other")
    private int other;

    public int getNumtime() {
        return numtime;
    }

    public void setNumtime(int numtime) {
        this.numtime = numtime;
    }

    public Bin(int metal, int plastic, int paper, int other) {
        this.metal = metal;
        this.plastic = plastic;
        this.paper = paper;
        this.other = other;
    }

    public int getMetal() {
        return metal;
    }

    public void setMetal(int metal) {
        this.metal = metal;
    }

    public int getPlastic() {
        return plastic;
    }

    public void setPlastic(int plastic) {
        this.plastic = plastic;
    }

    public int getPaper() {
        return paper;
    }

    public void setPaper(int paper) {
        this.paper = paper;
    }

    public int getOther() {
        return other;
    }

    public void setOther(int other) {
        this.other = other;
    }
}
