package com.example.hashimoto_app.backend;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DataHolder
{
    private ArrayList<ThyroidElement> thyroidData;
    private ArrayList<SymptomElement> symptomData;
    private ArrayList<IntakeElement> intakeData;
    private final long USER_ID;

    public DataHolder(long USER_ID)
    {
        this.USER_ID = USER_ID;
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
    public ArrayList<String> getSupplements()
    {
        ArrayList<String> supplements = new ArrayList<>();
        for(int i = 0; i < intakeData.size(); i++)
        {
            supplements.add(intakeData.get(i).getNameOfSubstance());
        }
        return supplements;
    }
    public String getUnitOfSupplement(String substance)
    {
        for (int i = 0; i < intakeData.size(); i++)
        {
            if(intakeData.get(i).getNameOfSubstance().equals(substance))
            {
                return intakeData.get(i).getUnit();
            }
        }
        return "mg";
    }

    public String getSymptomNotUpdatedForOneDay()
    {
        ArrayList<String> possibleSymptoms = new ArrayList<>();
        for(int i = 0; i < symptomData.size(); i++)
        {
            if(symptomData.get(i).getMeasurements().size() != 0)
            {
                Date lastEntry = symptomData.get(i).getMeasurements().get(symptomData.get(i).getMeasurements().size()-1).getDate();
                long diff = new Date().getTime() - lastEntry.getTime();
                long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                if(days > 1)
                {
                    possibleSymptoms.add(symptomData.get(i).getSymptomName());
                }
            }
        }
        if (possibleSymptoms.size() != 0)
        {
            Random rand = new Random(System.currentTimeMillis());
            int returnIndex =rand.nextInt(possibleSymptoms.size());
            return possibleSymptoms.get(returnIndex);
        }
        else
        {
            return null;
        }
    }
    public void addSymptom(String symptomName)
    {
        SymptomElement element = new SymptomElement(symptomName, new ArrayList<Measurement>());
        symptomData.add(element);
    }
    public void addSupplement(String supplementName, String unit)
    {
        IntakeElement element = new IntakeElement(supplementName, unit, new ArrayList<Measurement>());
        intakeData.add(element);
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
    public long getUSER_ID(){return USER_ID;}
}
