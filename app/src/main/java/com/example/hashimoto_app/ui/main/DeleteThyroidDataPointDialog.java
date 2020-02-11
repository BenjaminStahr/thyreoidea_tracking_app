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

public class DeleteThyroidDataPointDialog extends AppCompatDialogFragment
{
    private DeleteThyroidDataPointDialogListener listener;
    private String substance;
    private double dateOfDataPoint;
    private Series series;

    public DeleteThyroidDataPointDialog(String substance, double dateOfDataPoint, Series series)
    {
        this.substance = substance;
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
                        MainActivity.getDataHolder().deleteThyroidDataPoint(substance, dateOfDataPoint);
                        listener.refreshThyroidGraph(series, dateOfDataPoint, substance);
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
            listener = (DeleteThyroidDataPointDialogListener) context;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public interface DeleteThyroidDataPointDialogListener
    {
        void refreshThyroidGraph(Series series, double dataPoint, String substance);
    }
}
