package com.example.hashimoto_app.backend;

import java.util.ArrayList;

public class IntakeElement
{
    String name;
    String unit;
    ArrayList<Measurement> measurements;

    public IntakeElement(String name, String unit, ArrayList<Measurement> measurements)
    {
        this.name = name;
        this.unit = unit;
        this.measurements = measurements;
    }
}
