package edu.neu.madcourse.mad_goer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialog;

public class PasswordDialog extends AppCompatDialog {
    private EditText editPassword;

    public PasswordDialog(Context context) {
        super(context);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_dialog);

        AlertDialog.Builder builder = new AlertDialog.Builder(getOwnerActivity());

        LayoutInflater inflater = getOwnerActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.password_dialog, null);

        builder.setView(view)
                .setTitle(("Join"))
                .setNegativeButton("Cancel", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Go", new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        editPassword = view.findViewById(R.id.editPassword);

        return builder.create();
    }

}
