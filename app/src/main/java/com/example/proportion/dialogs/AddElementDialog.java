package com.example.proportion.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.proportion.R;

public class AddElementDialog extends DialogFragment {

    String name;
    double value;
    int index;
    boolean allowDecimals;
    Type type;
    DialogCallback dialogCallback;
    public interface DialogCallback {
        void onResult(String name, double value, int index, boolean allowDecimals);
    }
    enum Type {
        ADD,
        EDIT
    }


    public AddElementDialog(DialogCallback dialogCallback) {
        this.type = Type.ADD;
        this.dialogCallback = dialogCallback;
        this.index = -1;
    }

    public void setData(String name, String value, boolean allowDecimals, int index){
        this.name = name;
        this.value = Double.parseDouble(value);
        this.index = index;
        this.allowDecimals = allowDecimals;
        this.type = Type.EDIT;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_element_dialog, null);

        EditText editTextName = view.findViewById(R.id.editTextName);
        EditText editTextValue = view.findViewById(R.id.editTextValue);
        CheckBox allowDecimalsCheckBox = view.findViewById(R.id.allowDecimals);


        Button btnCancel = view.findViewById(R.id.btnElCancel);
        Button btnConfirm = view.findViewById(R.id.btnElConfirm);

        if(type == Type.EDIT){
            editTextName.setText(name);
            editTextValue.setText(String.valueOf(value));
            allowDecimalsCheckBox.setChecked(allowDecimals);
        }

        builder.setView(view).setTitle("Enter an item");

        btnCancel.setOnClickListener(v -> dismiss());

        btnConfirm.setOnClickListener(v -> {
            String name = editTextName.getText().toString();
            String value = editTextValue.getText().toString();
            boolean allowDecimals = allowDecimalsCheckBox.isChecked();

            if (name.isEmpty() || value.isEmpty()) {
                Toast.makeText(getContext(), "Please fill every field",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            dialogCallback.onResult(name, Double.parseDouble(value), index, allowDecimals);
            dismiss();
        });

        Dialog dialog = builder.create();
        dialog.setCancelable(false);

        return dialog;
    }
}