package com.example.hashimoto_app.ui.main;

import android.content.Context;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Date;

public class IntakeFragment extends Fragment
{
    Context context;

    public IntakeFragment(Context context)
    {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.intake_fragment,
                container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        ListView intakeListView = (ListView)getView().findViewById(R.id.intakeListView);
        ArrayList<LineGraphSeries> differentViews = new ArrayList<>();
        String[] units = new String[MainActivity.getDataHolder().getIntakeData().size()];
        String[] namesOfSubstances = new String[MainActivity.getDataHolder().getIntakeData().size()];

        for(int i = 0; i < MainActivity.getDataHolder().getIntakeData().size(); i++)
        {
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
            units[i] = MainActivity.getDataHolder().getIntakeData().get(i).getUnit();
            namesOfSubstances[i] = MainActivity.getDataHolder().getIntakeData().get(i).getNameOfSubstance();
            for(int j = 0; j < MainActivity.getDataHolder().getIntakeData().get(i).getMeasurements().size(); j++)
            {
                Date date = MainActivity.getDataHolder().getIntakeData().get(i).getMeasurements().get(j).getDate();
                float amount = MainActivity.getDataHolder().getIntakeData().get(i).getMeasurements().get(j).getAmount();
                DataPoint point = new DataPoint(date,(double) amount);
                series.appendData(point, false, MainActivity.getDataHolder().getIntakeData().get(i).getMeasurements().size(),
                        false);
            }
            differentViews.add(series);
        }
        PlotAdapter adapter = new PlotAdapter(context, differentViews, units, namesOfSubstances);
        if (intakeListView != null)
        {
            intakeListView.setAdapter(adapter);
        }

    }
}