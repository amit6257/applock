package com.example.amitagarwal.applock.views;

/**
 * Created by amitagarwal on 1/1/15.
 */
public class Figure {
    private int number;
    private String colour;

    public Figure(int number, String colour) {
        this.number = number;
        this.colour = colour;
    }

    public Figure(){

    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }
}
