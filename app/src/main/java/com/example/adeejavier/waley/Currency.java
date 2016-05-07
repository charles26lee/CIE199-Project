package com.example.adeejavier.waley;

/**
 * Created by user on 5/7/2016.
 */
public class Currency {
    private String name = "";
    private double rate = 0;

    public Currency(String _name, double _rate) {
        name = _name;
        rate = _rate;
    }

    public String getName() {
        return name;
    }

    public double getRate() {
        return rate;
    }

    public String toString() {
        return name;
    }
}
