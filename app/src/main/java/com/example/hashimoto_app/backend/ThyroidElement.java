package com.example.hashimoto_app.backend;

import java.util.ArrayList;

public class ThyroidElement
{
    String name;
    String unit;
    ArrayList<ThyroidMeasurement> measurements;

    public ThyroidElement(String name, String unit, ArrayList<ThyroidMeasurement> measurements)
    {
        this.name = name;
        this.unit = unit;
        this.measurements = measurements;
    }
}
