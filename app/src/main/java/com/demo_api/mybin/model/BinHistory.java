package com.demo_api.mybin.model;

import java.time.LocalDate;
import java.util.Date;

public class BinHistory {
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
        return date.getYear() + "/" + date.getMonth() +  "/" + date.getYear();
    }
    public String getMonthYear(){
        return date.getMonth() +  "/" + date.getYear();
    }
    public String getYear(){
        return date.getYear() + "";
    }
}
