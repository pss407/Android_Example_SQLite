package com.example.sqllite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText id;
    EditText pw;
    Button login;
    Button sign;
    Button select;
    DBHelper helper;
    SQLiteDatabase db;

    final static String dbName = "memberdb";
    final static int dbVersion = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = (EditText) findViewById(R.id.text_id);
        pw = (EditText) findViewById(R.id.text_pw);
        login = (Button) findViewById(R.id.login);
        sign = (Button) findViewById(R.id.sign_in);
        select = (Button) findViewById(R.id.select);
        helper = new DBHelper(this, dbName, null, dbVersion);

    }

    public void signClick(View v) {
        String ID = id.getText().toString();
        String PW = pw.getText().toString();

        db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM tb_member WHERE id = '"+ID+"';", null);

        if(cursor.getCount()==0) {
            db = helper.getWritableDatabase();
            db.execSQL("INSERT INTO tb_member(id, password) VALUES (?, ?);", new String[]{ID, PW});
            Toast.makeText(this, "회원가입에 성공했습니다.", Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this, "이미 가입한 회원입니다.", Toast.LENGTH_LONG).show();
        cursor.close();
        db.close();
    }

    public void loginClick(View v) {
        String ID = id.getText().toString();
        String PW = pw.getText().toString();

        db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, password FROM tb_member WHERE id = '"+ID+"' AND password = '"+PW+"';", null);

        if(cursor.getCount()==0)
            Toast.makeText(this, "회원가입을 해주세요.", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "로그인 되었습니다.", Toast.LENGTH_LONG).show();
        cursor.close();
        db.close();
    }

    public void selectClick(View v) {
        Intent intent = new Intent(this, ReadDBActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db = helper.getWritableDatabase();
        db.execSQL("DELETE FROM tb_member");
        db.close();
        helper.close();
    }
}
