package com.example.hashimoto_app.backend;

import java.util.ArrayList;

public class SymptomElement
{
    String name;
    ArrayList<Measurement> measurements;

    public SymptomElement(String name, ArrayList<Measurement> measurements)
    {
        this.name = name;
        this.measurements = measurements;
    }
}
