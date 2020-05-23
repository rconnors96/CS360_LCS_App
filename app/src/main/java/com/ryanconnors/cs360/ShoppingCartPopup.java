package com.ryanconnors.cs360;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ShoppingCartPopup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_cart_popup);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .8));
        getSupportActionBar().hide();


        ListView shoppingCartListView = findViewById(R.id.shopping_cart_list);
        TextView emptyCartPopup = findViewById(R.id.cart_empty_textview);
        List<String> extraItemList = Arrays.asList(getIntent().getStringArrayExtra("EXTRA_ITEM_LIST"));


        if(extraItemList.size()==0) {
            emptyCartPopup.setVisibility(View.VISIBLE);
        } else {
            Map<String, Integer> frequencyMap = new HashMap<>();
            List<String> shoppingCart = new ArrayList<>();
            for (String s : extraItemList) {
                Integer count = frequencyMap.get(s);
                if (count == null) {
                    count = 0;
                }
                frequencyMap.put(s, count +1);
            }
            for (Map.Entry<String,Integer> entry : frequencyMap.entrySet()) {
                shoppingCart.add(entry.getKey() + "   x" + entry.getValue());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, shoppingCart);
            shoppingCartListView.setAdapter(adapter);
        }
    }


    public void onExitButtonClicked(View view) {
        this.finish();
    }
}
