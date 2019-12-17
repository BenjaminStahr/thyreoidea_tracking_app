package com.example.hashimoto_app.backend;

import java.util.ArrayList;

public class ThyroidElement
{
    private String nameOfSubstance;
    private String unit;
    private ArrayList<ThyroidMeasurement> measurements;

    public ThyroidElement(String nameOfSubstance, String unit, ArrayList<ThyroidMeasurement> measurements)
    {
        this.nameOfSubstance = nameOfSubstance;
        this.unit = unit;
        this.measurements = measurements;
    }

    public String getNameOfSubstance()
    {
        return nameOfSubstance;
    }

    public String getUnit()
    {
        return unit;
    }

    public ArrayList<ThyroidMeasurement> getMeasurements()
    {
        return measurements;
    }
}
