package com.example.amitagarwal.applock.utils;

/**
 * Created by amitagarwal on 1/17/15.
 */
public class EnterPasswordFigure {

    int number;
    String color;
    boolean isNumber,isColor;

    public EnterPasswordFigure() {
        this.isNumber = false;
        this.isColor = false;
    }

    public boolean isNumber() {
        return isNumber;
    }

    public boolean isColor() {
        return isColor;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
        isNumber = true;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        isColor = true;
    }
}
