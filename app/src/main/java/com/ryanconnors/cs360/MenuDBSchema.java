package com.ryanconnors.cs360;

import android.content.Context;
import android.content.res.AssetManager;

public class MenuDBSchema {
    public static final class MenuTable {
        public static final String NAME = "menu";

        public static final class Cols {
            public static final String TYPE = "type";
            public static final String ITEM_NAME = "item_name";
            public static final String SIZE = "size";
            public static final String PRICE = "price";
        }
    }
}
