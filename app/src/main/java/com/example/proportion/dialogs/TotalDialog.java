package com.example.proportion.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.proportion.R;

public class TotalDialog extends DialogFragment {
    int index;

    TotalDialogCallback dialogCallback;
    public interface TotalDialogCallback {
        void onTotalChanged(double value, int index);
    }


    public TotalDialog(TotalDialogCallback dialogCallback) {
        this.dialogCallback = dialogCallback;
        this.index = -1;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.total_dialog, null);

        EditText editTextTotal = view.findViewById(R.id.new_total_value);
        Button btnCancel = view.findViewById(R.id.btnTotCancel);
        Button btnConfirm = view.findViewById(R.id.btnTotConfirm);

        builder.setView(view).setTitle("Enter the new total");

        btnCancel.setOnClickListener(v -> dismiss());
        btnConfirm.setOnClickListener(v -> {
            String total = editTextTotal.getText().toString();

            if(total.isEmpty()){
                Toast.makeText(getContext(),"Please fill every field",

                        Toast.LENGTH_SHORT).show();
                return;
            }

            dialogCallback.onTotalChanged(Double.parseDouble(total), index);
            dismiss();
        });

        Dialog dialog = builder.create();
        dialog.setCancelable(false);
        return dialog;
    }
}