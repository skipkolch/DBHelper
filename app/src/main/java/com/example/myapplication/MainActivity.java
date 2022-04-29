package com.example.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button bthAdd;
    private Button bthRead;
    private Button bthClear;

    private EditText etName;
    private EditText etEmail;
    private TextView textView;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bthAdd = findViewById(R.id.btnAdd);
        bthRead = findViewById(R.id.btnRead);
        bthClear = findViewById(R.id.btnClear);

        bthAdd.setOnClickListener(this);
        bthRead.setOnClickListener(this);
        bthClear.setOnClickListener(this);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etMail);
        textView = findViewById(R.id.textView);

        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View v) {

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        switch (v.getId()) {
            case R.id.btnAdd: {
                ContentValues contentValues = new ContentValues();

                contentValues.put(DBHelper.KEY_NAME, etName.getText().toString());
                contentValues.put(DBHelper.KEY_EMAIL, etEmail.getText().toString());

                database.insert(DBHelper.TABLE_NAME,  null, contentValues);
                break;
            }

            case R.id.btnRead: {
                Cursor cursor = database.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);

                if(cursor.moveToFirst()) {

                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
                    int emailIndex = cursor.getColumnIndex(DBHelper.KEY_EMAIL);

                    StringBuilder builder = new StringBuilder();

                    do {
                        builder.append("ID = ").append(cursor.getString(idIndex)).append("\n")
                                .append("Name = ").append(cursor.getString(nameIndex)).append("\n")
                                .append("Email = ").append(cursor.getString(emailIndex)).append("\n");
                    } while (cursor.moveToNext());

                    cursor.close();
                    textView.setText(builder.toString());

                } else {
                    textView.setText("0 rows");
                }
                break;
            }

            case R.id.btnClear: {
                database.delete(DBHelper.TABLE_NAME, null, null);
                break;
            }
        }

        database.close();
    }
}