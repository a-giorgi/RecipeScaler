package com.example.proportion.dialogs;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.proportion.R;

public class TextDialog extends DialogFragment {

    private String text = "";

    TextDialog.TextDialogCallback dialogCallback;
    public interface TextDialogCallback {
        void onTextConfirmed(String text);
    }

    public TextDialog(String text, TextDialog.TextDialogCallback dialogCallback){
        this.dialogCallback = dialogCallback;
        this.text = text;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.paste_text_layout, null);
        builder.setView(view);

        EditText editText = view.findViewById(R.id.editText);
        Button btnCancel = view.findViewById(R.id.btnCancel);
        Button btnConfirm = view.findViewById(R.id.btnConfirm);

        editText.setText(text);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = editText.getText().toString();
                dialogCallback.onTextConfirmed(inputText);
                dismiss();
            }
        });

        return builder.create();
    }
}