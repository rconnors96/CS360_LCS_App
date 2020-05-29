package com.ryanconnors.cs360;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class OrderSubmittedPopup extends AppCompatActivity {
    private String username, location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_submitted_popup);

        DisplayMetrics displayMetrics =  new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.8));
        getSupportActionBar().hide();

        username = getIntent().getStringExtra("EXTRA_USERNAME");
        location = getIntent().getStringExtra("EXTRA_LOCATION");
        String[] shoppingCartDisplay = getIntent().getStringArrayExtra("EXTRA_SHOPPING_CART_DISPLAY");
        String date = getIntent().getStringExtra("EXTRA_DATE");


        ListView orderSubmittedListView = findViewById(R.id.order_submitted_cart_list);
        TextView editDate = findViewById(R.id.edit_date_textview);
        TextView editLocation = findViewById(R.id.edit_location_textview);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, shoppingCartDisplay);
        orderSubmittedListView.setAdapter(adapter);
        editDate.setText(date);
        editLocation.setText(location);
        }


        public void onReturnToMainClicked(View view) {
            Intent intent = new Intent(this, MainMenuActivity.class);
            intent.putExtra("EXTRA_USERNAME", username);
            startActivity(intent);
        }
    }
