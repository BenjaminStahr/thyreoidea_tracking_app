package com.example.hashimoto_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class PlotAdapter extends ArrayAdapter<String> {

    ArrayList<LineGraphSeries> data;
    Context context;

    public PlotAdapter(Context context, ArrayList<LineGraphSeries> data)
    {
        super(context, R.layout.thyroid_item_plot);
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount()
    {
        return data.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.thyroid_item_plot, parent, false);
            viewHolder.graphView = (GraphView) convertView.findViewById(R.id.graph);
            //viewHolder.mFlag = (ImageView) convertView.findViewById(R.id.imageView);
            //mViewHolder.mName = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.graphView.addSeries(data.get(position));
        //mViewHolder.mFlag.setImageResource(flags[position]);
        //mViewHolder.mName.setText(names[position]);

        return convertView;
    }

    static class ViewHolder
    {
        GraphView graphView;
    }
}