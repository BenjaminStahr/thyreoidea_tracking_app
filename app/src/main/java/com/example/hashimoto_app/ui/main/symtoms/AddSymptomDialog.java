package com.example.hashimoto_app.ui.main.symtoms;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.hashimoto_app.MainActivity;
import com.example.hashimoto_app.R;

/**
 *This class task is to generate a dialog add a symptom to the general list of symptoms
 */
public class AddSymptomDialog extends AppCompatDialogFragment
{
    private AddSymptomDialogListener listener;
    private EditText symptomEditText;

    /**
     *
     * @param savedInstanceState
     * @return returns an instance of the dialog
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view  = inflater.inflate(R.layout.additional_symptom_dialog, null);
        builder.setView(view)
                .setTitle("Symptom hinzufügen")
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
                        if(!symptomEditText.getText().toString().equals(""))
                        {
                            MainActivity.getDataHolder().addSymptom(symptomEditText.getText().toString());
                            listener.refreshSymptomList();
                        }
                    }
                });
        symptomEditText = view.findViewById(R.id.symptom_name_edit_text);
        return builder.create();
    }

    /**
     * This method attaches the dialog to the overall context
     * @param context
     */
    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        try
        {
            listener = (AddSymptomDialogListener) context;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public interface AddSymptomDialogListener
    {
        void refreshSymptomList();
    }
}