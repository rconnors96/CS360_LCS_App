package com.ryanconnors.cs360;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onViewOrdersButtonClicked(View view) {
        intent = new Intent(this, ViewOrders.class);
        startActivity(intent);
    }

    public void onOrderCoffeeButtonClicked(View view) {
        intent = new Intent(this, OrderCoffee.class);
        startActivity(intent);
    }

}
