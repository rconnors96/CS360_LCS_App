package com.ryanconnors.cs360;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ryanconnors.cs360.LcsSQLiteSchema.MenuTable;
import com.ryanconnors.cs360.LcsSQLiteSchema.OrdersTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewOrders extends AppCompatActivity {
    private String username, itemSelected;
    private SQLiteDatabase orderDB;
    private TextView noExistingOrders;
    private ListView ordersList;
    private List<Integer> orderIds;
    private String[] extraShoppingCartDisplay;
    private int thisOrderId;
    private Button viewThisOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);
        getSupportActionBar().hide();

        ordersList = findViewById(R.id.orders_listview);
        noExistingOrders = findViewById(R.id.no_orders_textview);
        TextView editUsername = findViewById(R.id.edit_vieworders_username);

        viewThisOrder = findViewById(R.id.view_this_order_button);

        username = getIntent().getStringExtra("EXTRA_USERNAME");
        editUsername.setText(username);

        orderDB = new LcsSQLiteHandler(this).getReadableDatabase();
        Cursor allOrdersRows = getAllRows();
        orderIds = getOrderIDS(allOrdersRows);
        List<String> orderIdStrings = intListToStringList(orderIds);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, orderIdStrings);
        ordersList.setAdapter(adapter);
        ordersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<String> thisOrderItems = getOrdersByID(position);
                extraShoppingCartDisplay = new String[thisOrderItems.size()];
                extraShoppingCartDisplay = thisOrderItems.toArray(extraShoppingCartDisplay);
            }
        });

    }


    public void onGoBackClicked(View view) {
        Intent intent = new Intent(this, MainMenuActivity.class);
        intent.putExtra("EXTRA_USERNAME", username);
        startActivity(intent);
    }


    public void onViewThisOrderClicked(View view) {
        Intent intent = new Intent(this, ViewSpecificOrder.class);
        intent.putExtra("EXTRA_ORDER_ID", thisOrderId);
        intent.putExtra("EXTRA_SHOPPING_CART_DISPLAY", extraShoppingCartDisplay);
        startActivity(intent);
    }



    private List<String> getOrdersByID(int id) {
        //using listview's position, get the corresponding entry in the orderIds list
        thisOrderId = orderIds.get(id);
        //use this id to get all the records in Orders table that have that order_id
        Cursor rowsWithThisOrderID = orderDB.rawQuery("select * from " + OrdersTable.NAME +
                " WHERE " + OrdersTable.Cols.ORDER_ID + " = "  + thisOrderId ,null);

        //iterate through all the results and extract the item name using the menu_id supplied to orders table
        Cursor itemNameCursor;
        String menuID;
        List<String> itemsInThisOrder = new ArrayList<>();
        while (rowsWithThisOrderID.moveToNext()) {
            menuID = rowsWithThisOrderID.getString(2);
            itemNameCursor = orderDB.rawQuery("select " + MenuTable.Cols.ITEM_NAME + " from " +
                    MenuTable.NAME + " where " + MenuTable.Cols.MENU_ID + " like '" + menuID + "'",
                    null);
            itemNameCursor.moveToFirst();
            itemsInThisOrder.add(itemNameCursor.getString(0));
        }

        //return the formatted "shopping cart" display that has been used before
        return getShoppingCartDisplay(itemsInThisOrder);

    }


    //returns a formatted "shopping cart" display
    private List<String> getShoppingCartDisplay(List<String> allItems) {
        Map<String, Integer> frequencyMap = new HashMap<>();
        List<String> shoppingCartDisplay = new ArrayList<>();


        for (String s : allItems) {
            Integer count = frequencyMap.get(s);
            if (count == null) {
                count = 0;
            }
            frequencyMap.put(s, count +1);
        }
        for (Map.Entry<String,Integer> entry : frequencyMap.entrySet()) {
            shoppingCartDisplay.add(entry.getKey() + "   x" + entry.getValue());
        }
        return shoppingCartDisplay;
    }


    private List<String> intListToStringList(List<Integer> list) {
        if (list == null) {
            noExistingOrders.setVisibility(View.VISIBLE);
            viewThisOrder.setVisibility(View.INVISIBLE);
            return new ArrayList<>();
        }

        List<String> newList = new ArrayList<>(list.size());
        for (Integer thisInt : list) {
            newList.add(getDateFromOrderID(thisInt) +
                    "                    Location:" + getLocationFromOrderID(thisInt));
        }
        return newList;
    }


    private List<Integer> getOrderIDS(Cursor cursor) {
        List<Integer> orderIdList = new ArrayList<>();
        if (cursor.getCount() == 0) {
            return null;
        }

        while(cursor.moveToNext()) {
            if (!orderIdList.contains(cursor.getInt(1))) {
                orderIdList.add(cursor.getInt(1));
            }
        }

        return orderIdList;
    }


    private Cursor getAllRows() {
        Cursor allRows = orderDB.rawQuery("select * from " + OrdersTable.NAME +
                " where " + OrdersTable.Cols.USERNAME + " like '%" + username + "%'",
                null);
        allRows.moveToFirst();
        return allRows;
    }

    private String getDateFromOrderID(int orderID) {
        Cursor dateFound = orderDB.rawQuery("select date from " + OrdersTable.NAME +
                " WHERE " + OrdersTable.Cols.ORDER_ID + " = " + orderID, null);
        dateFound.moveToFirst();
        return dateFound.getString(0);
    }

    private String getLocationFromOrderID(int orderID) {
        Cursor dateFound = orderDB.rawQuery("select location from " + OrdersTable.NAME +
                " WHERE " + OrdersTable.Cols.ORDER_ID + " = " + orderID, null);
        dateFound.moveToFirst();
        return dateFound.getString(0);
    }
}
