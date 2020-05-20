package com.ryanconnors.cs360;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ryanconnors.cs360.LcsSQLiteSchema.OrdersTable;


public class OrderCoffee extends AppCompatActivity {
    private SQLiteDatabase ordersDB;
    private Spinner foodDrinkSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_coffee);
        ordersDB = new LcsSQLiteHandler(this).getWritableDatabase();

        foodDrinkSpinner = findViewById(R.id.food_drink_spinner);
        foodDrinkSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void getOrdersContentValues(String order_id, String menu_id, String username, String date) {
        ContentValues values = new ContentValues();
        values.put(OrdersTable.Cols.ORDER_ID, order_id);
        values.put(OrdersTable.Cols.MENU_ID, menu_id);
        values.put(OrdersTable.Cols.USERNAME, username);
        values.put(OrdersTable.Cols.DATE, date);
    }

    private void addOrdersItem(ContentValues values) {
        ordersDB.insert(OrdersTable.NAME, null, values);
    }

    public Cursor getAllDataCursor(String tableName) {
        return ordersDB.rawQuery("select * from " + tableName, null);
    }

    public String getAllDataString(String tableName) {
        Cursor cursor = getAllDataCursor(tableName);
        if (cursor.getCount() == 0) {
            return "NO DATA";
        }
        StringBuilder sb = new StringBuilder();
        while (cursor.moveToNext()) {
            sb.append("menu_id: " + cursor.getString(0));
            sb.append("..type: " + cursor.getString(1));
            sb.append("..item_name: " + cursor.getString(2));
            sb.append("..size: " + cursor.getString(3));
            sb.append("..price: " + cursor.getString(4));
        }

        return sb.toString();
    }

}
