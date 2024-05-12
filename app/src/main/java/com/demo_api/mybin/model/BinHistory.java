package com.demo_api.mybin.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class BinHistory implements Serializable {
    private LocalDate date;

    public BinHistory(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDayMonthYear(){
        return date.getDayOfMonth() + "/" + date.getMonthValue() +  "/" + date.getYear();
    }
    public String getMonthYear(){
        return date.getMonthValue() +  "/" + date.getYear();
    }
    public String getYear(){
        return date.getYear() + "";
    }
}
