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

import com.example.hashimoto_app.R;

import java.util.ArrayList;

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
        ArrayList<String> values = new ArrayList<>();
        values.add("Schmerzen");
        values.add("Unruhe");
        values.add("Depression");
        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, values);
        if (symptomListView != null)
        {
            symptomListView.setAdapter(adapter);
        }
    }
}
