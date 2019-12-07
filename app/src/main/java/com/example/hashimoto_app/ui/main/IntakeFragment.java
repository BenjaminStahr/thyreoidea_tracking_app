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
        ArrayList<String> values = new ArrayList<>();
        values.add("Selen");
        values.add("Eisen");
        values.add("Magnesium");
        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, values);
        if (intakeListView != null)
        {
             intakeListView.setAdapter(adapter);
        }
    }
}