package com.example.hashimoto_app.ui.main.symtoms;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hashimoto_app.MainActivity;
import com.example.hashimoto_app.PlotAdapter;
import com.example.hashimoto_app.R;
import com.example.hashimoto_app.ui.main.symtoms.DeleteSymptomDataPointDialog;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class SymptomFragment extends Fragment
{
    private final Context context;
    private String period;

    public SymptomFragment(Context context)
    {
        this.context = context;
        period = context.getString(R.string.period_week);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.symptom_fragment,
                container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        setAdapterData(period);
    }
    public void setAdapterData(String period)
    {
        this.period = period;
        if (getView() != null)
        {
            ListView symptomListView = (ListView) getView().findViewById(R.id.symptomListView);
            ArrayList<LineGraphSeries> differentViews = new ArrayList<>();
            String[] units = new String[MainActivity.getDataHolder().getSymptomsWithDataPointsSize()];
            for (int i = 0; i < units.length; i++)
            {
                units[i] = "Intensität";
            }
            final String[] namesOfSymptoms = new String[MainActivity.getDataHolder().getSymptomsWithDataPointsSize()];
            int symptomIndex = 0;
            for (int i = 0; i < MainActivity.getDataHolder().getSymptomData().size(); i++)
            {
                if (MainActivity.getDataHolder().getSymptomData().get(i).getMeasurements().size() != 0)
                {
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                    final int iForListener = symptomIndex;
                    series.setOnDataPointTapListener(new OnDataPointTapListener()
                    {
                        @Override
                        public void onTap(Series series, DataPointInterface dataPoint)
                        {
                            DeleteSymptomDataPointDialog deleteDatapointSymptomDialog = new DeleteSymptomDataPointDialog(
                                    namesOfSymptoms[iForListener], dataPoint.getX(), series);
                            deleteDatapointSymptomDialog.show(getActivity().getSupportFragmentManager(), "delete symptom datapoint dialog");
                        }
                    });
                    series.setDrawDataPoints(true);
                    namesOfSymptoms[symptomIndex] = MainActivity.getDataHolder().getSymptomData().get(i).getSymptomName();
                    if(MainActivity.getDataHolder().getSymptomData().get(i).getMeasurements() != null)
                    {
                        for (int j = 0; j < MainActivity.getDataHolder().getSymptomData().get(i).getMeasurements().size(); j++)
                        {
                            Date date = MainActivity.getDataHolder().getSymptomData().get(i).getMeasurements().get(j).getDate();
                            float amount = MainActivity.getDataHolder().getSymptomData().get(i).getMeasurements().get(j).getAmount();
                            DataPoint point = new DataPoint(date.getTime(), (double) amount);
                            //Date dateToday = new Date();
                            Calendar calendar = Calendar.getInstance();
                            //calendar.set(calendar.YEAR, calendar.MONTH, calendar.DATE, 0, 0, 0);
                            Date dateToday = calendar.getTime();
                            long diff = dateToday.getTime() - date.getTime();
                            long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                            if (period.equals(getString(R.string.period_week))) {
                                if (period.equals(getString(R.string.period_week)) && days <= 7) {
                                    series.appendData(point, true, MainActivity.getDataHolder().getSymptomData().get(i).getMeasurements().size(),
                                            false);
                                }
                            } else if (period.equals(getString(R.string.period_month)) && days <= 30) {
                                series.appendData(point, false, MainActivity.getDataHolder().getSymptomData().get(i).getMeasurements().size(),
                                        false);
                            } else {
                                if (period.equals(getString(R.string.period_year)) && days <= 365) {
                                    series.appendData(point, false, MainActivity.getDataHolder().getSymptomData().get(i).getMeasurements().size(),
                                            false);
                                }
                            }
                        }
                    }
                    differentViews.add(series);
                    symptomIndex++;
                }


            }
            PlotAdapter adapter = new PlotAdapter(context, differentViews, units, namesOfSymptoms, period, true);
            if (symptomListView != null)
            {
                symptomListView.setAdapter(adapter);
            }
        }
    }
}
