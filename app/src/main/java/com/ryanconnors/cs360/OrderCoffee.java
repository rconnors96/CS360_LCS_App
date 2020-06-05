package com.ryanconnors.cs360;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ryanconnors.cs360.LcsSQLiteSchema.OrdersTable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class OrderCoffee extends AppCompatActivity{
    private SQLiteDatabase ordersDB;
    private List<MenuItem> menuItems;
    private String username;
    private String itemNameSelected;
    private String locationName;
    private String locationAddress;
    private String monthAsString;
    private String dayAsString;
    private int hour;
    private int minute;
    private int year;
    private double total;
    private List<MenuItem> shoppingCart;
    private TextView editTotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_coffee);
        getSupportActionBar().hide();

        //initialize shopping cart
        shoppingCart = new ArrayList<>();

        //get username
        username = getIntent().getStringExtra("EXTRA_USERNAME");

        //get location
        locationName = getIntent().getStringExtra("EXTRA_LOCATION_NAME");
        locationAddress = getIntent().getStringExtra("EXTRA_LOCATION_ADDRESS");

        //get date and time
        hour = getIntent().getIntExtra("EXTRA_HOUR",1);
        minute = getIntent().getIntExtra("EXTRA_MINUTE",1);
        int month = getIntent().getIntExtra("EXTRA_MONTH", 1);
        monthAsString = monthToString(month);
        int day = getIntent().getIntExtra("EXTRA_DAY", 1);
        dayAsString = dayToString(day);
        year = getIntent().getIntExtra("EXTRA_YEAR",1);

        //get writable orders database
        ordersDB = new LcsSQLiteHandler(this).getWritableDatabase();

        //get Resources
        Resources res = this.getResources();

        //get all items on menu, then get the unique names and put them in a String array
        menuItems = getListMenuItems();
        String[] menuItemNames = getMenuItemNames(menuItems);
        ListView menuListView = findViewById(R.id.menu_items_listview);
        editTotal = findViewById(R.id.edit_total_textview);
        editTotal.setText(String.format(res.getString(R.string.dollar_amount), 0.00));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, menuItemNames);

        menuListView.setAdapter(adapter);
        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemNameSelected = (String) parent.getItemAtPosition(position);
            }
        });
    }

    public String dayToString(int day) {
        if (day < 9) {
            return "0" + (day+1);
        } else {
            return "" + (day+1);
        }
    }


    public String monthToString(int month) {
        if (month < 9) {
            return "0" + (month+1);
        } else {
            return "" + (month+1);
        }
    }

    public void onGoBackClicked(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("EXTRA_USERNAME", username);
        startActivity(intent);
    }


    private List<String> getShoppingCartDisplay(List<MenuItem> allItems) {
        String[] allItemsAsArray = getAllItemNames(allItems);
        Map<String, Integer> frequencyMap = new HashMap<>();
        List<String> shoppingCartDisplay = new ArrayList<>();

        if(allItemsAsArray.length == 0) {
            List<String> emptyList = new ArrayList<>();
            return emptyList;
        } else {
            for (String s : allItemsAsArray) {
                Integer count = frequencyMap.get(s);
                if (count == null) {
                    count = 0;
                }
                frequencyMap.put(s, count +1);
            }
            for (Map.Entry<String,Integer> entry : frequencyMap.entrySet()) {
                shoppingCartDisplay.add(entry.getKey() + "   x" + entry.getValue());
            }
        }
        return shoppingCartDisplay;
    }


    public void onSubmitOrderClicked(View view) {
        int orderID = 0;
        LcsSQLiteHandler dbHandler = new LcsSQLiteHandler(this);
        String date = monthAsString + "-" + dayAsString + "-" + year;
        String time = hour + ":" + minute;

        if(shoppingCart.size() == 0) {
            onShoppingCartClicked(view);
        } else {
            //DECIDES WHAT THE ORDER_ID SHOULD BE
            Cursor cursor = getAllDataCursor(OrdersTable.NAME);
            if (cursor.getCount() == 0) {
                orderID = 0;
            } else {
                Cursor maxValue = dbHandler.getMaxValue(ordersDB, OrdersTable.NAME, OrdersTable.Cols.ORDER_ID);
                maxValue.moveToFirst();
                orderID = maxValue.getInt(0) + 1;
            }

            //INSERTS SHOPPING CART ITEMS INTO ORDERS TABLE
            for (MenuItem currentItem : shoppingCart) {
                addOrdersItem(getOrdersContentValues(orderID, currentItem.getMENU_ID(), username,
                        date, time, locationName));
            }
            dbHandler.close();


            List<String> shoppingCartDisplayAsList = getShoppingCartDisplay(shoppingCart);
            String[] extraShoppingCartDisplay = new String[shoppingCartDisplayAsList.size()];
            extraShoppingCartDisplay = shoppingCartDisplayAsList.toArray(extraShoppingCartDisplay);
            ordersDB.close();
            Intent intent = new Intent(this, OrderSubmittedPopup.class);
            intent.putExtra("EXTRA_SHOPPING_CART_DISPLAY", extraShoppingCartDisplay);
            intent.putExtra("EXTRA_USERNAME", username);
            intent.putExtra("EXTRA_DATE", date);
            intent.putExtra("EXTRA_TIME", time);
            intent.putExtra("EXTRA_LOCATION_NAME", locationName);
            intent.putExtra("EXTRA_LOCATION_ADDRESS", locationAddress);
            intent.putExtra("EXTRA_HOUR", hour);
            intent.putExtra("EXTRA_MINUTE", minute);
            startActivity(intent);
        }

    }


    private ContentValues getOrdersContentValues(int order_id, String menu_id, String username, String date, String time, String location) {
        ContentValues values = new ContentValues();
        values.put(OrdersTable.Cols.ORDER_ID, order_id);
        values.put(OrdersTable.Cols.MENU_ID, menu_id);
        values.put(OrdersTable.Cols.USERNAME, username);
        values.put(OrdersTable.Cols.DATE, date);
        values.put(OrdersTable.Cols.TIME, time);
        values.put(OrdersTable.Cols.LOCATION, location);

        return values;
    }

    private void addOrdersItem(ContentValues values) {
        ordersDB.insert(OrdersTable.NAME, null, values);
    }


    public void onShoppingCartClicked(View view) {
        List<String> shoppingCartList = getShoppingCartDisplay(shoppingCart);
        String[] shoppingCartDisplay = new String[shoppingCartList.size()];
        shoppingCartDisplay = shoppingCartList.toArray(shoppingCartDisplay);



        Intent intent = new Intent(this, ShoppingCartPopup.class);
        intent.putExtra("EXTRA_SHOPPING_CART_DISPLAY", shoppingCartDisplay);
        startActivity(intent);
    }


    public void onAddSelectedItemClicked(View view) {
        for (MenuItem selectedItem : menuItems) {
            if (itemNameSelected.equals(selectedItem.getITEM_NAME())) {
                shoppingCart.add(selectedItem);
                total += selectedItem.getPRICE();
            }
        }
        editTotal.setText(String.format(getResources().getString(R.string.dollar_amount), total));
    }



    private String[] getAllItemNames(List<MenuItem> menuItems) {
        ArrayList<String> itemNames = new ArrayList<>();

        for (MenuItem mItem : menuItems) {
            itemNames.add(mItem.getITEM_NAME());
        }

        String[] items = new String[itemNames.size()];
        return itemNames.toArray(items);
    }


    private String[] getMenuItemNames(List<MenuItem> menuItems) {
        ArrayList<String> itemNames = new ArrayList<>();

        for (MenuItem mItem : menuItems) {
            if (!itemNames.contains(mItem.getITEM_NAME()))
            itemNames.add(mItem.getITEM_NAME());
        }

        String[] items = new String[itemNames.size()];
        return itemNames.toArray(items);
    }

    public Cursor getAllDataCursor(String tableName) {
        return ordersDB.rawQuery("select * from " + tableName, null);
    }

    public List<MenuItem> getListMenuItems() {
        Cursor cursor = getAllDataCursor(LcsSQLiteSchema.MenuTable.NAME);
        if (cursor.getCount() == 0) {
            return null;
        }

        List<MenuItem> menuItems = new ArrayList<>();
        while (cursor.moveToNext()) {
            menuItems.add(new MenuItem(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getDouble(3)
            ));
        }
        return menuItems;
    }
}
