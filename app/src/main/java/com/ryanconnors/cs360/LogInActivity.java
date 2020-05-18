package com.ryanconnors.cs360;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

public class LogInActivity extends AppCompatActivity {

    private EditText usernameFromEditText;
    private EditText passwordFromEditText;
    private String username;
    private String password;

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

        TextView revealIncorrect = findViewById(R.id.incorrect_login_popup);


        if (!username.equals("") && !password.equals("")) {
            try {
                DB userpassDB = DBFactory.open(this, "userpass");

                if (userpassDB.exists(username)) {
                    if (userpassDB.get(username).equals(password)) {
                        userpassDB.close();
                        logInSuccess(view);
                    }
                    else {
                        revealIncorrect.setVisibility(View.VISIBLE);
                        userpassDB.close();
                    }
                } else {
                    revealIncorrect.setVisibility(View.VISIBLE);
                    userpassDB.close();
                }
            } catch (SnappydbException e) {
                e.printStackTrace();
                System.out.println("ERROR CAUGHT");
            }
        }

    }

    public void logInSuccess(View view) {
        Intent intent = new Intent(this, MainMenuActivity.class);
        intent.putExtra("EXTRA_USERNAME", username);
        intent.putExtra("EXTRA_PASSWORD", password);
        startActivity(intent);
    }

    public void onRegisterButtonClicked(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
