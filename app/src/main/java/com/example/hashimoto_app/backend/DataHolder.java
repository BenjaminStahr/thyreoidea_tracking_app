package com.example.hashimoto_app.backend;

import java.util.ArrayList;

public class DataHolder
{
    private ArrayList<ThyroidElement> thyroidData;
    private ArrayList<SymptomElement> symptomData;
    private ArrayList<IntakeElement> intakeData;

    public DataHolder()
    {
        thyroidData = new ArrayList<>();
        symptomData = new ArrayList<>();
        intakeData = new ArrayList<>();
    }
    public ArrayList<String> getSymptoms()
    {
        ArrayList<String> symptoms = new ArrayList<>();
        for(int i = 0; i < symptomData.size(); i++)
        {
            symptoms.add(symptomData.get(i).getSymptomName());
        }
        return symptoms;
    }
    public void addSymptom(String symptomName)
    {
        SymptomElement element = new SymptomElement(symptomName, new ArrayList<Measurement>());
        symptomData.add(element);
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
