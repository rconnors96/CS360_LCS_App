package com.ryanconnors.cs360;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ViewSpecificOrder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_specific_order);

        DisplayMetrics displayMetrics =  new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.8));
        getSupportActionBar().hide();

        ListView shoppingCartListView = findViewById(R.id.view_specific_order_list);
        String[] shoppingCartDisplay = getIntent().getStringArrayExtra("EXTRA_SHOPPING_CART_DISPLAY");

        TextView orderNumber = findViewById(R.id.order_number_textview);
        int orderID = getIntent().getIntExtra("EXTRA_ORDER_ID",0);
        orderNumber.setText(String.format(this.getResources().getString(R.string.order_number), orderID));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, shoppingCartDisplay);
        shoppingCartListView.setAdapter(adapter);
    }

    public void onExitButtonClicked(View view) {
        this.finish();
    }
}
