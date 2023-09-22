// DatabaseHelper.java
package com.example.foodtruck;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ngo_database";
    private static final int DATABASE_VERSION = 2; // Incremented the version to 2
    public static final String TABLE_NGOS = "ngos";
    public static final String TABLE_DONATIONS = "donations";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHONE = "phone";

    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_USER_NAME = "Uname";
    public static final String COLUMN_ITEM_TYPE = "item_type";
    public static final String COLUMN_ITEM_QUANTITY = "item_quantity";
    public static final String COLUMN_ITEM_ADDRESS = "item_address";
public static final String COLUM_NGO_NAME = "item_ngoName";
    private static final String CREATE_TABLE_NGOS =
            "CREATE TABLE " + TABLE_NGOS + "(" +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_PHONE + " TEXT," +
                    COLUMN_PASSWORD + " TEXT" +
                    ")";

    private static final String CREATE_TABLE_DONATIONS =
            "CREATE TABLE " + TABLE_DONATIONS + "(" +
                    COLUMN_USER_NAME + " TEXT," +
                    COLUM_NGO_NAME + " TEXT," +
                    COLUMN_ITEM_TYPE + " TEXT," +
                    COLUMN_ITEM_QUANTITY + " INTEGER," +
                    COLUMN_ITEM_ADDRESS + " TEXT" +
                    ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_NGOS);
        db.execSQL(CREATE_TABLE_DONATIONS);
        insertDummyData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NGOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DONATIONS);
        onCreate(db);
    }

    public void insertDummyData(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, "NGO 1");
        values.put(COLUMN_PHONE, "+918746988083");
        values.put(COLUMN_PASSWORD, "password1");
        db.insert(TABLE_NGOS, null, values);

        values.clear();
        values.put(COLUMN_NAME, "NGO 2");
        values.put(COLUMN_PHONE, "+919480590584");
        values.put(COLUMN_PASSWORD, "password2");
        db.insert(TABLE_NGOS, null, values);

        values.clear();
        values.put(COLUMN_NAME, "NGO 3");
        values.put(COLUMN_PHONE, "+919480590584");
        values.put(COLUMN_PASSWORD, "password3");
        db.insert(TABLE_NGOS, null, values);

        values.clear();

        values.put(COLUMN_NAME, "NGO 4");
        values.put(COLUMN_PHONE, "+918746988083");
        values.put(COLUMN_PASSWORD, "password4");
        db.insert(TABLE_NGOS, null, values);

        values.clear();
    }

    public void insertDonation(Donation donation) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(COLUMN_USER_NAME, donation.getItemName());
            values.put(COLUM_NGO_NAME, donation.getNgoName());
            values.put(COLUMN_ITEM_TYPE, donation.getItemType());
            values.put(COLUMN_ITEM_QUANTITY, donation.getItemQuantity());
            values.put(COLUMN_ITEM_ADDRESS, donation.getItemAddress());

            long insertedId = db.insert(TABLE_DONATIONS, null, values);

            if (insertedId != -1) {
                Log.d("DatabaseHelper", "Donation inserted successfully. ID: " + insertedId);
            } else {
                Log.e("DatabaseHelper", "Failed to insert donation.");
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error inserting donation: " + e.getMessage());
        }
    }


    public String getNGOPhoneNumber(String ngoName) {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {COLUMN_PHONE};
        String selection = COLUMN_NAME + " = ?";
        String[] selectionArgs = {ngoName};
        Cursor cursor = db.query(TABLE_NGOS, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE));
            cursor.close();
            return phoneNumber;
        }

        return null;
    }



    public String getNGOPassword(String ngoName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_PASSWORD};
        String selection = COLUMN_NAME + " = ?";
        String[] selectionArgs = {ngoName};
        Cursor cursor = db.query(TABLE_NGOS, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD));
            cursor.close();
            return password;
        }

        return null;
    }

    public List<Donation> getAllDonations(String ngoName) {
        List<Donation> donationList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DONATIONS + " WHERE " + COLUM_NGO_NAME + " = ?", new String[]{ngoName});

        if (cursor.moveToFirst()) {
            int UnameIndex = cursor.getColumnIndex(COLUMN_USER_NAME);
            int itemTypeIndex = cursor.getColumnIndex(COLUMN_ITEM_TYPE);
            int itemQuantityIndex = cursor.getColumnIndex(COLUMN_ITEM_QUANTITY);
            int itemAddressIndex = cursor.getColumnIndex(COLUMN_ITEM_ADDRESS);
            int itemNgoNameIndex = cursor.getColumnIndex(COLUM_NGO_NAME);

            do {
                // Check if the column index is valid
                if (UnameIndex != -1 && itemTypeIndex != -1 && itemQuantityIndex != -1 && itemAddressIndex != -1) {
                    String Uname = cursor.getString(UnameIndex);
                    String itemType = cursor.getString(itemTypeIndex);
                    int itemQuantity = cursor.getInt(itemQuantityIndex);
                    String itemAddress = cursor.getString(itemAddressIndex);
                    String NgoName = cursor.getString(itemNgoNameIndex);
                    // Create a Donation object with the retrieved values
                    Donation donation = new Donation(Uname, itemType, itemQuantity, itemAddress, NgoName);
                    donationList.add(donation);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        return donationList;
    }

}
