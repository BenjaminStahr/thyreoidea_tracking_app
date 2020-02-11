package com.example.hashimoto_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PlotAdapter extends ArrayAdapter<String>
{
    private ArrayList<LineGraphSeries> data;

    private String[] unit;
    private String[] nameOfSubstance;
    private Context context;
    private String period;
    private boolean isSymptomView = false;

    public PlotAdapter(Context context, ArrayList<LineGraphSeries> data, String[] unit, String[] nameOfSubstance, String period, boolean isSymptomView)
    {
        super(context, R.layout.graph_item_plot);
        this.data = data;
        this.context = context;
        this.unit = unit;
        this.nameOfSubstance = nameOfSubstance;
        this.period = period;
        this.isSymptomView = isSymptomView;
    }

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
    //TODO Here we use the view holder pattern
    // actually we don't use it because the use produces errors when adding data
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder = new ViewHolder();
        //if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.graph_item_plot, parent, false);
            viewHolder.graphView = (GraphView) convertView.findViewById(R.id.graph);
            viewHolder.graphView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    System.out.println("clicked Graph");
                }
            });
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
            // regarding symptoms all views are scaled 1 to 5 on the y-axis
            if (isSymptomView)
            {
               viewHolder.graphView.getViewport().setYAxisBoundsManual(true);
               viewHolder.graphView.getViewport().setMaxY(5);
                viewHolder.graphView.getViewport().setMinY(1);
            }
            viewHolder.graphView.getViewport().setXAxisBoundsManual(true);
            viewHolder.graphView.getViewport().setMaxX(new Date().getTime());
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            // getting the right lower border for the graph
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
            convertView.setTag(viewHolder);
        }
        //else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.graphView.addSeries(data.get(position));
        viewHolder.nameOfSubstanceView.setText(nameOfSubstance[position]);
        viewHolder.unitView.setText(unit[position]);
        return convertView;
    }
    static class ViewHolder
    {
        GraphView graphView;
        TextView nameOfSubstanceView;
        TextView unitView;
    }
}