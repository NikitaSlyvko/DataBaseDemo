package com.example.nikita.databasedemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnAdd, btnDelete, btnUpdate, btnRead, btnClear;
    private TextView textId, textBrand, textModel;

    private PhoneDataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = (Button) findViewById(R.id.button_add_);
        btnAdd.setOnClickListener(this);

        btnDelete = (Button) findViewById(R.id.button_delete_);
        btnDelete.setOnClickListener(this);

        btnUpdate = (Button) findViewById(R.id.button_update_);
        btnUpdate.setOnClickListener(this);

        btnRead = (Button) findViewById(R.id.button_read_);
        btnRead.setOnClickListener(this);

        btnClear = (Button) findViewById(R.id.button_clear_);
        btnClear.setOnClickListener(this);

        textId = (TextView) findViewById(R.id.text_id);
        textBrand = (TextView) findViewById(R.id.text_brand);
        textModel = (TextView) findViewById(R.id.text_model);

        dbHelper = new PhoneDataBaseHelper(this);

    }

    @Override
    public void onClick(View v) {
        String id = textId.getText().toString();
        String brand = textBrand.getText().toString();
        String model = textModel.getText().toString();

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        switch(v.getId()) {
            case R.id.button_add_:
                contentValues.put(PhoneDataBaseHelper.KEY_BRAND, brand);
                contentValues.put(PhoneDataBaseHelper.KEY_MODEL, model);

                database.insert(PhoneDataBaseHelper.PHONE_TABLE, null, contentValues);
                break;

            case R.id.button_delete_:
                int delCount = 0;
                if(id.equalsIgnoreCase("")) break;
                delCount = database.delete(PhoneDataBaseHelper.PHONE_TABLE, PhoneDataBaseHelper
                        .KEY_ID + "=" + id, null);

                Log.d("mLog", "deleted rows count = " + delCount);
                break;

            case R.id.button_update_:
                if(id.equalsIgnoreCase("")) break;
                contentValues.put(PhoneDataBaseHelper.KEY_BRAND, brand);
                contentValues.put(PhoneDataBaseHelper.KEY_MODEL, model);

                int updCount = database.update(PhoneDataBaseHelper.PHONE_TABLE, contentValues,
                        PhoneDataBaseHelper.KEY_ID + "=?", new String[] {id});

                Log.d("mLog", "deleted rows count = " + updCount);
                break;

            case R.id.button_read_:
                Cursor cursor  = database.query(PhoneDataBaseHelper.PHONE_TABLE, null, null, null, null, null, null);
                if(cursor.moveToFirst()) {
                    int idIndex = cursor.getColumnIndex(PhoneDataBaseHelper.KEY_ID);
                    int brandIndex = cursor.getColumnIndex(PhoneDataBaseHelper.KEY_BRAND);
                    int modelIndex = cursor.getColumnIndex(PhoneDataBaseHelper.KEY_MODEL);

                    do {
                        Log.d("mLog", "ID = " + cursor.getInt(idIndex) + "" +
                                ", BRAND = " + cursor.getString(brandIndex)
                                + ", MODEL = " + cursor.getString(modelIndex));
                    } while (cursor.moveToNext());
                } else Log.d("mLog", "0 rows");
                cursor.close();
                break;

            case R.id.button_clear_:
                database.delete(PhoneDataBaseHelper.PHONE_TABLE, null, null);
                break;
        }
        dbHelper.close();
    }
}
