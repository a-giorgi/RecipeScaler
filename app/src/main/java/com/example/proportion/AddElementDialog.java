package com.example.proportion;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AddElementDialog extends DialogFragment {

    String name;
    double value;
    DialogCallback dialogCallback;
    interface DialogCallback {
        void onResult(String name, double value);
    }

    public AddElementDialog(DialogCallback dialogCallback) {
        this.dialogCallback = dialogCallback;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_element_dialog, null);

        EditText editTextName = view.findViewById(R.id.editTextName);
        EditText editTextValue = view.findViewById(R.id.editTextValue);

        builder.setView(view)
                .setTitle("Enter Details")
                .setPositiveButton("OK", null)
                .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        Dialog dialog = builder.create();
        dialog.setOnShowListener(dialog1 -> {
            AlertDialog alertDialog = (AlertDialog) dialog1;
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                String name = editTextName.getText().toString();
                String value =  editTextValue.getText().toString();

                if(name.isEmpty() || value.isEmpty()){
                    Toast.makeText(getContext(),"Please fill every field", Toast.LENGTH_SHORT).show();
                    return;
                }

                dialogCallback.onResult(name, Double.parseDouble(value));
                dismiss();
            });
        });
        dialog.setCancelable(false);
        return dialog;
    }
}