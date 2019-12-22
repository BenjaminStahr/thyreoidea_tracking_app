package com.example.hashimoto_app;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/*
This is a Customadapter for showing one Graph in the thyroid list view
 */
public class PlotAdapter extends ArrayAdapter<String>
{
    private ArrayList<LineGraphSeries> data;

    private String[] unit;
    private String[] nameOfSubstance;
    private Context context;
    private String period;

    public PlotAdapter(Context context, ArrayList<LineGraphSeries> data, String[] unit, String[] nameOfSubstance, String period)
    {
        super(context, R.layout.graph_item_plot);
        this.data = data;
        this.context = context;
        this.unit = unit;
        this.nameOfSubstance = nameOfSubstance;
        this.period = period;
    }

    @Override
    public int getCount()
    {
        return data.size();
    }

    @NonNull
    @Override
    // Here we use the view holder pattern
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.graph_item_plot, parent, false);
            viewHolder.graphView = (GraphView) convertView.findViewById(R.id.graph);
            viewHolder.nameOfSubstanceView = (TextView) convertView.findViewById(R.id.substanceLabel);
            viewHolder.unitView = (TextView) convertView.findViewById(R.id.unitLabel);
            viewHolder.graphView.getGridLabelRenderer().setLabelFormatter((new DefaultLabelFormatter()
            {
                @Override
                public String formatLabel(double value, boolean isValueX)
                {
                    if(isValueX)
                    {
                        if(period.equals(context.getString(R.string.period_week)))
                        {
                            SimpleDateFormat sdf = new SimpleDateFormat("EEE");
                            return sdf.format(new Date((long) value));
                        }
                        else if (period.equals(context.getString(R.string.period_month)))
                        {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM");
                            return sdf.format(new Date((long) value));
                        }
                        else
                        {
                            SimpleDateFormat sdf = new SimpleDateFormat("MMM");
                            return sdf.format(new Date((long) value));
                        }

                    }
                    else
                    {
                        return super.formatLabel(value, isValueX);
                    }
                }
            }));
            // for making float values completely visible in the y-axis
            viewHolder.graphView.getGridLabelRenderer().setPadding(40);
            viewHolder.graphView.getGridLabelRenderer().setHumanRounding(false, true);
            viewHolder.graphView.getViewport().setXAxisBoundsManual(true);
            viewHolder.graphView.getViewport().setMaxX(new Date().getTime());
            //viewHolder.graphView.getViewport().setMinX(data.get(position).getLowestValueX());
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        if(period.equals(context.getString(R.string.period_week)))
        {
            cal.add(Calendar.DATE, -7);

        }
        else if(period.equals(context.getString(R.string.period_month)))
        {
            cal.add(Calendar.DATE, -30);
        }
        else
        {
            cal.add(Calendar.DATE, -365);
        }
        viewHolder.graphView.getViewport().setMinX(cal.getTime().getTime());
        viewHolder.graphView.addSeries(data.get(position));
        viewHolder.nameOfSubstanceView.setText(nameOfSubstance[position]);
        viewHolder.unitView.setText(unit[position]);
        return convertView;
    }
    public void setData(ArrayList<LineGraphSeries> data)
    {
        this.data = data;
    }
    public void setUnit(String[] unit)
    {
        this.unit = unit;
    }
    public void setNameOfSubstance(String[] nameOfSubstance)
    {
        this.nameOfSubstance = nameOfSubstance;
    }
    public void setPeriod(String period)
    {
        this.period = period;
    }
    static class ViewHolder
    {
        GraphView graphView;
        TextView nameOfSubstanceView;
        TextView unitView;
    }
}