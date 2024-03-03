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

public class TotalDialog extends DialogFragment {
    double value;
    int index;

    TotalDialogCallback dialogCallback;
    interface TotalDialogCallback {
        void onTotalChanged(double value, int index);
    }
    enum Type {
        ADD,
        EDIT
    }


    public TotalDialog(TotalDialogCallback dialogCallback) {
        this.dialogCallback = dialogCallback;
        this.index = -1;
    }

    public void setData(String value, int index){
        this.value = Double.parseDouble(value);
        this.index = index;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.total_dialog, null);

        EditText editTextTotal = view.findViewById(R.id.new_total_value);


        builder.setView(view)
                .setTitle("Enter New Total Value")
                .setPositiveButton("OK", null)
                .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        Dialog dialog = builder.create();
        dialog.setOnShowListener(dialog1 -> {
            AlertDialog alertDialog = (AlertDialog) dialog1;
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                String total = editTextTotal.getText().toString();

                if(total.isEmpty()){
                    Toast.makeText(getContext(),"Please fill every field", Toast.LENGTH_SHORT).show();
                    return;
                }



                dialogCallback.onTotalChanged(Double.parseDouble(total), index);
                dismiss();
            });
        });
        dialog.setCancelable(false);
        return dialog;
    }
}