package com.example.hashimoto_app.ui.main.thyroid;

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
import java.util.Date;

public class ThyroidDialog extends AppCompatDialogFragment implements DatePickerDialog.OnDateSetListener
{
    private Spinner dialogSpinner;
    private TextView unitTextView;
    private EditText registeredValueEditText;
    private ThyroidDialogListener listener;
    private  TextView pickedDate;
    private Date selectedDate;
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
                        if(selectedDate != null)
                        {
                            final String registeredValue = registeredValueEditText.getText().toString();
                            final String substance = dialogSpinner.getSelectedItem().toString();
                            final String unit = unitTextView.getText().toString();
                            if(Float.valueOf(registeredValue) < 10)
                            {
                                listener.applyThyroidTexts(registeredValue, substance, unit, selectedDate);
                            }
                            else
                            {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                alertDialogBuilder.setTitle("Hinweis");
                                alertDialogBuilder
                                        .setMessage("Dieser Wert scheint unnatürlich hoch. Wollen Sie ihn dennoch eintragen?")
                                        .setPositiveButton("Ja",new DialogInterface.OnClickListener()
                                        {
                                            public void onClick(DialogInterface dialog,int id)
                                            {
                                                listener.applyThyroidTexts(registeredValue, substance, unit, selectedDate);
                                            }
                                        })
                                        .setNegativeButton("Nein", new DialogInterface.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) { }
                                        });

                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            }
                        }
                        else
                        {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                            alertDialogBuilder.setTitle("Hinweis");
                            alertDialogBuilder
                                    .setMessage("Sie haben kein Datum ausgewählt, daher wurde kein Wert eingetragen.")
                                    .setCancelable(false)
                                    .setPositiveButton("Okay",new DialogInterface.OnClickListener()
                                    {
                                        public void onClick(DialogInterface dialog,int id)
                                        {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }
                    }
                    else
                    {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                        alertDialogBuilder.setTitle("Hinweis");
                        alertDialogBuilder
                                .setMessage("Sie haben keinen Wert ausgewählt, daher wurde kein Wert eingetragen.")
                                .setCancelable(false)
                                .setPositiveButton("Okay",new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog,int id)
                                    {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
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
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth, 0, 0, 0);
        selectedDate = calendar.getTime();
    }

    public interface ThyroidDialogListener
    {
        void applyThyroidTexts(String registeredValue, String substance, String unit, Date selectedDate);
    }
}
