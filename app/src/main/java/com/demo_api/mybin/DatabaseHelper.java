package com.demo_api.mybin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.demo_api.mybin.model.BinDetailHistory;
import com.demo_api.mybin.model.User;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserProfile.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USER = "User";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_AVATAR = "avatar";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PHONENUMBER = "phoneNumber";
    private static final String COLUMN_ADDRESS = "address";
    private static final String TABLE_BIN = "bin";
    private static final String COLUMN_ID_BIN = "idBin";
    private static final String COLUMN_NAME_BIN = "nameBin";
    private static final String COLUMN_TIME_BIN = "timeBin";
    private static final String COLUMN_ACCURACY_BIN = "accuracyBin";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_AVATAR + " TEXT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_PHONENUMBER + " TEXT,"
                + COLUMN_ADDRESS + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);

        String CREATE_BIN_TABLE = "CREATE TABLE " + TABLE_BIN + "("
                + COLUMN_ID_BIN + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TIME_BIN + " TEXT,"
                + COLUMN_ACCURACY_BIN + " TEXT,"
                + COLUMN_NAME_BIN + " TEXT" + ")";
        db.execSQL(CREATE_BIN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BIN);
        onCreate(db);
    }

    public void addUser(User user) {
        Log.d("DEBUG1", user.toString());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, user.getUserName());
        values.put(COLUMN_PASSWORD, user.getPassword());
//        values.put(COLUMN_AVATAR, user.getAvatar());
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PHONENUMBER, user.getPhoneNumber());
        values.put(COLUMN_ADDRESS, user.getAddress());

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, new String[]{COLUMN_ID, COLUMN_USERNAME, COLUMN_AVATAR, COLUMN_PASSWORD, COLUMN_NAME, COLUMN_EMAIL, COLUMN_PHONENUMBER, COLUMN_ADDRESS},
                COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            User user = new User(
                    cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_AVATAR)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_PHONENUMBER)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS))
            );
            cursor.close();
            return user;
        } else {
            return null;
        }
    }

    public void deleteUser(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, COLUMN_ID + " = ?", new String[]{String.valueOf(userId)});
        db.close();
    }

    public void deleteAllUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, null, null);
        db.close();
    }

    public void dropTable(String dropTableIfExistsTableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS User");
        db.close();
    }

    public void createTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_AVATAR + " TEXT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_PHONENUMBER + " TEXT,"
                + COLUMN_ADDRESS + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);
    }

    public void createBinTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        String CREATE_BIN_TABLE = "CREATE TABLE " + TABLE_BIN + "("
                + COLUMN_ID_BIN + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TIME_BIN + " TEXT,"
                + COLUMN_ACCURACY_BIN + " TEXT,"
                + COLUMN_NAME_BIN + " TEXT" + ")";
        db.execSQL(CREATE_BIN_TABLE);
    }

    public User validateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, new String[]{COLUMN_ID, COLUMN_USERNAME, COLUMN_AVATAR, COLUMN_PASSWORD, COLUMN_NAME, COLUMN_EMAIL, COLUMN_PHONENUMBER, COLUMN_ADDRESS},
                COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?", new String[]{username, password}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            User user = new User(
                    cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_AVATAR)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_PHONENUMBER)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS))
            );
            cursor.close();
            return user;
        } else {
            return null;
        }
    }

    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PHONENUMBER, user.getPhoneNumber());
        values.put(COLUMN_ADDRESS, user.getAddress());
        values.put(COLUMN_PASSWORD, user.getPassword());
        // Password is not included here assuming it doesn't need to be updated from this method

        // updating row
        return db.update(TABLE_USER, values, COLUMN_ID + " = ?", new String[]{String.valueOf(user.getId())});
    }

    public void addBin(BinDetailHistory binDetailHistory) {
        Log.d("DEBUG1", binDetailHistory.toString());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TIME_BIN, binDetailHistory.getTime());
        values.put(COLUMN_ACCURACY_BIN, binDetailHistory.getAccuracy());
        values.put(COLUMN_NAME_BIN, binDetailHistory.getName());

        db.insert(TABLE_BIN, null, values);
        db.close();
    }

    public List<BinDetailHistory> getAllBin() {
        List<BinDetailHistory> binList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_BIN;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                BinDetailHistory bin = new BinDetailHistory();
//                bin.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_BIN)));
                bin.setTime(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME_BIN)));
                bin.setAccuracy(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ACCURACY_BIN)));
                bin.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_BIN)));

                binList.add(bin);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return binList;
    }
}
