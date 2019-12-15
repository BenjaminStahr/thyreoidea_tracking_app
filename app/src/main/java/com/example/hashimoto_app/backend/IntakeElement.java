package com.example.hashimoto_app.backend;

import java.util.ArrayList;

public class IntakeElement
{
    private String nameOfSubstance;
    private String unit;
    private ArrayList<Measurement> measurements;

    public IntakeElement(String nameOfSubstance, String unit, ArrayList<Measurement> measurements)
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
    public ArrayList<Measurement> getMeasurements()
    {
        return measurements;
    }
}
