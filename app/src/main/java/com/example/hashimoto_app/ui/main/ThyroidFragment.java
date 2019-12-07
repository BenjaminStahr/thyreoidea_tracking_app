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

import com.example.hashimoto_app.PlotAdapter;
import com.example.hashimoto_app.R;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class ThyroidFragment extends Fragment
{
    private final Context context;

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
        ListView thyroidListView = (ListView)getView().findViewById(R.id.thyroidListView);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        LineGraphSeries<DataPoint> series1 = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        ArrayList<LineGraphSeries> values = new ArrayList<>();
        values.add(series);
        values.add(series1);
        values.add(series2);
        PlotAdapter adapter = new PlotAdapter(context, values);
        if (thyroidListView != null)
        {
            thyroidListView.setAdapter(adapter);
        }
        /*ArrayList<String> values = new ArrayList<>();
        values.add("t4");
        values.add("t3");
        values.add("tpoak");
        ArrayAdapter adapter = new ArrayAdapter(context, R.layout.thyroid_item_plot, values);
        if (thyroidListView != null)
        {
            thyroidListView.setAdapter(adapter);
        }*/
    }



    /*@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //DBHelper = new DatabaseHelper(activity);
    }*/

}

