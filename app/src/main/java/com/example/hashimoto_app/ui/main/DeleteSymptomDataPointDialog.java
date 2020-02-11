package com.example.hashimoto_app.ui.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.hashimoto_app.MainActivity;
import com.example.hashimoto_app.R;
import com.jjoe64.graphview.series.Series;

public class DeleteSymptomDataPointDialog extends AppCompatDialogFragment
{
    private DeleteSymptomDataPointDialogListener listener;
    private String symptom;
    private double dateOfDataPoint;
    private Series series;

    public DeleteSymptomDataPointDialog(String symptom, double dateOfDataPoint, Series series)
    {
        this.symptom = symptom;
        this.dateOfDataPoint = dateOfDataPoint;
        this.series = series;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view  = inflater.inflate(R.layout.delete_datapoint_dialog, null);
        builder.setView(view)
                .setTitle("Eintragung löschen")
                .setNegativeButton("abbrechen", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                })
                .setPositiveButton("löschen", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        MainActivity.getDataHolder().deleteSymptomDataPoint(symptom, dateOfDataPoint);
                        listener.refreshSymptomGraph(series, dateOfDataPoint, symptom);
                    }
                });
        return builder.create();
    }
    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        try
        {
            listener = (DeleteSymptomDataPointDialogListener) context;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public interface DeleteSymptomDataPointDialogListener
    {
        void refreshSymptomGraph(Series series, double dataPoint, String symptom);
    }
}
