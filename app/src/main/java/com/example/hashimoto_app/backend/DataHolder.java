package com.example.hashimoto_app.backend;

import java.util.ArrayList;

public class DataHolder
{
    private ArrayList<ThyroidElement> thyroidData;
    private ArrayList<SymptomElement> symptomData;
    private ArrayList<IntakeElement> intakeData;

    public DataHolder()
    {
        thyroidData = new ArrayList<ThyroidElement>();
        symptomData = new ArrayList<SymptomElement>();
        intakeData = new ArrayList<IntakeElement>();
    }

    public ArrayList<ThyroidElement> getThyroidData()
    {
        return thyroidData;
    }

    public ArrayList<SymptomElement> getSymptomData()
    {
        return symptomData;
    }

    public ArrayList<IntakeElement> getIntakeData()
    {
        return intakeData;
    }
}
