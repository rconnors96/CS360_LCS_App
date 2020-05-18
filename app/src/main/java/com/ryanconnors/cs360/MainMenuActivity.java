package com.ryanconnors.cs360;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainMenuActivity extends AppCompatActivity {

    private Intent intent;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = getIntent().getStringExtra("EXTRA_USERNAME");
        password = getIntent().getStringExtra("EXTRA_PASSWORD");
    }

    public void onViewOrdersButtonClicked(View view) {
        intent = new Intent(this, ViewOrders.class);
        intent.putExtra("EXTRA_USERNAME", username);
        intent.putExtra("EXTRA_PASSWORD", password);
        startActivity(intent);
    }

    public void onOrderCoffeeButtonClicked(View view) {
        intent = new Intent(this, OrderCoffee.class);
        intent.putExtra("EXTRA_USERNAME", username);
        intent.putExtra("EXTRA_PASSWORD", password);
        startActivity(intent);
    }

}
