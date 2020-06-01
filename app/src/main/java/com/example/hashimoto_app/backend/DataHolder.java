package com.example.hashimoto_app.backend;

import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * This class holds the backend of the application. It offers the used data structure and all methods
 * to manipulate this data structure
 */
public class DataHolder
{
    private ArrayList<ThyroidElement> thyroidData;
    private ArrayList<SymptomElement> symptomData;
    private ArrayList<IntakeElement> intakeData;
    private final long USER_ID;

    /**
     * the user id is a identifer for every user of the app
     * @param USER_ID
     *
     */
    public DataHolder(long USER_ID)
    {
        this.USER_ID = USER_ID;
        thyroidData = new ArrayList<>();
        symptomData = new ArrayList<>();
        intakeData = new ArrayList<>();
    }

    /**
     * @return returns a list with all saved symptoms of the Hashimoto-Thyreoiditis
     */
    public ArrayList<String> getSymptoms()
    {
        ArrayList<String> symptoms = new ArrayList<>();
        for(int i = 0; i < symptomData.size(); i++)
        {
            symptoms.add(symptomData.get(i).getSymptomName());
        }
        return symptoms;
    }

    /**
     *
     * @return returns a list with all saved supplements
     */
    public ArrayList<String> getSupplements()
    {
        ArrayList<String> supplements = new ArrayList<>();
        for(int i = 0; i < intakeData.size(); i++)
        {
            supplements.add(intakeData.get(i).getNameOfSubstance());
        }
        return supplements;
    }

    /**
     *
     * @param substance
     * @return returns the unit of the specified supplement
     */
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

    /**
     *
     * @return returns a random symptom, which was not updated for one day
     */
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

    /**
     *
     * @return returns the number of all symptoms, which have at least one entry
     */
    public int getSymptomsWithDataPointsSize()
    {
        int counter = 0;
        for(int i = 0; i < symptomData.size(); i++)
        {
            if (symptomData.get(i).getMeasurements().size() != 0)
            {
                counter++;
            }
        }
        return counter;
    }

    /**
     *
     * @return returns the number of thyroid values, which have at least one data point
     */
    public int getThyroidWithDataPointsSize()
    {
        int counter = 0;
        for(int i = 0; i < thyroidData.size(); i++)
        {
            if (thyroidData.get(i).getMeasurements().size() != 0)
            {
                counter++;
            }
        }
        return counter;
    }

    /**
     *
     * @return returns the number of supplements, which have at least one recorded data point
     */
    public int getIntakeWithDataPointsSize()
    {
        int counter = 0;
        for(int i = 0; i < intakeData.size(); i++)
        {
            if (intakeData.get(i).getMeasurements().size() != 0)
            {
                counter++;
            }
        }
        return counter;
    }

    /**
     *
     * @param symptom
     * @return returns the date where a specified symptom got updated the last time
     */
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
                if (symptomData.get(i).getMeasurements().size() != 0)
                {
                    lastUpdate = symptomData.get(i).getMeasurements().get(symptomData.get(i).getMeasurements().size() - 1).getDate();
                }
            }
        }
        return lastUpdate;
    }

    /**
     * This method adds a symptom to the internal list of symptoms
     * @param symptomName
     */
    public void addSymptom(String symptomName)
    {
        SymptomElement element = new SymptomElement(symptomName, new ArrayList<Measurement>());
        symptomData.add(element);
    }

    /**
     * This method adds a symptom to the internal list of supplements
     * @param supplementName
     * @param unit
     */
    public void addSupplement(String supplementName, String unit)
    {
        IntakeElement element = new IntakeElement(supplementName, unit, new ArrayList<Measurement>());
        intakeData.add(element);
    }

    /**
     * This method deletes a measurement for a specified thyroid value
     * @param substance
     * @param moment
     */
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

    /**
     * This method deletes a data point of a specified symptom
     * @param symptom
     * @param moment
     */
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

    /**
     * This method deletes a data point for a specified supplement
     * @param substance
     * @param moment
     */
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

    /**
     * @param substance
     * @return returns all measurements for a specified thyfoid value
     */
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

    /**
     *
     * @param symptom
     * @return returns all data points for a specified symptom
     */
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

    /**
     *
     * @param substance
     * @return returns all data points for a specified supplement
     */
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
