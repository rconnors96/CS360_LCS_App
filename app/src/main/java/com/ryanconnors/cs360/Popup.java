package com.ryanconnors.cs360;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

public class Popup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        DisplayMetrics displayMetrics =  new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.8));
        getSupportActionBar().hide();

        String username = getIntent().getStringExtra("EXTRA_USERNAME");
        String password = getIntent().getStringExtra("EXTRA_PASSWORD");

        TextView given_username = findViewById(R.id.given_username);
        given_username.setText(username);
        TextView given_password = findViewById(R.id.given_password);
        given_password.setText(password);

    }

    public void onReturnLoginButton(View view) {
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }
}
