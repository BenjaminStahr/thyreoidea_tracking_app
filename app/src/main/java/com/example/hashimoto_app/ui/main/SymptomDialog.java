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

import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.hashimoto_app.MainActivity;
import com.example.hashimoto_app.R;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.List;

public class SymptomDialog extends AppCompatDialogFragment
{
    private Spinner dialogSpinner;
    private NumberPicker numberPicker;
    private String[] numberPickerData = {"10", "9", "8", "7", "6", "5", "4", "3", "2", "1"};
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
                        int registeredValue = Integer.valueOf(numberPickerData[numberPicker.getValue()-1]);
                        System.out.println(registeredValue);
                        String symptom = dialogSpinner.getSelectedItem().toString();
                        listener.applySymptomTexts(registeredValue, symptom);
                    }
                });
        numberPicker = view.findViewById(R.id.numberPicker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(numberPickerData.length);
        numberPicker.setDisplayedValues(numberPickerData);
        numberPicker.setValue(10);
        dialogSpinner = view.findViewById(R.id.symptom_spinner);
        setSpinnerItems();
        /*final List<String> list = MainActivity.getDataHolder().getSymptoms();
        ArrayAdapter<String> adp1 = new ArrayAdapter<>(mainActivity.getApplicationContext(),
                android.R.layout.simple_list_item_1, list);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dialogSpinner.setAdapter(adp1);*/
        dialogSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        ImageView addSymptomImageView = view.findViewById(R.id.add_symptom_image);
        addSymptomImageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mainActivity.openAddSymptomDialog();
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
            listener = (SymptomDialogListener) context;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setSpinnerItems()
    {
        final List<String> list = MainActivity.getDataHolder().getSymptoms();
        ArrayAdapter<String> adp1 = new ArrayAdapter<>(mainActivity.getApplicationContext(),
                android.R.layout.simple_list_item_1, list);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dialogSpinner.setAdapter(adp1);
    }
    public interface SymptomDialogListener
    {
        void applySymptomTexts(int registeredValue, String symptom);
    }
}
