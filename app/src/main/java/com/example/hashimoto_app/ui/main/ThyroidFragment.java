package com.example.hashimoto_app.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.hashimoto_app.R;

import java.util.ArrayList;

public class ThyroidFragment extends Fragment
{
    public ThyroidFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.thyroid_fragment,
                container, false);


        ListView thyroidListView = (ListView)container.findViewById(R.id.thyroidListView);
        ArrayList<String> values = new ArrayList<>();
        values.add("t4");
        values.add("t3");
        values.add("tpoak");
        ArrayAdapter adapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_1, values);
        thyroidListView.setAdapter(adapter);
        // Inflate the layout for this fragment
        return view;
    }

}

