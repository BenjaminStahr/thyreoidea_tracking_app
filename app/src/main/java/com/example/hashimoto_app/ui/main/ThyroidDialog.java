package com.example.hashimoto_app.ui.main;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.example.hashimoto_app.R;

import org.w3c.dom.Text;

import java.util.Calendar;

public class ThyroidDialog extends AppCompatDialogFragment implements DatePickerDialog.OnDateSetListener
{
    private Spinner dialogSpinner;
    private TextView unitTextView;
    private EditText registeredValueEditText;
    private ThyroidDialogListener listener;
    private  TextView pickedDate;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view  = inflater.inflate(R.layout.thyroid_dialog, null);
        builder.setView(view)
            .setTitle("Schilddrüsenwert eintragen")
            .setNegativeButton("abbrechen", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {

                }
            })
            .setPositiveButton("eintragen", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    if(!registeredValueEditText.getText().toString().equals(""))
                    {
                        String registeredValue = registeredValueEditText.getText().toString();
                        String substance = dialogSpinner.getSelectedItem().toString();
                        String unit = unitTextView.getText().toString();
                        listener.applyThyroidTexts(registeredValue, substance, unit);
                    }
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
        Button pickDateButton = (Button) view.findViewById(R.id.pickDateBtn);
        pickDateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openDatePickerDialog();
            }
        });
        pickedDate = (TextView) view.findViewById(R.id.pickedDateTextField);

        return builder.create();
    }

    private void openDatePickerDialog()
    {
        if(getActivity() != null)
        {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getActivity(),
                    this,
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        }

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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
    {
        int monthReal = month + 1;
        pickedDate.setText(dayOfMonth + "." + monthReal + "." + year);
    }

    public interface ThyroidDialogListener
    {
        void applyThyroidTexts(String registeredValue, String substance, String unit);
    }
}
