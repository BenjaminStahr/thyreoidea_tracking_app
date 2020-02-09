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
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

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
    private SymptomDialogListener listener;
    private MainActivity mainActivity;
    private int intensity = 1;

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
                        String symptom = dialogSpinner.getSelectedItem().toString();
                        listener.applySymptomTexts(intensity, symptom);
                    }
                });

        final TextView intensityIndicatorText = (TextView) view.findViewById(R.id.intensity_text);
        RadioGroup rGroup = (RadioGroup)view.findViewById(R.id.intensity_radio_group);
        final RadioButton rb_0 = (RadioButton)view.findViewById(R.id.intensity_radio_btn_0);
        final RadioButton rb_1 = (RadioButton)view.findViewById(R.id.intensity_radio_btn_1);
        final RadioButton rb_2 = (RadioButton)view.findViewById(R.id.intensity_radio_btn_2);
        final RadioButton rb_3 = (RadioButton)view.findViewById(R.id.intensity_radio_btn_3);
        final RadioButton rb_4 = (RadioButton)view.findViewById(R.id.intensity_radio_btn_4);
        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                if (rb_0.isChecked())
                {
                    intensityIndicatorText.setText("sehr schwach(1)");
                    intensity = 1;
                }
                else if (rb_1.isChecked())
                {
                    intensityIndicatorText.setText("schwach(2)");
                    intensity = 2;
                }
                else if (rb_2.isChecked())
                {
                    intensityIndicatorText.setText("mäßig(3)");
                    intensity = 3;
                }
                else if (rb_3.isChecked())
                {
                    intensityIndicatorText.setText("stark(4)");
                    intensity = 4;
                }
                else if (rb_4.isChecked())
                {
                    intensityIndicatorText.setText("sehr stark(5)");
                    intensity = 5;
                }
            }
        });
        dialogSpinner = view.findViewById(R.id.symptom_spinner);
        setSpinnerItems();

        Button addSymptomButton = view.findViewById(R.id.add_symptom_button);
        addSymptomButton.setOnClickListener(new View.OnClickListener()
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
