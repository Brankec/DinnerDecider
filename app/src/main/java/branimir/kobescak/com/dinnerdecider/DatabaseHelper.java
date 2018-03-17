package branimir.kobescak.com.dinnerdecider;

import android.database.sqlite.SQLiteOpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

/**
 * Created by Gejmer on 2/21/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "orderinglist.db";
    public static final String TABLE_NAME = "ordering_table";
    public static final String COLUMN_0 = "ID";
    public static final String COLUMN_1 = "NAME";
    public static final String COLUMN_2 = "PRICE";
    public static final String COLUMN_3 = "COMPANY";
    public static final String COLUMN_4 = "PHONE_NUMBER";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_1 + " TEXT, " + COLUMN_2 + " INTEGER, " + COLUMN_3 + " TEXT, " + COLUMN_4 + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
    }

    public boolean addData(String item1, String item2, String item3, String item4) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_1, item1);
        contentValues.put(COLUMN_2, item2);
        contentValues.put(COLUMN_3, item3);
        contentValues.put(COLUMN_4, item4);

        long result = db.insert(TABLE_NAME, null, contentValues); //if it fails to enter it will return value -1

        if(result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public void editRowDataByID(int ID, String name, int price, String company, String phone) {
        final SQLiteDatabase db = this.getWritableDatabase();
        final SQLiteStatement stmt = db.compileStatement("UPDATE " + TABLE_NAME + " SET " +
                COLUMN_1 + " = ?, " + COLUMN_2 + " = ?, " + COLUMN_3 + " = ?, " + COLUMN_4 + " = ? " +
                " WHERE " + COLUMN_0 + " = ?;");
        stmt.bindString(1, name);
        stmt.bindLong(2, price);
        stmt.bindString(3, company);
        stmt.bindString(4, phone);
        stmt.bindLong(5, ID);
        stmt.execute();
    }

    public void removeDataByID(int ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        final SQLiteStatement stmt = db.compileStatement("DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_0 + " = ?;");

        stmt.bindLong(1, ID);
        stmt.execute();
    }

    public Cursor getDatalist() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        return data;
    }

    public String[] getDataByID(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] data = new String[5];
        Cursor dataTemp = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ID = " + id, null);

        dataTemp.moveToFirst();

        for(int i = 0; i < data.length; i++) {
            data[i] = dataTemp.getString(i);
        }

        return data;
    }

    public int[] getIDList() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT " + COLUMN_0 + " FROM " + TABLE_NAME, null);

        int[] IDs = new int[data.getCount()];

        for(int i = 0; i < data.getCount(); i++) {
            data.moveToNext();
            IDs[i] = Integer.parseInt(data.getString(0));
        }

        return IDs;
    }
}
