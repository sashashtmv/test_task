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
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText editTextEmail;
    private TextInputEditText editTextPassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (TextInputEditText) findViewById(R.id.editTextPassword);
        progressBar = findViewById(R.id.progressbar);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.textViewSignup).setOnClickListener(this);
        findViewById(R.id.buttonLogin).setOnClickListener(this);
        findViewById(R.id.textView_forgotPass).setOnClickListener(this);

    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if (email.isEmpty()) {
            editTextEmail.setError("Требуется электронная почта");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Пожалуйста, введите действительный адрес электронной почты");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Требуется пароль");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6 || password.length() > 15) {
            editTextPassword.setError("Пароль должен состоять из 6-15 символов.");
            editTextPassword.requestFocus();
            return;
        }

        String upperCaseChars = "(.*[A-Z].*)";
        if (!password.matches(upperCaseChars)) {
            editTextPassword.setError("Пароль должен содержать как минимум одну цифру, одну строчную букву, одну прописную букву и один специальный символ..");
            editTextPassword.requestFocus();
            return;
        }

        String lowerCaseChars = "(.*[a-z].*)";
        if (!password.matches(lowerCaseChars)) {
            editTextPassword.setError("Пароль должен содержать как минимум одну цифру, одну строчную букву, одну прописную букву и один специальный символ..");
            editTextPassword.requestFocus();
            return;
        }

        String numbers = "(.*[0-9].*)";
        if (!password.matches(numbers)) {
            editTextPassword.setError("Пароль должен содержать как минимум одну цифру, одну строчную букву, одну прописную букву и один специальный символ.");
            editTextPassword.requestFocus();
            return;
        }

        String specialChars = "(.*[,~,!,@,#,$,%,^,&,*,(,),-,_,=,+,[,{,],},|,;,:,<,>,/,?].*$)";
        if (!password.matches(specialChars)) {
            editTextPassword.setError("Пароль должен содержать как минимум одну цифру, одну строчную букву, одну прописную букву и один специальный символ..");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    if (mAuth.getCurrentUser().isEmailVerified()) {
                        finish();
                        Intent intent = new Intent(LoginActivity.this, DrawerActivity.class);
                        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else {
                        editTextPassword.setText("");
                        Toast.makeText(LoginActivity.this, "Подтвердите свой адрес электронной почты для входа", Toast.LENGTH_LONG).show();
                        FirebaseAuth.getInstance().signOut();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(this, DrawerActivity.class));
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textViewSignup:
                Intent intentSignup = new Intent(this, SignUpActivity.class);
                intentSignup.addFlags(intentSignup.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentSignup);
                finish();
                break;

            case R.id.buttonLogin:
                userLogin();
                break;

            case R.id.textView_forgotPass:
                Intent intentResetPass = new Intent(this, ResetPassActivity.class);
                intentResetPass.addFlags(intentResetPass.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentResetPass);
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
