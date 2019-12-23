package com.example.hashimoto_app.ui.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.hashimoto_app.MainActivity;
import com.example.hashimoto_app.R;

import java.util.ArrayList;
import java.util.List;

public class SymptomDialog extends AppCompatDialogFragment
{
    private Spinner dialogSpinner;
    private EditText registeredValueEditText;
    private SymptomDialogListener listener;
    private MainActivity mainActivity;

    public SymptomDialog(MainActivity mainActivity)
    {
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view  = inflater.inflate(R.layout.symptom_dialog, null);
        builder.setView(view)
                .setTitle("Neuer Eintrag")
                .setNegativeButton("abbrechen", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                })
                .setPositiveButton("Eintragen", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if(!registeredValueEditText.getText().toString().equals(""))
                        {
                            String registeredValue = registeredValueEditText.getText().toString();
                            String symptom = dialogSpinner.getSelectedItem().toString();
                            listener.applySymptomTexts(registeredValue, symptom);
                        }
                    }
                });
        registeredValueEditText = view.findViewById(R.id.registeredValue);
        dialogSpinner = view.findViewById(R.id.symptom_spinner);
        final List<String> list = MainActivity.getDataHolder().getSymptoms();
        ArrayAdapter<String> adp1 = new ArrayAdapter<>(mainActivity.getApplicationContext(),
                android.R.layout.simple_list_item_1, list);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dialogSpinner.setAdapter(adp1);
        dialogSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

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
            listener = (SymptomDialogListener) context;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public interface SymptomDialogListener
    {
        void applySymptomTexts(String registeredValue, String symptom);
    }
}
