package com.example.hashimoto_app.ui.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hashimoto_app.MainActivity;
import com.example.hashimoto_app.PlotAdapter;
import com.example.hashimoto_app.R;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ThyroidFragment extends Fragment
{
    private final Context context;
    //private PlotAdapter adapter;

    public ThyroidFragment(Context context)
    {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.thyroid_fragment,
                container, false);
        // Inflate the layout for this fragment
        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        /*ListView thyroidListView = (ListView)getView().findViewById(R.id.thyroidListView);
        ArrayList<LineGraphSeries> differentViews = new ArrayList<>();
        String[] units = new String[MainActivity.getDataHolder().getThyroidData().size()];
        String[] namesOfSubstances = new String[MainActivity.getDataHolder().getThyroidData().size()];

        for(int i = 0; i < MainActivity.getDataHolder().getThyroidData().size(); i++)
        {
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
            units[i] = MainActivity.getDataHolder().getThyroidData().get(i).getUnit();
            namesOfSubstances[i] = MainActivity.getDataHolder().getThyroidData().get(i).getNameOfSubstance();
            for(int j = 0; j < MainActivity.getDataHolder().getThyroidData().get(i).getMeasurements().size(); j++)
            {
                Date date = MainActivity.getDataHolder().getThyroidData().get(i).getMeasurements().get(j).getDate();
                float amount = MainActivity.getDataHolder().getThyroidData().get(i).getMeasurements().get(j).getAmount();
                DataPoint point = new DataPoint(date,(double) amount);
                series.appendData(point, false, MainActivity.getDataHolder().getThyroidData().get(i).getMeasurements().size(),
                        false);
            }
            differentViews.add(series);
        }
        adapter = new PlotAdapter(context, differentViews, units, namesOfSubstances);
        if (thyroidListView != null)
        {
            thyroidListView.setAdapter(adapter);
        }*/
        setAdapterData(getString(R.string.period_week));

    }

    public void setAdapterData(String period)
    {
        if (getView() != null)
        {
            ListView thyroidListView = (ListView) getView().findViewById(R.id.thyroidListView);
            ArrayList<LineGraphSeries> differentViews = new ArrayList<>();
            String[] units = new String[MainActivity.getDataHolder().getThyroidData().size()];
            String[] namesOfSubstances = new String[MainActivity.getDataHolder().getThyroidData().size()];

            for (int i = 0; i < MainActivity.getDataHolder().getThyroidData().size(); i++)
            {
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                series.setDrawDataPoints(true);
                units[i] = MainActivity.getDataHolder().getThyroidData().get(i).getUnit();
                namesOfSubstances[i] = MainActivity.getDataHolder().getThyroidData().get(i).getNameOfSubstance();
                for (int j = 0; j < MainActivity.getDataHolder().getThyroidData().get(i).getMeasurements().size(); j++)
                {
                    Date date = MainActivity.getDataHolder().getThyroidData().get(i).getMeasurements().get(j).getDate();
                    float amount = MainActivity.getDataHolder().getThyroidData().get(i).getMeasurements().get(j).getAmount();
                    DataPoint point = new DataPoint(date.getTime(), (double) amount);
                    //Date dateToday = new Date();
                    Calendar calendar = Calendar.getInstance();
                    //calendar.set(calendar.YEAR, calendar.MONTH, calendar.DATE, 0, 0, 0);
                    Date dateToday = calendar.getTime();
                    long diff = dateToday.getTime() - date.getTime();
                    long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                    if (period.equals(getString(R.string.period_week)))
                    {
                        if (period.equals(getString(R.string.period_week)) && days <= 7)
                        {
                            series.appendData(point, true, MainActivity.getDataHolder().getThyroidData().get(i).getMeasurements().size(),
                                    false);
                        }
                    }
                    else if (period.equals(getString(R.string.period_month)))
                    {
                        if (period.equals(getString(R.string.period_month)) && days <= 30)
                        {
                            series.appendData(point, false, MainActivity.getDataHolder().getThyroidData().get(i).getMeasurements().size(),
                                    false);
                        }
                    }
                    else
                    {
                        if (period.equals(getString(R.string.period_year)) && days <= 365)
                        {
                            series.appendData(point, false, MainActivity.getDataHolder().getThyroidData().get(i).getMeasurements().size(),
                                    false);
                        }
                    }
                }
                differentViews.add(series);
            }
            PlotAdapter adapter = new PlotAdapter(context, differentViews, units, namesOfSubstances, period);
            if (thyroidListView != null)
            {
                thyroidListView.setAdapter(adapter);
            }
        }
    }
}

