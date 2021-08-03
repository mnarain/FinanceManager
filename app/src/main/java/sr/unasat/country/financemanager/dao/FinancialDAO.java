package sr.unasat.country.financemanager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import sr.unasat.country.financemanager.entities.User;


/**
 * Created by mnarain on 2/4/2016.
 * See https://www.sqlite.org/ for sqlite functionalities
 */
public class FinancialDAO extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "financial.db";
    private static final int DATABASE_VERSION = 1;

    public static final String ID = "id";

    public static final String USER_TABLE = "user";
    public static final String USER_USERNAME = "username";
    public static final String USER_PASSWORD = "password";

    public static final String TRANSACTION_TABLE = "transactionT";
    public static final String TRANSACTION_AMOUNT = "amount";
    public static final String TRANSACTION_TYPE = "type";
    public static final String TRANSACTION_TYPE_VALUE_EXPENSE = "expense";
    public static final String TRANSACTION_TYPE_VALUE_INCOME = "income";


    private static final String SQL_USER_TABLE_QUERY = "create table user(id INTEGER PRIMARY KEY, username STRING NOT NULL UNIQUE, password STRING NOT NULL)";
    private static final String SQL_TRANSACTION_TABLE_QUERY = "create table transactionT (id INTEGER PRIMARY KEY, amount REAL NOT NULL, type STRING NOT NULL)";

    public FinancialDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setDefaultCredentials();
    }

    private void setDefaultCredentials() {
        User user = findOneRecordByUsername("Tesla");
        if (user != null) {
            return;
        }
        //Set default username and password
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_USERNAME, "Tesla");
        contentValues.put(USER_PASSWORD, "Tesla");
        insertOneRecord(USER_TABLE, contentValues);
    }

    ;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_USER_TABLE_QUERY);
        db.execSQL(SQL_TRANSACTION_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insertOneRecord(String tableName, ContentValues contentValues) {
        SQLiteDatabase db = getWritableDatabase();
        long rowId = db.insert(tableName, null, contentValues);
        db.close();
        //return the row ID of the newly inserted row, or -1 if an error occurred
        return rowId;
    }

    public boolean insertMultipleRecord(String tableName, List<ContentValues> contentValuesList) {
        SQLiteDatabase db = getWritableDatabase();
        long countOnSucces = 0;
        long rowId = 0;
        for (ContentValues contentValues : contentValuesList) {
            rowId = db.insert(tableName, null, contentValues);
            countOnSucces = (rowId == 1 ? countOnSucces++ : countOnSucces);
        }
        boolean isSuccess = (countOnSucces > 0 && contentValuesList.size() == countOnSucces);
        db.close();
        //return the true id all inserts where succesfull
        return isSuccess;
    }

    public User findOneRecordByUsername(String username) {
        User user = null;
        SQLiteDatabase db = getReadableDatabase();
        String sql = String.format("select id, username, password from %s where %s = '%s'", USER_TABLE, USER_USERNAME, username);
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            user = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
        }
        db.close();
        return user;
    }

    public List<User> findAllRecords(String table) {
        List<User> users = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = String.format("select * from %s", table);
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            users.add(new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
        }
        db.close();
        return users;
    }

    public int updateRecord(String table, ContentValues contentValues, String username) {
        SQLiteDatabase db = getWritableDatabase();
        int effectedRows = 0;
        String whereClause = String.format("%s = ?",USER_USERNAME);
        String[] whereArgs = {username};
        effectedRows = db.update(table, contentValues, whereClause, whereArgs); // update user set username = [new value] where username = 'admin';
        return effectedRows;
    }

    public int deleteRecord(String table, String username) {
        SQLiteDatabase db = getWritableDatabase();
        int effectedRows = 0;
        String whereClause = String.format("%s = ?",USER_USERNAME);
        String[] whereArgs = {username};
        effectedRows = db.delete(table, whereClause, whereArgs); // delete from user where username = 'spiderman2'
        return effectedRows;
    }

/*    public int updateRecord(ContentValues contentValues, String id, String username) {
        SQLiteDatabase db = getWritableDatabase();
        int effectedRows = 0;
        String whereClause = String.format("%s = ? OR %s = ?", ID, USER_USERNAME);
        String[] whereArgs = {id, username};
        effectedRows = db.update(USER_TABLE, contentValues, whereClause, whereArgs); // update user where id = 1 or username = 'admin'
        return effectedRows;
    }*/



/*    public int deleteRecord(String weight, String date) {
        SQLiteDatabase db = getWritableDatabase();
        int effectedRows = 0;
        String whereClause = String.format("%s = ? OR %s = ?", TABLE_WI_INFO_WEIGHT, TABLE_WI_INFO_DATE);
        String[] whereArgs = {weight, date};
        effectedRows = db.delete(TABLE_WI_INFO_NAME, whereClause, whereArgs);
        return effectedRows;
    }*/

}