package com.ryanconnors.cs360;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ryanconnors.cs360.LcsSQLiteSchema.MenuTable;
import com.ryanconnors.cs360.LcsSQLiteSchema.OrdersTable;

public class LcsSQLiteHandler extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "lcs.sqlite3";

    LcsSQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + MenuTable.NAME + "(" +
                MenuTable.Cols.MENU_ID + " primary key, " +
                MenuTable.Cols.TYPE + ", " +
                MenuTable.Cols.ITEM_NAME + ", " +
                MenuTable.Cols.PRICE + ")"
        );

        db.execSQL("create table if not exists " + OrdersTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                OrdersTable.Cols.ORDER_ID + ", " +
                OrdersTable.Cols.MENU_ID + ", " +
                OrdersTable.Cols.USERNAME + ", " +
                OrdersTable.Cols.DATE + "," +
                OrdersTable.Cols.TIME + "," +
                OrdersTable.Cols.LOCATION + "," +
                " FOREIGN KEY (" + OrdersTable.Cols.MENU_ID + ") REFERENCES " +
                MenuTable.NAME + "(" + MenuTable.Cols.MENU_ID + "))"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    Cursor getMaxValue(SQLiteDatabase db, String tableName, String col) {
        return db.rawQuery("select MAX(" + col + ") FROM " + tableName,
                null);
    }

}
