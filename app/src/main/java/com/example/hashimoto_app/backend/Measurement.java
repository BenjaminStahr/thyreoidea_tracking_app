package com.example.hashimoto_app.backend;

import java.util.Date;

/**
 * This class represents a measurement for a symptom or a supplement
 */
public class Measurement
{
    private Date date;
    private float amount;

    public Measurement(Date date, float amount)
    {
        this.date = date;
        this.amount = amount;
    }

    public Date getDate()
    {
        return date;
    }
    public float getAmount()
    {
        return amount;
    }
}
