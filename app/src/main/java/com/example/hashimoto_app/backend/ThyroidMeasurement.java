package com.example.hashimoto_app.backend;


import java.time.LocalDate;
import java.util.Date;

public class ThyroidMeasurement
{
    private Date date;
    private float amount;
    private float upperBound;
    private float lowerBound;

    public ThyroidMeasurement(Date date, float amount, float lowerBound, float upperBound)
    {
        this.date = date;
        this.amount = amount;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public Date getDate()
    {
        return date;
    }

    public float getAmount()
    {
        return amount;
    }

    public float getUpperBound()
    {
        return upperBound;
    }

    public float getLowerBound()
    {
        return lowerBound;
    }
}
