package com.example.hashimoto_app.backend;


import java.time.LocalDate;
import java.util.Date;

public class Measurement
{
    Date date;
    float amount;

    public Measurement(Date date, float amount)
    {
        this.date = date;
        this.amount = amount;
    }
}
