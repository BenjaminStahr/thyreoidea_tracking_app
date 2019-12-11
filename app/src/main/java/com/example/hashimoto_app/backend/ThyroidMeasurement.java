package com.example.hashimoto_app.backend;


import java.time.LocalDate;
import java.util.Date;

public class ThyroidMeasurement
{
    Date date;
    float amount;
    float upperBound;
    float lowerBound;

    public ThyroidMeasurement(Date date, int amount, float lowerBound, float upperBound)
    {
        this.date = date;
        this.amount = amount;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }
}
