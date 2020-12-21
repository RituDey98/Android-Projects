package com.ritudey.languagepreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView languageTv ;
    SharedPreferences sharedPreferences;


    public void setLanguage(String myLanguage){

        sharedPreferences.edit().putString("language",myLanguage).apply();
        languageTv.setText(myLanguage);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){

            case R.id.english:
                Log.i("item selected","English");
                setLanguage("English");
                return  true;

            case R.id.japanese:
                Log.i("item selected","Japanese");
                setLanguage("Japanese");
                return true;

            default:
                Log.i("default","Default");
                return  false;
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        languageTv = findViewById(R.id.my_language);
        sharedPreferences = this.getSharedPreferences("com.ritudey.languagepreferences", Context.MODE_PRIVATE);

        final String language = sharedPreferences.getString("language","");


        if(language.equals("")) {

            //adding alert box
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_btn_speak_now)
                    .setTitle("Select Language")
                    .setMessage("Choose your preferred language")
                    .setPositiveButton("English", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setLanguage("English");
                        }
                    })
                    .setNegativeButton("Japanese", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setLanguage("Japanese");
                        }
                    }).show();

        }else{

          languageTv.setText(language);

        }





    }
}