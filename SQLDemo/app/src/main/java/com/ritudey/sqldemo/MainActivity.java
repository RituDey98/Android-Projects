package com.ritudey.sqldemo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        try {
            //open or create database
            SQLiteDatabase myDatabase = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);
            //open or create table into the database
            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS users (name VARCHAR, age INT(3))");

            //insert data
            myDatabase.execSQL("INSERT INTO users (name,age) VALUES ('Rik',23) ");
            myDatabase.execSQL("INSERT INTO users (name,age) VALUES ('Ritu',22)");
            myDatabase.execSQL("INSERT INTO users (name,age) VALUES ('Richa',5)");
            myDatabase.execSQL("INSERT INTO users (name,age) VALUES ('Nidhi',22)");

            //get data from the database
            Cursor c = myDatabase.rawQuery("SELECT * FROM users", null);
            int nameIndex = c.getColumnIndex("name");
            int ageIndex = c.getColumnIndex("age");

            c.moveToFirst();
            while (c != null) {

                Log.i("valuesNAME", c.getString(nameIndex));
                Log.i("valuesAGE", String.valueOf(c.getInt(ageIndex)));
                c.moveToNext();
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }


    }
}