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
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.hashimoto_app.MainActivity;
import com.example.hashimoto_app.R;

import java.util.List;

public class AddSymptomDialog extends AppCompatDialogFragment
{
    private AddSymptomDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view  = inflater.inflate(R.layout.add_symptom_dialog, null);
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
                        /*int registeredValue = Integer.valueOf(numberPickerData[numberPicker.getValue()-1]);
                        System.out.println(registeredValue);
                        String symptom = dialogSpinner.getSelectedItem().toString();
                        listener.applySymptomTexts(registeredValue, symptom);*/
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
            listener = (AddSymptomDialog.AddSymptomDialogListener) context;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public interface AddSymptomDialogListener
    {
        void applySymptomTexts(String symptom);
    }
}
