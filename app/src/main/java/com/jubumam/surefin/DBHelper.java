package com.jubumam.surefin;
 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
 
import java.util.ArrayList;
import java.util.List;
 
public class DBHelper extends SQLiteOpenHelper {
 
    private static final int db_version = 1;                    // Database Version
    private static final String DB_FILE_NAME = "UserData.db";
 
    private static final String [] COLUMNS = {"contents TEXT", "name TEXT", "num INTEGER"};
    public static String TABLE_NAME = "my_table";
 
    public DBHelper(Context context) {
        // TODO  http://stackoverflow.com/questions/4547461/closing-the-database-in-a-contentprovider
        super(context, DB_FILE_NAME, null, db_version);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TABLE_NAME +
                " (" + BaseColumns._ID + " integer primary key autoincrement ";
        for (int i = 0; i < COLUMNS.length; i++) {
            sql += ", " + COLUMNS[i];
        }
        sql += " ) ";
        db.execSQL(sql);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
 
    public List<ItemRow> getItem() {
        List<ItemRow> list = new ArrayList<>();
        try {
            beginTransaction();
            Cursor c = getAll(TABLE_NAME);
            if (c != null) {
                int total = c.getCount();
                if (total > 0) {
                    c.moveToFirst();
                    while (!c.isAfterLast()) {
                        String contents = c.getString(1);
                        String name = c.getString(2);
                        int num = c.getInt(3);
                        list.add(new ItemRow(contents, name, num));
                        c.moveToNext();
                    }
                }
                c.close();
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            endTransaction();
        }
        return list;
    }
 
    public void setItem(String contents, String name, int num) throws SQLiteException{
        ContentValues values = new ContentValues();
        values.put("contents", contents);
        values.put("name", name);
        values.put("num", num);
        insert(TABLE_NAME, values);
    }
 
    public void setDelete() {
        AllDelete(TABLE_NAME);
    }
 
    protected Cursor getAll(String tableName) throws SQLiteException {
        return getReadableDatabase().query(tableName, null, null, null, null, null, /*"date desc"*/null);
    }
 
    protected void beginTransaction() {
        getWritableDatabase().beginTransaction();
    }
    protected void endTransaction() {
        getWritableDatabase().setTransactionSuccessful();   // db 속도 향상
        getWritableDatabase().endTransaction();
    }
 
    protected void insert(String tableName, ContentValues values) throws SQLiteException {
        getWritableDatabase().insert(tableName, null, values);
    }
 
    protected void AllDelete(String tableName) throws SQLiteException{
        getWritableDatabase().delete(tableName, null, null);
    }
}