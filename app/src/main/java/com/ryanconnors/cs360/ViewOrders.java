package com.ryanconnors.cs360;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ryanconnors.cs360.LcsSQLiteSchema.OrdersTable;

import java.util.ArrayList;
import java.util.List;

public class ViewOrders extends AppCompatActivity {
    private String username;
    private SQLiteDatabase orderDB;
    private TextView noExistingOrders;
    private ListView ordersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);

        ordersList = findViewById(R.id.orders_listview);
        noExistingOrders = findViewById(R.id.no_orders_textview);
        TextView editUsername = findViewById(R.id.edit_vieworders_username);

        username = getIntent().getStringExtra("EXTRA_USERNAME");
        editUsername.setText(username);

        orderDB = new LcsSQLiteHandler(this).getReadableDatabase();
        Cursor allOrdersRows = getAllRows();
        List<Integer> orderIds = getOrderIDS(allOrdersRows);
        List<String> orderIdStrings = intListToStringList(orderIds);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, orderIdStrings);
        ordersList.setAdapter(adapter);

    }


    private List<String> intListToStringList(List<Integer> list) {
        if (list == null) {
            noExistingOrders.setVisibility(View.VISIBLE);
            return new ArrayList<>();
        }

        List<String> newList = new ArrayList<>(list.size());
        for (Integer thisInt : list) {
            newList.add("ORDER #" + thisInt);
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
        return orderDB.rawQuery("select * from " + OrdersTable.NAME, null);
    }
}
