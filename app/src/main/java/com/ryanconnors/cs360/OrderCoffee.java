package com.ryanconnors.cs360;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ryanconnors.cs360.LcsSQLiteSchema.OrdersTable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class OrderCoffee extends AppCompatActivity{
    private SQLiteDatabase ordersDB;
    private List<MenuItem> menuItems;
    private String[] menuItemNames;
    private String username, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_coffee);
        username = getIntent().getStringExtra("EXTRA_USERNAME");
        password = getIntent().getStringExtra("EXTRA_PASSWORD");

        //get writable orders database
        ordersDB = new LcsSQLiteHandler(this).getWritableDatabase();

        //get all items on menu, then get the unique names and put them in a String array
        menuItems = getListMenuItems();
        menuItemNames = getMenuItemNames(menuItems);
        ListView menuListView = findViewById(R.id.menu_items_listview);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, menuItemNames);

        menuListView.setAdapter(adapter);
        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemSelected = (String) parent.getItemAtPosition(position);
            }
        });
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
                    cursor.getString(3),
                    cursor.getDouble(4)
            ));
        }
        return menuItems;
    }


    /*
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
    */
}
