package com.ryanconnors.cs360;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

public class MainMenuActivity extends AppCompatActivity {

    private Intent intent;
    private String username;
    private SQLiteDatabase menuDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        //get username
        username = getIntent().getStringExtra("EXTRA_USERNAME");

        if (isFirstLaunch()) {
            createMenuTable();
        }

    }


    public void onFacebookShareClicked(View view) {
        intent = new Intent(this, FacebookShareActivity.class);
        intent.putExtra("EXTRA_USERNAME", username);
        startActivity(intent);
    }


    public void onViewOrdersButtonClicked(View view) {
        intent = new Intent(this, ViewOrders.class);
        intent.putExtra("EXTRA_USERNAME", username);
        startActivity(intent);
    }

    public void onOrderCoffeeButtonClicked(View view) {
        //MAP ACTIVITY
        intent = new Intent(this, MapsActivity.class);

        //ORDERCOFFEE ACTIVITY
        //intent = new Intent(this, OrderCoffee.class);

        intent.putExtra("EXTRA_USERNAME", username);
        startActivity(intent);
    }

    private void createMenuTable() {
        menuDB = new LcsSQLiteHandler(this).getWritableDatabase();
        addMenuItem(getMenuContentValues("000", "Drink", "Hot Coffee", 1.00));
        addMenuItem(getMenuContentValues("001", "Drink", "Iced Coffee",  1.25));
        addMenuItem(getMenuContentValues("002", "Drink", "Hot Tea",  1.25));
        addMenuItem(getMenuContentValues("003", "Drink", "Iced Tea",  1.50));
        addMenuItem(getMenuContentValues("004", "Food", "Donut", 1.50));
        addMenuItem(getMenuContentValues("005", "Food", "Bagel", 2.00));
        addMenuItem(getMenuContentValues("006", "Food", "Egg Sandwich", 3.00));
    }

    private static ContentValues getMenuContentValues(String menu_id, String type, String item_name,
                                                      double price) {
        ContentValues values = new ContentValues();
        values.put(LcsSQLiteSchema.MenuTable.Cols.MENU_ID, menu_id);
        values.put(LcsSQLiteSchema.MenuTable.Cols.TYPE, type);
        values.put(LcsSQLiteSchema.MenuTable.Cols.ITEM_NAME, item_name);
        values.put(LcsSQLiteSchema.MenuTable.Cols.PRICE, price);

        return values;
    }

    private void addMenuItem(ContentValues values) {
        menuDB.insert(LcsSQLiteSchema.MenuTable.NAME, null, values);
    }

    private boolean isFirstLaunch() {
        try {
            DB firstLaunchDB = DBFactory.open(this, "firstLaunch");

            if (firstLaunchDB.exists("firstLaunch")) {
                firstLaunchDB.close();
                return false;
            } else {
                firstLaunchDB.put("firstLaunch", "no");
                firstLaunchDB.close();
                return true;
            }
        } catch (SnappydbException e) {
            e.printStackTrace();
            System.out.println("ERROR CAUGHT WITH FIRST LAUNCH BLOCK");
        }
        return false;
    }

}
