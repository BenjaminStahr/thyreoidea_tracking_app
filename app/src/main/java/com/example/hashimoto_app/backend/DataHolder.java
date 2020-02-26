package com.example.hashimoto_app.backend;

import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
import java.util.Calendar;
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
    public Date getLastUpdateDateOfSymptom(String symptom)
    {
        // last date is initialized in the past so that a new entry can get made if there is none
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, 0, 0, 0, 0, 0);
        Date lastUpdate = calendar.getTime();
        for (int i = 0; i < symptomData.size(); i++)
        {
            if (symptomData.get(i).getSymptomName().equals(symptom))
            {
                lastUpdate = symptomData.get(i).getMeasurements().get(symptomData.get(i).getMeasurements().size()-1).getDate();
            }
        }
        return lastUpdate;
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
    public void deleteThyroidDataPoint(String substance, double moment)
    {
        for (int i = 0; i < thyroidData.size(); i++)
        {
            if(thyroidData.get(i).getNameOfSubstance().equals(substance))
            {
                for (int j = 0; j < thyroidData.get(i).getMeasurements().size(); j++)
                {
                    if(thyroidData.get(i).getMeasurements().get(j).getDate().getTime() == moment)
                    {
                        thyroidData.get(i).getMeasurements().remove(j);
                        break;
                    }
                }
            }
        }
    }

    public void deleteSymptomDataPoint(String symptom, double moment)
    {
        for (int i = 0; i < symptomData.size(); i++)
        {
            if(symptomData.get(i).getSymptomName().equals(symptom))
            {
                for (int j = 0; j < symptomData.get(i).getMeasurements().size(); j++)
                {
                    if(symptomData.get(i).getMeasurements().get(j).getDate().getTime() == moment)
                    {
                        symptomData.get(i).getMeasurements().remove(j);
                        break;
                    }
                }
            }
        }
    }
    public void deleteIntakeDataPoint(String substance, double moment)
    {
        for (int i = 0; i < intakeData.size(); i++)
        {
            if(intakeData.get(i).getNameOfSubstance().equals(substance))
            {
                for (int j = 0; j < intakeData.get(i).getMeasurements().size(); j++)
                {
                    if(intakeData.get(i).getMeasurements().get(j).getDate().getTime() == moment)
                    {
                        intakeData.get(i).getMeasurements().remove(j);
                        break;
                    }
                }
            }
        }
    }

    public DataPoint[] getThyroidDataPointsForSubstance(String substance)
    {
        DataPoint[] result;
        for (int i = 0; i < thyroidData.size(); i++)
        {
            if(thyroidData.get(i).getNameOfSubstance().equals(substance))
            {
                result = new DataPoint[thyroidData.get(i).getMeasurements().size()];
                ArrayList<ThyroidMeasurement> elements = thyroidData.get(i).getMeasurements();
                for(int j = 0; j < elements.size(); j++)
                {
                    double x = elements.get(j).getDate().getTime();
                    double y = elements.get(j).getAmount();
                    result[j] = new DataPoint(x, y);
                }
                return result;
            }
        }
        return null;
    }

    public DataPoint[] getDataPointsForSymptom(String symptom)
    {
        DataPoint[] result;
        for (int i = 0; i < symptomData.size(); i++)
        {
            if(symptomData.get(i).getSymptomName().equals(symptom))
            {
                result = new DataPoint[symptomData.get(i).getMeasurements().size()];
                ArrayList<Measurement> elements = symptomData.get(i).getMeasurements();
                for(int j = 0; j < elements.size(); j++)
                {
                    double x = elements.get(j).getDate().getTime();
                    double y = elements.get(j).getAmount();
                    result[j] = new DataPoint(x, y);
                }
                return result;
            }
        }
        return null;
    }
    public DataPoint[] getIntakeDataPointsForSubstance(String substance)
    {
        DataPoint[] result;
        for (int i = 0; i < intakeData.size(); i++)
        {
            if(intakeData.get(i).getNameOfSubstance().equals(substance))
            {
                result = new DataPoint[intakeData.get(i).getMeasurements().size()];
                ArrayList<Measurement> elements = intakeData.get(i).getMeasurements();
                for(int j = 0; j < elements.size(); j++)
                {
                    double x = elements.get(j).getDate().getTime();
                    double y = elements.get(j).getAmount();
                    result[j] = new DataPoint(x, y);
                }
                return result;
            }
        }
        return null;
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
