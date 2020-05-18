package com.ryanconnors.cs360;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappyDB;
import com.snappydb.SnappydbException;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameFromEditText;
    private EditText passwordFromEditText;
    private String username;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void onRegisterButtonClicked(View view) {
        usernameFromEditText = findViewById(R.id.register_username);
        passwordFromEditText = findViewById(R.id.register_password);
        username = usernameFromEditText.getText().toString();
        password = passwordFromEditText.getText().toString();

        if (!username.equals("") && !password.equals("")){
            try {
                DB userpassDB = DBFactory.open(this, "userpass");

                if (userpassDB.exists(username)) {
                    TextView accountExistsPopup = findViewById(R.id.account_exists_popup);
                    accountExistsPopup.setVisibility(View.VISIBLE);
                    userpassDB.close();
                } else {
                    userpassDB.put(username, password);
                    userpassDB.close();
                    Intent intent = new Intent(this, Popup.class);
                    intent.putExtra("EXTRA_USERNAME", username);
                    intent.putExtra("EXTRA_PASSWORD", password);
                    startActivity(intent);
                }
            } catch (SnappydbException e) {
                e.printStackTrace();
                System.out.println("ERROR CAUGHT");
            }
        } //if fields not empty
    } //register button clicked

    public void onReturnLoginClicked(View view) {
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }
}
