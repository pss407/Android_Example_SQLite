package com.example.sqllite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ReadDBActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_db);

        TextView idText = (TextView) findViewById(R.id.id_layout);
        TextView pwText = (TextView) findViewById(R.id.password_layout);

        DBHelper helper = new DBHelper(this, "memberdb", null, 2);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT id, password FROM tb_member;", null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                idText.append("\n"+cursor.getString(0));
                pwText.append("\n"+cursor.getString(1));
            }
        }

        else {
            Toast.makeText(this, "조회결과가 없습니다.", Toast.LENGTH_LONG).show();
        }
        cursor.close();
        db.close();
        helper.close();
    }
}