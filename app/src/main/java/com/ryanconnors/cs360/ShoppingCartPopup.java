package com.ryanconnors.cs360;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ShoppingCartPopup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart_popup);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .8));
        getSupportActionBar().hide();


        ListView shoppingCartListView = findViewById(R.id.shopping_cart_list);
        TextView emptyCartPopup = findViewById(R.id.cart_empty_textview);
        String[] shoppingCartDisplay = getIntent().getStringArrayExtra("EXTRA_SHOPPING_CART_DISPLAY");


        if(shoppingCartDisplay.length == 0) {
            emptyCartPopup.setVisibility(View.VISIBLE);
        } else {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, shoppingCartDisplay);
            shoppingCartListView.setAdapter(adapter);
        }
    }


    public void onExitButtonClicked(View view) {
        this.finish();
    }
}
