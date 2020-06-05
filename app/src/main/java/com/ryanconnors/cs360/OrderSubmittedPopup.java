package com.ryanconnors.cs360;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class OrderSubmittedPopup extends AppCompatActivity {
    private String username, locationName, locationAddress, date, time;
    private int month, day, year;
    private int hour, minute;

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
        locationName = getIntent().getStringExtra("EXTRA_LOCATION_NAME");
        locationAddress = getIntent().getStringExtra("EXTRA_LOCATION_ADDRESS");
        String[] shoppingCartDisplay = getIntent().getStringArrayExtra("EXTRA_SHOPPING_CART_DISPLAY");
        date = getIntent().getStringExtra("EXTRA_DATE");
        time = getIntent().getStringExtra("EXTRA_TIME");
        hour = getIntent().getIntExtra("EXTRA_HOUR", 1);
        minute = getIntent().getIntExtra("EXTRA_MINUTE", 1);


        ListView orderSubmittedListView = findViewById(R.id.order_submitted_cart_list);
        TextView editDate = findViewById(R.id.edit_date_textview);
        TextView editLocation = findViewById(R.id.edit_location_textview);
        TextView editTime = findViewById(R.id.edit_time_textview);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, shoppingCartDisplay);
        orderSubmittedListView.setAdapter(adapter);
        editDate.setText(date);
        editTime.setText(time);
        editLocation.setText(locationName);
        }


        public void onReturnToMainClicked(View view) {
            Intent intent = new Intent(this, MainMenuActivity.class);
            intent.putExtra("EXTRA_USERNAME", username);
            startActivity(intent);
        }

        //sets time to a default of 12:00pm to 1:00 pm
        //and sets title of event to Pickup Order
        public void onAddCalendarClicked(View view) {
            setMonthDayYear(date);
            Calendar beginTime = Calendar.getInstance();
            beginTime.set(year, month, day, hour, minute);
            Calendar endTime = Calendar.getInstance();
            endTime.set(year, month, day,hour+1,minute);
            Intent intent = new Intent(Intent.ACTION_INSERT)
                    .setData(CalendarContract.Events.CONTENT_URI)
                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                    .putExtra(CalendarContract.Events.TITLE, "Pickup order from " + locationName)
                    .putExtra(CalendarContract.Events.EVENT_LOCATION, locationAddress)
                    .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
            startActivity(intent);


        }

        public void setMonthDayYear(String date) {
            //need to subtract one from month because when the date is parsed it is in readable form
            //for example June is shown as "06", but the Calendar API requires dates to start at
            //index 0, so June would actually be 5.
            month = Integer.parseInt(date.substring(0,2)) - 1;
            day = Integer.parseInt(date.substring(3,5));
            year = Integer.parseInt(date.substring(6));
        }
}
