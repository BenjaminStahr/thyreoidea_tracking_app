package com.example.hashimoto_app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import com.example.hashimoto_app.backend.DataHolder;
import com.example.hashimoto_app.backend.FileManager;
import com.example.hashimoto_app.backend.IntakeElement;
import com.example.hashimoto_app.backend.Measurement;
import com.example.hashimoto_app.backend.SymptomElement;
import com.example.hashimoto_app.backend.ThyroidElement;
import com.example.hashimoto_app.backend.ThyroidMeasurement;
import com.example.hashimoto_app.ui.main.AddSupplementDialog;
import com.example.hashimoto_app.ui.main.AddSymptomDialog;
import com.example.hashimoto_app.ui.main.IntakeDialog;
import com.example.hashimoto_app.ui.main.SymptomDialog;
import com.example.hashimoto_app.ui.main.ThyroidDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import com.example.hashimoto_app.ui.main.SectionsPagerAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements ThyroidDialog.ThyroidDialogListener,
        SymptomDialog.SymptomDialogListener, AddSymptomDialog.AddSymptomDialogListener, IntakeDialog.IntakeDialogListener,
        AddSupplementDialog.AddSupplementDialogListener
{
    // central data management of the applications data
    private static DataHolder dataHolder;
    // holds the different fragments
    SectionsPagerAdapter sectionsPagerAdapter;
    // lets user change the shown time period
    private Spinner periodSpinner;
    // dialogs of which the main activity needs the reference, because they can init some action
    SymptomDialog symptomDialog;
    IntakeDialog intakeDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        final ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        try
        {
            dataHolder = new Gson().fromJson(FileManager.getFileAsString("userData", getApplicationContext()), DataHolder.class);
        }
        catch (Exception ex)
        {
            Random rand = new Random(System.currentTimeMillis());
            int id = rand.nextInt();
            if(id < 0)
            {
                id *= -1;
            }
            dataHolder = new DataHolder(id);
            // register user in the server
            sendFirstTimeDataToServer(getUserDataAsJson());
            // add some sample data to the holder
            Calendar calendar = Calendar.getInstance();
            calendar.set(2020, 0, 9, 0, 0, 0);
            Date date = calendar.getTime();
            calendar.set(2020, 0, 13, 0, 0, 0);
            Date date2 = calendar.getTime();
            calendar.set(2020, 0, 14, 6, 0, 0);
            Date date3 = calendar.getTime();
            // sample data for thyroid measurements
            ThyroidMeasurement thyroidMeasurement1 = new ThyroidMeasurement(date, 3, 1, 3);
            ThyroidMeasurement thyroidMeasurement2 = new ThyroidMeasurement(date2, 4, 1, 3);
            ThyroidMeasurement thyroidMeasurement5 = new ThyroidMeasurement(date3, 2, 1, 3);
            ArrayList<ThyroidMeasurement> thyroidMeasurements1 = new ArrayList<>();
            thyroidMeasurements1.add(thyroidMeasurement1);
            thyroidMeasurements1.add(thyroidMeasurement2);
            thyroidMeasurements1.add(thyroidMeasurement5);
            ArrayList<ThyroidMeasurement> thyroidMeasurements2 = new ArrayList<>();
            ThyroidMeasurement thyroidMeasurement3 = new ThyroidMeasurement(date, 1.6f, 1, 3);
            ThyroidMeasurement thyroidMeasurement4 = new ThyroidMeasurement(date2, 1.4f, 1, 3);
            thyroidMeasurements2.add(thyroidMeasurement3);
            thyroidMeasurements2.add(thyroidMeasurement4);
            ArrayList<ThyroidMeasurement> thyroidMeasurements3 = new ArrayList<>();
            thyroidMeasurements3.add(thyroidMeasurement1);
            thyroidMeasurements3.add(thyroidMeasurement2);
            thyroidMeasurements3.add(thyroidMeasurement5);
            dataHolder.getThyroidData().add(new ThyroidElement("TSH", "µU/ml", thyroidMeasurements1));
            dataHolder.getThyroidData().add(new ThyroidElement("fT3", "pg/ml", thyroidMeasurements2));
            dataHolder.getThyroidData().add(new ThyroidElement("fT4", "ng/dl", thyroidMeasurements3));

            // sample data for symptoms
            Measurement symptomMeasurement1 = new Measurement(date, 2);
            Measurement symptomMeasurement2 = new Measurement(date2, 3);
            Measurement symptomMeasurement3 = new Measurement(date3, 3);
            Measurement symptomMeasurement4 = new Measurement(date, 5);
            Measurement symptomMeasurement5 = new Measurement(date2, 7);
            Measurement symptomMeasurement6 = new Measurement(date3, 1);
            ArrayList<Measurement> symptomMeasurements1 = new ArrayList<>();
            symptomMeasurements1.add(symptomMeasurement1);
            symptomMeasurements1.add(symptomMeasurement2);
            symptomMeasurements1.add(symptomMeasurement3);
            ArrayList<Measurement> symptomMeasurements2 = new ArrayList<>();
            symptomMeasurements2.add(symptomMeasurement4);
            symptomMeasurements2.add(symptomMeasurement5);
            symptomMeasurements2.add(symptomMeasurement6);
            ArrayList<Measurement> symptomMeasurements3 = new ArrayList<>();
            symptomMeasurements3.add(symptomMeasurement4);
            symptomMeasurements3.add(symptomMeasurement5);
            symptomMeasurements3.add(symptomMeasurement6);
            ArrayList<Measurement> symptomMeasurements4 = new ArrayList<>();
            symptomMeasurements4.add(symptomMeasurement1);
            symptomMeasurements4.add(symptomMeasurement2);
            symptomMeasurements4.add(symptomMeasurement3);

            dataHolder.getSymptomData().add(new SymptomElement("Unruhe", symptomMeasurements1));
            dataHolder.getSymptomData().add(new SymptomElement("Zittern", symptomMeasurements2));
            dataHolder.getSymptomData().add(new SymptomElement("erhöhter Herzschlag", symptomMeasurements3));
            dataHolder.getSymptomData().add(new SymptomElement("Unwohlsein", symptomMeasurements4));

            Measurement intakeMeasurement1 = new Measurement(date, 6);
            Measurement intakeMeasurement2 = new Measurement(date2, 3);
            Measurement intakeMeasurement3 = new Measurement(date3, 7);
            Measurement intakeMeasurement4 = new Measurement(date, 1);
            Measurement intakeMeasurement5 = new Measurement(date2, 2);
            Measurement intakeMeasurement6 = new Measurement(date3, 10);

            ArrayList<Measurement> intakeMeasurements1 = new ArrayList<>();
            intakeMeasurements1.add(intakeMeasurement1);
            intakeMeasurements1.add(intakeMeasurement2);
            intakeMeasurements1.add(intakeMeasurement3);
            ArrayList<Measurement> intakeMeasurements2 = new ArrayList<>();
            intakeMeasurements2.add(intakeMeasurement4);
            intakeMeasurements2.add(intakeMeasurement5);
            intakeMeasurements2.add(intakeMeasurement6);
            ArrayList<Measurement> intakeMeasurements3 = new ArrayList<>();
            intakeMeasurements3.add(intakeMeasurement1);
            intakeMeasurements3.add(intakeMeasurement2);
            intakeMeasurements3.add(intakeMeasurement3);
            dataHolder.getIntakeData().add(new IntakeElement("Magnesium", "g", intakeMeasurements2));
            dataHolder.getIntakeData().add(new IntakeElement("Selen", "mg", intakeMeasurements1));
            dataHolder.getIntakeData().add(new IntakeElement("Vitamin D", "mg", intakeMeasurements3));
            FileManager.saveFile("userData", new Gson().toJson(dataHolder), getApplicationContext());
        }
        //dataHolder = new Gson().fromJson(FileManager.getFileAsString("userData", getApplicationContext()), DataHolder.class);
        periodSpinner = findViewById(R.id.period_spinner);
        periodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                updateDataAccordingToSelectedTimePeriod();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //sendOnChannel(view);
                if(viewPager.getCurrentItem() == 0)
                {
                    openThyroidDialog();
                }
                else if(viewPager.getCurrentItem() == 1)
                {
                    openSymptomDialog();
                }
                else
                {
                    openIntakeDialog();
                }
            }
        });
        //scheduleNotifications();
        PeriodicWorkRequest notificationRequest =
                new PeriodicWorkRequest.Builder(NotificationWorker.class, 15, TimeUnit.MINUTES)
                        .build();
        WorkManager.getInstance(getApplicationContext())
                .enqueue(notificationRequest);

        PeriodicWorkRequest networkRequest =
                new PeriodicWorkRequest.Builder(NetworkWorker.class, 15, TimeUnit.MINUTES)
                        .build();
        WorkManager.getInstance(getApplicationContext())
                .enqueue(networkRequest);
        //sendSymptomDataToServer(getUserDataAsJson());
    }
    /*public static void sendSymptomDataToServer(final String symptomData)
    {

        new AsyncTask<Void, Void, String>()
        {
            @Override
            protected String doInBackground(Void... voids)
            {
                return updateServerData(symptomData);
            }
        }.execute();
    }*/
    public static void sendFirstTimeDataToServer(final String symptomData)
    {
        new AsyncTask<Void, Void, String>()
        {
            @Override
            protected String doInBackground(Void... voids)
            {
                return initServerData(symptomData);
            }
        }.execute();
    }
    public static String initServerData(String symptomData)
    {
        String query_url = "http://192.168.178.20:3000/data";
        try {
            URL url = new URL(query_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            OutputStream os = conn.getOutputStream();
            //String s = getUserDataAsJson();
            os.write(symptomData.getBytes());
            os.close();
            InputStream in = new BufferedInputStream(conn.getInputStream());
            String result = in.toString();
            //JSONObject myResponse = new JSONObject(result);
            in.close();
            conn.disconnect();

            return result;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "unsuccessful initialization of connection to server";
        }
    }


    public static String getUserDataAsJson()
    {
        final JsonObject sendData = new JsonObject();
        String idJson = new Gson().toJson(dataHolder.getUSER_ID());
        String symptomJson = new Gson().toJson(dataHolder.getSymptomData());
        sendData.add("id", new Gson().fromJson(idJson, JsonPrimitive.class));
        sendData.add("symptomData", new Gson().fromJson(symptomJson, JsonArray.class));
        return sendData.toString();
    }
    public void openThyroidDialog()
    {
        ThyroidDialog thyroidDialog = new ThyroidDialog();
        thyroidDialog.show(getSupportFragmentManager(), "thyroid dialog");
    }

    public void openSymptomDialog()
    {
        symptomDialog = new SymptomDialog(this);
        symptomDialog.show(getSupportFragmentManager(), "symptom dialog");
    }

    public void openIntakeDialog()
    {
        intakeDialog = new IntakeDialog(this);
        intakeDialog.show(getSupportFragmentManager(), "intake dialog");
    }
    public void openAddSymptomDialog()
    {
        AddSymptomDialog addSymptomDialog = new AddSymptomDialog();
        addSymptomDialog.show(getSupportFragmentManager(), "add symptom dialog");
    }
    public void openAddSupplementDialog()
    {
        AddSupplementDialog addSupplementDialog = new AddSupplementDialog();
        addSupplementDialog.show(getSupportFragmentManager(), "add supplement dialog");
    }

    public void updateDataAccordingToSelectedTimePeriod()
    {
        if(periodSpinner.getSelectedItemPosition() == 0)
        {
            sectionsPagerAdapter.adjustDataToTimePeriod(getString(R.string.period_week));
        }
        else if (periodSpinner.getSelectedItemPosition() == 1)
        {
            sectionsPagerAdapter.adjustDataToTimePeriod(getString(R.string.period_month));
        }
        else
        {
            sectionsPagerAdapter.adjustDataToTimePeriod(getString(R.string.period_year));
        }
    }
    /*public void scheduleNotifications()
    {
        /*Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, NotificationReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long firstMillis = System.currentTimeMillis();
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                30*1000, pIntent);
        Intent intent = new Intent(getApplicationContext(), NotificationService.class);
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 1, intent, 0);
        long firstMillis = System.currentTimeMillis();
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null)
        {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,firstMillis,  AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
        }
        //alarmManager.setExact(AlarmManager.RTC_WAKEUP,firstMillis + 10000, pendingIntent);
    }*/

    @Override
    public void applyThyroidTexts(String registeredValue, String substance, String unit, Date selectedDate)
    {
        boolean alreadyRegistered = false;
        boolean registeredInserted = false;
        ThyroidMeasurement thyroidMeasurement = new ThyroidMeasurement(selectedDate, Float.valueOf(registeredValue), 0 ,0);
        for(int i = 0; i < dataHolder.getThyroidData().size(); i++)
        {
            // right substance
            if(dataHolder.getThyroidData().get(i).getNameOfSubstance().equals(substance))
            {
                alreadyRegistered = true;
                for(int j = 0; j < dataHolder.getThyroidData().get(i).getMeasurements().size(); j++)
                {
                    if(dataHolder.getThyroidData().get(i).getMeasurements().get(j).getDate().getTime() > selectedDate.getTime())
                    {
                        if(j != 0)
                        {
                            // the case that its newer than the oldest entry but older than the newest entry
                            dataHolder.getThyroidData().get(i).getMeasurements().add(j, thyroidMeasurement);
                            registeredInserted = true;
                            break;
                        }
                        else
                        {
                            // the case when its older than the oldest entry
                            dataHolder.getThyroidData().get(i).getMeasurements().add(0, thyroidMeasurement);
                            registeredInserted = true;
                            break;
                        }
                    }
                }
                // the case when its newer than the newest entry
                if(!registeredInserted)
                {
                    dataHolder.getThyroidData().get(i).getMeasurements().add(thyroidMeasurement);
                }

            }
        }
        if(!alreadyRegistered)
        {
            ArrayList<ThyroidMeasurement> thyroidMeasurements = new ArrayList<>();
            thyroidMeasurements.add(thyroidMeasurement);
            ThyroidElement thyroidElement = new ThyroidElement(substance, unit, thyroidMeasurements);
            dataHolder.getThyroidData().add(thyroidElement);
        }
        updateDataAccordingToSelectedTimePeriod();
        FileManager.saveFile("userData", new Gson().toJson(dataHolder), getApplicationContext());
    }
    @Override
    public void applySymptomTexts(int registeredValue, String symptom)
    {
        Measurement measurement = new Measurement(new Date(), registeredValue);
        for(int i = 0; i < dataHolder.getSymptomData().size(); i++)
        {
            if(dataHolder.getSymptomData().get(i).getSymptomName().equals(symptom))
            {
                dataHolder.getSymptomData().get(i).getMeasurements().add(measurement);
            }
        }
        updateDataAccordingToSelectedTimePeriod();
        FileManager.saveFile("userData", new Gson().toJson(dataHolder), getApplicationContext());
    }
    @Override
    public void refreshSymptomList()
    {
        symptomDialog.setSpinnerItems();
        updateDataAccordingToSelectedTimePeriod();
        FileManager.saveFile("userData", new Gson().toJson(dataHolder), getApplicationContext());
    }
    @Override
    public void refreshSupplementList()
    {
        intakeDialog.setSpinnerItems();
        updateDataAccordingToSelectedTimePeriod();
        FileManager.saveFile("userData", new Gson().toJson(dataHolder), getApplicationContext());
    }
    @Override
    public void applyRegisteredIntake(String registeredValue, String substance)
    {
        Measurement measurement = new Measurement(new Date(), Float.valueOf(registeredValue));
        for(int i = 0; i < dataHolder.getIntakeData().size(); i++)
        {
            if(dataHolder.getIntakeData().get(i).getNameOfSubstance().equals(substance))
            {
                dataHolder.getIntakeData().get(i).getMeasurements().add(measurement);
            }
        }
        updateDataAccordingToSelectedTimePeriod();
        FileManager.saveFile("userData", new Gson().toJson(dataHolder), getApplicationContext());
    }
    public static DataHolder getDataHolder()
    {
        return dataHolder;
    }
}