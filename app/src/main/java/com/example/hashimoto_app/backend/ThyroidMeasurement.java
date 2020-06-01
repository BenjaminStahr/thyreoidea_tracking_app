package com.example.hashimoto_app.backend;
import java.util.Date;

/**
 * This class represents a measurement for a thyroid value
 */
public class ThyroidMeasurement
{
    private Date date;
    private float amount;
    // upperBound and lowerBound never get used, because the application cant record relative thyroid values, what would have been nice
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
