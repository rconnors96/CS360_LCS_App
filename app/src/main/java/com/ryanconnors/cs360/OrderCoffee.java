package com.ryanconnors.cs360;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class OrderCoffee extends AppCompatActivity {
    private Intent intent;
    private String[] menuType = {"Food", "Drinks"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_coffee);
    }

}
