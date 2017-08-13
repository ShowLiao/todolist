package com.example.show.todolistv2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by show on 8/7/17.
 */

public class TodoItemDB extends SQLiteOpenHelper {

    private static TodoItemDB sInstance;

    //DB
    private static final String DB_NAME = "TodoListDB";
    private static final int DATABASE_VERSION = 1;

    //Table
    private static final String ITEM = "item";

    //Columns
    private static final String ID_COL = "id";
    private static final String TASK_COL = "task";
    private static final String DATE_COL = "dueDate";
    private static final String LOCATION_COL = "location";
    private static final String DETAIL_COL = "detail";
    private static final String TABLE_NAME = "TodoList";
    private static final String PRIORITY_COL = "priority";

    public TodoItemDB(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    public static synchronized TodoItemDB getsInstance(Context context) {

        if (null == sInstance) {
            sInstance = new TodoItemDB(context.getApplicationContext());
        }

        return sInstance;

    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "Create Table " + TABLE_NAME + "(" +
                ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TASK_COL + " TEXT NOT NULL, " +
                DATE_COL + " TEXT, " +
                LOCATION_COL + " TEXT, " +
                DETAIL_COL + " TEXT, " +
                PRIORITY_COL + " INTEGER NOT NULL)";

        Log.i("==SQL:", CREATE_TABLE);
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABL IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    public void addItem(Item item) {

        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        try {

            ContentValues values = new ContentValues();
            values.put(TASK_COL, item.getTask());
            values.put(DATE_COL, item.getDate());
            values.put(LOCATION_COL, item.getLocation());
            values.put(DETAIL_COL, item.getDetail());
            values.put(PRIORITY_COL, item.getPriority());

            db.insertOrThrow(TABLE_NAME, null, values);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }

    }

    public void updateInfo(Item item) {

        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            values.put(TASK_COL, item.getTask());
            values.put(DATE_COL, item.getDate());
            values.put(LOCATION_COL, item.getLocation());
            values.put(DETAIL_COL, item.getDetail());
            values.put(PRIORITY_COL, item.getPriority());

            int row = db.update(TABLE_NAME, values, ID_COL + "= " + item.getId(), null);
            if (row > 0) {
                Log.d("Todo DB", "update successed!!");
            }
            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }

    }

    public void delItem(int id) {

        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        try {

            int row = db.delete(TABLE_NAME, ID_COL + " = " + id, null);
            if (row > 0) {
                Log.d("Todo DB", "delete successed!!");
            }
            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public Item query(int id) {
        String QUERY = "SELECT * FROM " + TABLE_NAME + " where id = " + id ;

        SQLiteDatabase db = getReadableDatabase();
        Item item = new Item();
        db.beginTransaction();

        try {
            Cursor cursor = db.rawQuery(QUERY, null);

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()){

                    item.setId(cursor.getInt(0));
                    item.setTask(cursor.getString(1));
                    item.setDate(cursor.getString(2));
                    item.setLocation(cursor.getString(3));
                    item.setDetail(cursor.getString(4));
                    item.setPriority(cursor.getInt(5));
                    Log.d("==id:", String.valueOf(cursor.getInt(0)));
                    Log.d("==task", cursor.getString(1));
                    Log.d("date", cursor.getString(2));
                    Log.d("location", cursor.getString(3));
                    Log.d("detail", cursor.getString(4));

                    cursor.moveToNext();
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return item;
    }

    public ArrayList<Item> queryAll() {
        ArrayList<Item> list = new ArrayList<>();

        String QUERY = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = getReadableDatabase();

        db.beginTransaction();

        try {
            Cursor cursor = db.rawQuery(QUERY, null);

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()){
                    Item item = new Item();

                    item.setId(cursor.getInt(0));
                    item.setTask(cursor.getString(1));
                    item.setDate(cursor.getString(2));
                    item.setLocation(cursor.getString(3));
                    item.setDetail(cursor.getString(4));
                    item.setPriority(cursor.getInt(5));
                    Log.d("===id:", String.valueOf(item.getId()));
                    Log.d("task", item.getTask());
                    Log.d("date", item.getDate());
                    Log.d("location", item.getLocation());
                    Log.d("detail", item.getDetail());
                    Log.d("setPriority", String.valueOf(item.getPriority()));
                    list.add(item);
                    cursor.moveToNext();
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return list;
    }
}
