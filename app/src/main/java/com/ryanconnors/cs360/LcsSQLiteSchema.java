package com.ryanconnors.cs360;

import android.content.Context;
import android.content.res.AssetManager;

class LcsSQLiteSchema {
    static final class MenuTable {
        static final String NAME = "menu";

        static final class Cols {
            static final String MENU_ID = "id";
            static final String TYPE = "type"; //food or drink
            static final String ITEM_NAME = "item_name";
            static final String PRICE = "price";
        }
    }

    static final class OrdersTable {
        static final String NAME = "orders";

        static final class Cols {
            static final String ORDER_ID = "order_id";
            static final String MENU_ID = "menu_id";
            static final String USERNAME = "username";
            static final String DATE = "date";
            static final String LOCATION = "location";
        }
    }

}
