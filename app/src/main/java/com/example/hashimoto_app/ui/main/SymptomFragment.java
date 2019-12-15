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

public class SymptomFragment extends Fragment
{
    private final Context context;
    public SymptomFragment(Context context)
    {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.symptom_fragment,
                container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        ListView symptomListView = (ListView)getView().findViewById(R.id.symptomListView);
        /*ArrayList<String> values = new ArrayList<>();
        values.add("Schmerzen");
        values.add("Unruhe");
        values.add("Depression");
        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, values);
        if (symptomListView != null)
        {
            symptomListView.setAdapter(adapter);
        }*/
        ArrayList<LineGraphSeries> differentViews = new ArrayList<>();
        String[] units = new String[MainActivity.getDataHolder().getSymptomData().size()];
        // we do this for not changing the adapter
        for(int i = 0; i < units.length; i++)
        {
            units[i] = "Intensität";
        }

        String[] namesOfSubstances = new String[MainActivity.getDataHolder().getSymptomData().size()];

        for(int i = 0; i < MainActivity.getDataHolder().getSymptomData().size(); i++)
        {
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
            //units[i] = MainActivity.getDataHolder().getSymptomData().get(i).getUnit();
            namesOfSubstances[i] = MainActivity.getDataHolder().getSymptomData().get(i).getSymptomName();
            for(int j = 0; j < MainActivity.getDataHolder().getSymptomData().get(i).getMeasurements().size(); j++)
            {
                Date date = MainActivity.getDataHolder().getSymptomData().get(i).getMeasurements().get(j).getDate();
                float amount = MainActivity.getDataHolder().getSymptomData().get(i).getMeasurements().get(j).getAmount();
                DataPoint point = new DataPoint(date,(double) amount);
                series.appendData(point, false, MainActivity.getDataHolder().getSymptomData().get(i).getMeasurements().size(),
                        false);
            }
            differentViews.add(series);
        }

        PlotAdapter adapter = new PlotAdapter(context, differentViews, units, namesOfSubstances);
        if (symptomListView != null)
        {
            symptomListView.setAdapter(adapter);
        }
    }
}
