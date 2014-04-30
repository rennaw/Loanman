package com.loanman.app;

/**
 * Created by Owner on 4/29/2014.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    // Static Final Variable database meta information

    static final String DATABASE = "loanapp.db";
    static final int VERSION = 3;
    static final String TABLE = "loans";

    static final String C_ID = "_id";
    static final String C_NAME = "name";
    static final String C_ITEM = "item";
    static final String C_PHONE = "phone";
    static final String C_DATE = "date";

    // Override constructor
    public DBHelper(Context context) {
        super(context, DATABASE, null, VERSION);

    }

    // Override onCreate method
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create Employee table with following fields
        // _ID, ENAME, DESIGNATION and SALARY
        db.execSQL("CREATE TABLE " + TABLE + " ( " + C_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + C_NAME + " text, "
                + C_ITEM + " text, " + C_PHONE + " text, " + C_DATE + " text )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop old version table
        db.execSQL("Drop table " + TABLE);

        // Create New Version table
        onCreate(db);
    }

}

