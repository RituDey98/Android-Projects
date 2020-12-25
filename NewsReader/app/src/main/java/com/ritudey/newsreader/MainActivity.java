package com.ritudey.newsreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    //vars
    private ArrayList<String> mArticleTitles = new ArrayList<>();
    private ArrayList<String> mArticleContent = new ArrayList<>();

    //database
    SQLiteDatabase articleDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started");


        articleDB = this.openOrCreateDatabase("Article",MODE_PRIVATE,null);
        articleDB.execSQL("CREATE TABLE IF NOT EXISTS myArticles (id INTEGER PRIMARY KEY,articleId INTEGER, articleTitle VARCHAR,articleContent VARCHAR) ");

        DownloadTask myTask = new DownloadTask();
        try{
            //myTask.execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty");

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        updateListView();

    }


    public void updateListView()
    {
        Cursor c = articleDB.rawQuery("SELECT * FROM myArticles",null);

        int titleColumn = c.getColumnIndex("articleTitle");
        int contentColumn = c.getColumnIndex("articleContent");

        if(c.moveToFirst())
        {
            mArticleTitles.clear();
            mArticleContent.clear();
            do{
                mArticleTitles.add(c.getString(titleColumn));
                mArticleContent.add(c.getString(contentColumn));
            }while (c.moveToNext());
        }

        initRecyclerView();

    }


    //setting up recycler view

//'    private void initArticleInfo(String title,String type){
//
//        Log.d(TAG, "initArticleInfo: preparing info");
////
////        mArticleTitles.add(title);
////        mArticleTypes.add(type);
//
//
//    }'

    private void initRecyclerView()
    {
        Log.d(TAG, "recyclerView: init recycler view");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mArticleTitles,mArticleContent,getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }









    //getting the api content
    public class DownloadTask extends AsyncTask<String , Void , String>{


        @Override
        protected String doInBackground(String... urls) {

            String result="";
            URL myUrls;
            HttpsURLConnection httpsURLConnection = null;

            try {

                myUrls = new URL(urls[0]);
                httpsURLConnection = (HttpsURLConnection) myUrls.openConnection();
                InputStream inputStream = httpsURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);

                int data = reader.read() ;
                while(data != -1){

                    char current = (char) data;
                    result+=current;
                    data=reader.read();

                }





                Log.i("result:",result);

                JSONArray jsonIds = new JSONArray(result);

                //clear table
                articleDB.execSQL("DELETE FROM myArticles");


                for(int i=0 ; i<jsonIds.length();i++){

                    Log.i("result1",jsonIds.getString(i));
                    String articleID = jsonIds.getString(i);

                    myUrls = new URL("https://hacker-news.firebaseio.com/v0/item/"+articleID+".json?print=pretty");
                    httpsURLConnection = (HttpsURLConnection) myUrls.openConnection();
                    InputStream in = httpsURLConnection.getInputStream();
                    reader = new InputStreamReader(in);
                    data = reader.read();

                    String articleInfo="";

                    while (data != -1){

                        char curr = (char) data;
                        articleInfo+=curr;
                        data=reader.read();

                    }

                    Log.i("resultInfo",articleInfo);


                    //getting article info into json object
                    JSONObject jsonObject = new JSONObject(articleInfo);

                    if(!jsonObject.isNull("url") && !jsonObject.isNull("title") && !jsonObject.isNull("type")){

                        String articleUrl = jsonObject.optString("url");
                        String articleTitle = jsonObject.getString("title");
                        String articleType=jsonObject.getString("type");
                        Log.i("resultArticle",articleTitle+articleUrl+articleType);


                        //getting the content from url
//                        myUrls = new URL(articleUrl);
//                        httpsURLConnection = (HttpsURLConnection) myUrls.openConnection();
//                        in = httpsURLConnection.getInputStream();
//                        reader = new InputStreamReader(in);
//                        data = reader.read();
//
//                        String articleContent="";
//
//                        while (data != -1){
//
//                            char curr = (char) data;
//                            articleContent+=curr;
//                            data=reader.read();
//
//                        }
//
//                        Log.i("articlecontent",articleContent);

                        String sql = "INSERT INTO myArticles (articleId,articleTitle,articleContent) VALUES (? , ? , ?)";

                        SQLiteStatement statement = articleDB.compileStatement(sql);

                        statement.bindString(1,articleID);
                        statement.bindString(2,articleTitle);
                        statement.bindString(3,articleType);

                        statement.execute();

                    }


                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            updateListView();
        }
    }

}

