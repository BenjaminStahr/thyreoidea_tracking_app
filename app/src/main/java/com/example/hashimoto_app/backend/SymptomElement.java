package com.example.hashimoto_app.backend;

import java.util.ArrayList;

/**
 * This class represents a symptom
 */
public class SymptomElement
{
    private String symptomName;
    private ArrayList<Measurement> measurements;

    public SymptomElement(String symptomName, ArrayList<Measurement> measurements)
    {
        this.symptomName = symptomName;
        this.measurements = measurements;
    }

    public String getSymptomName()
    {
        return symptomName;
    }
    public ArrayList<Measurement> getMeasurements()
    {
        return measurements;
    }
    public void setMeasurements(ArrayList<Measurement> list){this.measurements = list;}
}
