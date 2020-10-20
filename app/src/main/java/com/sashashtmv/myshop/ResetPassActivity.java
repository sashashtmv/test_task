package com.sashashtmv.myshop;

import android.content.Intent;
import android.os.Bundle;

import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText editTextEmail;
    private ProgressBar progressBar;
    private String mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        progressBar = findViewById(R.id.progressbar);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.buttonReset).setOnClickListener(this);
        findViewById(R.id.textViewBack).setOnClickListener(this);

    }

    private void passReset() {
        mEmail = editTextEmail.getText().toString().trim();
        if (mEmail.isEmpty()) {
            editTextEmail.setError("Требуется электронная почта");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
            editTextEmail.setError("Пожалуйста, введите действительный адрес электронной почты");
            editTextEmail.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.getInstance().sendPasswordResetEmail(mEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    editTextEmail.setText("");
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ResetPassActivity.this, "Ссылка для сброса пароля отправлена на вашу электронную почту. Пожалуйста, проверьте свой почтовый ящик.", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ResetPassActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(ResetPassActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.buttonReset:
                passReset();
                break;

            case R.id.textViewBack:
                Intent intentLogin = new Intent(this, LoginActivity.class);
                intentLogin.addFlags(intentLogin.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentLogin);
                finish();
                break;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
    }
}
