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

        try {
            DB snappydb = DBFactory.open(this, "userpass");

            if(snappydb.get(username).equals(password)) {
                TextView accountExistsPopup = findViewById(R.id.account_exists_popup);
                accountExistsPopup.setVisibility(View.VISIBLE);
            } else {
                snappydb.put(username, password);
            }
            snappydb.close();

            //startActivity(new Intent(RegisterActivity.this, Popup.class))

        } catch (SnappydbException e) {
            e.printStackTrace();
        }

    }
}
