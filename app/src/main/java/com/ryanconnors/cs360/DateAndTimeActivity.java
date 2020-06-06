package com.ryanconnors.cs360;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

public class DateAndTimeActivity extends AppCompatActivity {

    private String username, locationName, locationAddress;
    private TimePicker timePicker;
    private DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_and_time);

        //hides blue title bar
        getSupportActionBar().hide();

        timePicker = findViewById(R.id.timePicker);
        datePicker = findViewById(R.id.datePicker);

        //gets values from intent
        username = getIntent().getStringExtra("EXTRA_USERNAME");
        locationName = getIntent().getStringExtra("EXTRA_LOCATION_NAME");
        locationAddress = getIntent().getStringExtra("EXTRA_LOCATION_ADDRESS");

    }

    public void onSelectClicked(View view) {

        //obtain values from DatePicker and TimePicker
        int hourOfDay = timePicker.getHour();
        int minute = timePicker.getMinute();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();
        int year = datePicker.getYear();


        //puts all known values as intent extras to be sent to OrderCoffee
        Intent intent = new Intent(this, OrderCoffee.class);
        intent.putExtra("EXTRA_USERNAME", username);
        intent.putExtra("EXTRA_LOCATION_NAME", locationName);
        intent.putExtra("EXTRA_LOCATION_ADDRESS", locationAddress);
        intent.putExtra("EXTRA_HOUR", hourOfDay);
        intent.putExtra("EXTRA_MINUTE", minute);
        intent.putExtra("EXTRA_MONTH", month);
        intent.putExtra("EXTRA_DAY", day);
        intent.putExtra("EXTRA_YEAR", year);
        startActivity(intent);
    }

}
