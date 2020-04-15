package com.example.hashimoto_app.ui.main.intake;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.hashimoto_app.MainActivity;
import com.example.hashimoto_app.R;

import java.util.List;

public class IntakeDialog extends AppCompatDialogFragment
{
    private Spinner dialogSpinner;
    private TextView unitTextView;
    private EditText registeredValueEditText;
    private IntakeDialogListener listener;
    private  MainActivity mainActivity;

    public IntakeDialog(MainActivity mainActivity)
    {
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view  = inflater.inflate(R.layout.intake_dialog, null);
        builder.setView(view)
                .setTitle("Einnahme eintragen")
                .setNegativeButton("abbrechen", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                })
                .setPositiveButton("eintragen", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if(!registeredValueEditText.getText().toString().equals("") && dialogSpinner.getSelectedItem() != null)
                        {
                            final String registeredValue = registeredValueEditText.getText().toString();
                            final String substance = dialogSpinner.getSelectedItem().toString();
                            if(checkAmountPlausible())
                            {
                                listener.applyRegisteredIntake(registeredValue, substance);
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
                                                listener.applyRegisteredIntake(registeredValue, substance);
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
        unitTextView = view.findViewById(R.id.unit_intake_dialog);
        registeredValueEditText = view.findViewById(R.id.intake_registered_value);
        dialogSpinner = view.findViewById(R.id.intake_spinner);
        setSpinnerItems();
        dialogSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                unitTextView.setText(MainActivity.getDataHolder().getUnitOfSupplement(dialogSpinner.getSelectedItem().toString()));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        Button addSubstanceButton = view.findViewById(R.id.add_symptom_button);
        addSubstanceButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mainActivity.openAddSupplementDialog();
            }
        });
        /*ImageView addSupplementImageView = view.findViewById(R.id.add_intake_image);
        addSupplementImageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mainActivity.openAddSupplementDialog();
            }
        });*/
        return builder.create();
    }

    private boolean checkAmountPlausible()
    {
        String unit = unitTextView.getText().toString();
        if(unit.equals("g"))
        {
            if (Float.valueOf(registeredValueEditText.getText().toString()) > 10)
            {
                return false;
            }
        }
        else
        {
            if (Float.valueOf(registeredValueEditText.getText().toString()) > 10000)
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        try
        {
            listener = (IntakeDialogListener) context;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void setSpinnerItems()
    {
        final List<String> list = MainActivity.getDataHolder().getSupplements();
        ArrayAdapter<String> adp1 = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, list);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dialogSpinner.setAdapter(adp1);
    }
    public interface IntakeDialogListener
    {
        void applyRegisteredIntake(String registeredValue, String substance);
    }
}
