package com.example.hashimoto_app;

import android.os.Bundle;

import com.example.hashimoto_app.backend.DataHolder;
import com.example.hashimoto_app.backend.FileManager;
import com.example.hashimoto_app.backend.IntakeElement;
import com.example.hashimoto_app.backend.Measurement;
import com.example.hashimoto_app.backend.SymptomElement;
import com.example.hashimoto_app.backend.ThyroidElement;
import com.example.hashimoto_app.backend.ThyroidMeasurement;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.hashimoto_app.ui.main.SectionsPagerAdapter;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity
{
    private static DataHolder dataHolder;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DataHolder holder = new DataHolder();


        // add some sample data to the holder
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, 11, 5);
        Date date = calendar.getTime();
        calendar.set(2019, 11, 12);
        Date date2 = calendar.getTime();
        calendar.set(2019, 11, 7);
        Date date3 = calendar.getTime();
        ThyroidMeasurement thyroidMeasurement1 = new ThyroidMeasurement(date, 2, 1 ,3);
        ThyroidMeasurement thyroidMeasurement5 = new ThyroidMeasurement(date3, 1, 1 ,3);
        ThyroidMeasurement thyroidMeasurement2 = new ThyroidMeasurement(date2, 5, 1 ,3);
        ArrayList<ThyroidMeasurement> thyroidMeasurements1 = new ArrayList<>();
        thyroidMeasurements1.add(thyroidMeasurement1);
        thyroidMeasurements1.add(thyroidMeasurement5);
        thyroidMeasurements1.add(thyroidMeasurement2);
        ArrayList<ThyroidMeasurement> thyroidMeasurements2 = new ArrayList<>();
        ThyroidMeasurement thyroidMeasurement3 = new ThyroidMeasurement(date, 8, 1 ,3);
        ThyroidMeasurement thyroidMeasurement4 = new ThyroidMeasurement(date2, 5, 1 ,3);
        thyroidMeasurements2.add(thyroidMeasurement3);
        thyroidMeasurements2.add(thyroidMeasurement4);
        holder.getThyroidData().add(new ThyroidElement("TSH", "g/mol", thyroidMeasurements1));
        holder.getThyroidData().add(new ThyroidElement("T3", "g/ml", thyroidMeasurements2));
        holder.getThyroidData().add(new ThyroidElement("T4", "g/ml", thyroidMeasurements1));

        Measurement symptomMeasurement1 = new Measurement(date, 2);
        Measurement symptomMeasurement2 = new Measurement(date2, 3);
        ArrayList<Measurement> symptomMeasurements1 = new ArrayList<>();
        symptomMeasurements1.add(symptomMeasurement1);
        symptomMeasurements1.add(symptomMeasurement2);
        ArrayList<Measurement> symptomMeasurements2 = new ArrayList<>();
        symptomMeasurements2.add(symptomMeasurement1);
        symptomMeasurements2.add(symptomMeasurement2);
        holder.getSymptomData().add(new SymptomElement("Unruhe", symptomMeasurements1));
        holder.getSymptomData().add(new SymptomElement("Zittern", symptomMeasurements2));

        Measurement intakeMeasurement1 = new Measurement(date, 6);
        Measurement intakeMeasurement2 = new Measurement(date2, 3);
        ArrayList<Measurement> intakeMeasurements1 = new ArrayList<>();
        intakeMeasurements1.add(intakeMeasurement1);
        intakeMeasurements1.add(intakeMeasurement2);
        ArrayList<Measurement> intakeMeasurements2 = new ArrayList<>();
        intakeMeasurements2.add(intakeMeasurement1);
        intakeMeasurements2.add(intakeMeasurement2);
        holder.getIntakeData().add(new IntakeElement("Magnesium", "g", intakeMeasurements1));
        holder.getIntakeData().add(new IntakeElement("Selen", "mg", intakeMeasurements2));

        String json = new Gson().toJson(holder);
        FileManager.saveFile("test", json, getApplicationContext());
        String testString = FileManager.getFileAsString("test", getApplicationContext());

        dataHolder = new Gson().fromJson(testString, DataHolder.class);
    }

    public static DataHolder getDataHolder()
    {
        return dataHolder;
    }
}