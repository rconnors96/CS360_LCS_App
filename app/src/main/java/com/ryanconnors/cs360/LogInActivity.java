package com.ryanconnors.cs360;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LogInActivity extends AppCompatActivity {

    private EditText usernameFromEditText;
    private EditText passwordFromEditText;
    private String username;
    private String password;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }

    public void onLogInButtonClicked(View view) {
        usernameFromEditText = findViewById(R.id.username_entry);
        passwordFromEditText = findViewById(R.id.password_entry);
        username = usernameFromEditText.getText().toString();
        password = passwordFromEditText.getText().toString();


    }

    public void logInSuccess(View view) {
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }

    public void onRegisterButtonClicked(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
