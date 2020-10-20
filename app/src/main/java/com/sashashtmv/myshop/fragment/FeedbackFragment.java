package com.sashashtmv.myshop.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.sashashtmv.myshop.R;
import com.sashashtmv.myshop.helper.SendMail;

public class FeedbackFragment extends Fragment implements View.OnClickListener {

    private EditText editTextMessage;
    private Button buttonSend;
    private String name;
    private String email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_feedback, container, false);
        editTextMessage = (EditText) v.findViewById(R.id.editTextMessage);
        buttonSend = (Button) v.findViewById(R.id.buttonSend);
        buttonSend.setOnClickListener(this);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
            email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        }
        else {
            name = "";
            email = "";
        }

        return v;
    }

    private void sendEmail() {
        String email = "sashashtmv06@gmail.com";
        String subject = "[Отзыв] " + name;
        String message = editTextMessage.getText().toString().trim() + "\n\nотправлено на " + this.email;
        SendMail sm = new SendMail(getActivity(), email, subject, message);
        sm.execute();
    }

    @Override
    public void onClick(View v) {
        if (editTextMessage.getText().toString().length() < 1) {
            editTextMessage.setError(getString(R.string.permit_message));
            editTextMessage.requestFocus();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.feedback_single);
        builder.setMessage(R.string.feedback_valuable);

        builder.setPositiveButton(R.string.send, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendEmail();
                editTextMessage.setText("");
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editTextMessage.setText("");
                Toast.makeText(getActivity(), R.string.message_discarded, Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog ad = builder.create();
        ad.show();
    }
}
