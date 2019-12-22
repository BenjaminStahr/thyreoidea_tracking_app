package com.example.hashimoto_app.ui.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.example.hashimoto_app.R;

public class ThyroidDialog extends AppCompatDialogFragment
{
    private Spinner dialogSpinner;
    private TextView unitTextView;
    private EditText registeredValueEditText;
    private ThyroidDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view  = inflater.inflate(R.layout.thyroid_dialog, null);
        builder.setView(view)
            .setTitle("Neuer Eintrag")
            .setNegativeButton("abbrechen", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {

                }
            })
            .setPositiveButton("Okay", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    String registeredValue = registeredValueEditText.getText().toString();
                    String substance = dialogSpinner.getSelectedItem().toString();
                    String unit = unitTextView.getText().toString();
                    listener.applyTexts(registeredValue, substance, unit);
                }
            });
        unitTextView = view.findViewById(R.id.unit_thyroid_dialog);
        registeredValueEditText = view.findViewById(R.id.registeredValue);
        dialogSpinner = view.findViewById(R.id.thyroid_spinner);
        dialogSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                // works because spinner array and unit array have the same order
                unitTextView.setText(getResources().getStringArray(R.array.thyroidUnits)[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        try
        {
            listener = (ThyroidDialogListener) context;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public interface ThyroidDialogListener
    {
        void applyTexts(String registeredValue, String substance, String unit);
    }
}
