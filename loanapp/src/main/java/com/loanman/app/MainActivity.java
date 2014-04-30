package com.loanman.app;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

    // Primitive Variables
    String selected_ID = "";

    // Widget GUI Declare
    EditText txtName, txtItem, txtPhone, txtDate;
    Button btnAddLoan, btnUpdate, btnDelete;
    ListView lvLoan;

    // DB Objects
    DBHelper helper;
    SQLiteDatabase db;

    // Adapter Object
    SimpleCursorAdapter adapter;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init DB Objects
        helper = new DBHelper(this);

        // Widget GUI Init
        txtName = (EditText) findViewById(R.id.txtName);
        txtItem = (EditText) findViewById(R.id.txtItem);
        txtPhone = (EditText) findViewById(R.id.txtPhone);
        txtDate = (EditText) findViewById(R.id.txtDate);
        lvLoan = (ListView) findViewById(R.id.lvLoan);

        btnAddLoan = (Button) findViewById(R.id.btnAdd);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        // Attached Listener
        btnAddLoan.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        lvLoan.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View v,
                                    int position, long id) {

                String name, item, phone, date;

                // Display Selected Row of Listview into EditText widget

                Cursor row = (Cursor) adapter.getItemAtPosition(position);
                selected_ID = row.getString(0);
                name = row.getString(1);
                item = row.getString(2);
                phone = row.getString(3);
                date = row.getString(4);

                txtName.setText(name);
                txtItem.setText(item);
                txtPhone.setText(phone);
                txtDate.setText(date);
            }
        });

        // Fetch Data from database
        fetchData();
    }

    @Override
    public void onClick(View v) {

        // Perform CRUD Operation

        if (v == btnAddLoan) {

            // Add Record with help of ContentValues and DBHelper class object
            ContentValues values = new ContentValues();
            values.put(DBHelper.C_NAME, txtName.getText().toString());
            values.put(DBHelper.C_ITEM, txtItem.getText().toString());
            values.put(DBHelper.C_PHONE, txtPhone.getText().toString());
            values.put(DBHelper.C_DATE, txtDate.getText().toString());

            // Call insert method of SQLiteDatabase Class and close after
            // performing task
            db = helper.getWritableDatabase();
            db.insert(DBHelper.TABLE, null, values);
            db.close();

            clearFields();
            Toast.makeText(this, "Loan Added Successfully",
                    Toast.LENGTH_LONG).show();

            // Fetch Data from database and display into listview
            fetchData();

        }
        if (v == btnUpdate) {

            // Update Record with help of ContentValues and DBHelper class
            // object

            ContentValues values = new ContentValues();
            values.put(DBHelper.C_NAME, txtName.getText().toString());
            values.put(DBHelper.C_ITEM, txtItem.getText().toString());
            values.put(DBHelper.C_PHONE, txtPhone.getText().toString());
            values.put(DBHelper.C_DATE, txtDate.getText().toString());

            // Call update method of SQLiteDatabase Class and close after
            // performing task
            db = helper.getWritableDatabase();
            db.update(DBHelper.TABLE, values, DBHelper.C_ID + "=?",
                    new String[] { selected_ID });
            db.close();

            // Fetch Data from database and display into listview
            fetchData();
            Toast.makeText(this, "Record Updated Successfully",
                    Toast.LENGTH_LONG).show();
            clearFields();

        }
        if (v == btnDelete) {

            // Call delete method of SQLiteDatabase Class to delete record and
            // close after performing task
            db = helper.getWritableDatabase();
            db.delete(DBHelper.TABLE, DBHelper.C_ID + "=?",
                    new String[] { selected_ID });
            db.close();

            // Fetch Data from database and display into listview
            fetchData();
            Toast.makeText(this, "Record Deleted Successfully",
                    Toast.LENGTH_LONG).show();
            clearFields();

        }

    }

    // Clear Fields
    private void clearFields() {
        txtName.setText("");
        txtItem.setText("");
        txtPhone.setText("");
        txtDate.setText("");
    }

    // Fetch Fresh data from database and display into listview
    private void fetchData() {
        db = helper.getReadableDatabase();
        Cursor c = db.query(DBHelper.TABLE, null, null, null, null, null, null);
        adapter = new SimpleCursorAdapter(
                this,
                R.layout.row,
                c,
                new String[] { DBHelper.C_NAME, DBHelper.C_PHONE,
                        DBHelper.C_ITEM, DBHelper.C_DATE },
                new int[] { R.id.lblName, R.id.lblPhone, R.id.lblItem, R.id.lblDate });
        lvLoan.setAdapter(adapter);
    }
}


