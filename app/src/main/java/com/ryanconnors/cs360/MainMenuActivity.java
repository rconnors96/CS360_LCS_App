package com.ryanconnors.cs360;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

public class MainMenuActivity extends AppCompatActivity {

    private Intent intent;
    private String username;
    private String password;
    private SQLiteDatabase menuDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = getIntent().getStringExtra("EXTRA_USERNAME");
        password = getIntent().getStringExtra("EXTRA_PASSWORD");

        if (isFirstLaunch()) {
            createMenuTable();
        }

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

    private void createMenuTable() {
        menuDB = new LcsSQLiteHandler(this).getWritableDatabase();
        addMenuItem(getMenuContentValues("000", "Drink", "Hot Coffee", "s", 1.00));
        addMenuItem(getMenuContentValues("001", "Drink", "Hot Coffee", "m", 1.50));
        addMenuItem(getMenuContentValues("002", "Drink", "Hot Coffee", "l", 2.00));
        addMenuItem(getMenuContentValues("003", "Drink", "Iced Coffee", "s", 1.25));
        addMenuItem(getMenuContentValues("004", "Drink", "Iced Coffee", "m", 1.75));
        addMenuItem(getMenuContentValues("005", "Drink", "Iced Coffee", "l", 2.25));
        addMenuItem(getMenuContentValues("006", "Drink", "Hot Tea", "s", 1.25));
        addMenuItem(getMenuContentValues("007", "Drink", "Hot Tea", "m", 1.75));
        addMenuItem(getMenuContentValues("008", "Drink", "Hot Tea", "l", 2.25));
        addMenuItem(getMenuContentValues("009", "Drink", "Iced Tea", "s", 1.50));
        addMenuItem(getMenuContentValues("010", "Drink", "Iced Tea", "m", 2.00));
        addMenuItem(getMenuContentValues("011", "Drink", "Iced Tea", "l", 2.50));
        addMenuItem(getMenuContentValues("012", "Food", "Donut", "", 1.50));
        addMenuItem(getMenuContentValues("013", "Food", "Bagel", "", 2.00));
        addMenuItem(getMenuContentValues("014", "Food", "Egg Sandwich", "", 3.00));
    }

    private static ContentValues getMenuContentValues(String menu_id, String type, String item_name, String size,
                                                      double price) {
        ContentValues values = new ContentValues();
        values.put(LcsSQLiteSchema.MenuTable.Cols.MENU_ID, menu_id);
        values.put(LcsSQLiteSchema.MenuTable.Cols.TYPE, type);
        values.put(LcsSQLiteSchema.MenuTable.Cols.ITEM_NAME, item_name);
        values.put(LcsSQLiteSchema.MenuTable.Cols.SIZE, size);
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
