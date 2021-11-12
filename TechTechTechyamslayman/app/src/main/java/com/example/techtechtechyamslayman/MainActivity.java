package com.example.techtechtechyamslayman;

import androidx.annotation.DoNotInline;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.VideoView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    public ArrayList<String> urlList = new ArrayList<String>();
    public ArrayList<String> titleList = new ArrayList<String>();
    public ArrayList<String> idList = new ArrayList<String>();
    public ArrayList<String> JSONID = new ArrayList<String>();
    public ArrayList<String> SelectedID = new ArrayList<String>();
    SQLiteDatabase db;
    public static String EXTRA_MESSAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//hello
        try {
            db = this.openOrCreateDatabase("TechDB", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS news (id VARCHAR, title VARCHAR, url VARCHAR)");

        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            Cursor c = db.rawQuery("Select * from news", null);
            int idIndex = c.getColumnIndex("id");
            int titleIndex = c.getColumnIndex("title");
            int urlIndex = c.getColumnIndex("url");
            c.moveToFirst();
            while (c != null) {
                Log.i(
                        "ID", c.getString(idIndex)
                );
                idList.add(c.getString(idIndex));
                Log.i(
                        "Title", c.getString(titleIndex)
                );
                titleList.add(c.getString(titleIndex));
                Log.i(
                        "URL", c.getString(urlIndex)
                );
                urlList.add(c.getString(urlIndex));
                c.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ListView l = (ListView) findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titleList);
        l.setAdapter(adapter);

        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("IDX", String.valueOf(i));
                Intent intent = new Intent(getApplicationContext(), webview.class);

                intent.putExtra(EXTRA_MESSAGE, urlList.get(i));
                startActivity(intent);

            }
        });

        VideoView bg = (VideoView) findViewById(R.id.bgVid);
        bg.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.tech);
        bg.start();

        bg.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });

//        DownloadTask task = new DownloadTask();
//        String res = "";
//        String res2 = "";
//        try {
//            res = task.execute("https://hacker-news.firebaseio.com/v0/topstories.json").get();
//            splitArr(res);
//            Log.i("RESULT", res);
//
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


//        for (int i = 0; i < SelectedID.size(); i++) { //https://hacker-news.firebaseio.com/v0/item/[ARTICLE_ID].json?print=pretty
//            String APIURL = "https://hacker-news.firebaseio.com/v0/item/" + SelectedID.get(i) + ".json?print=pretty";
//            DownloadJSON task2 = new DownloadJSON();
//
//            try {
//                res2 = task2.execute(APIURL).get();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//
//    }
//
//    public class DownloadTask extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... urls) {
//            URL url;
//            HttpURLConnection urlConnection;
//            String result = "";
//
//            try {
//                url = new URL(urls[0]);
//                urlConnection = (HttpURLConnection) url.openConnection();
//                InputStream in = urlConnection.getInputStream();
//                InputStreamReader reader = new InputStreamReader(in);
//                int data = reader.read();
//                while (data != -1) {
//                    char c = (char) data;
//                    result += c;
//                    data = reader.read();
//                }
//                return result;
//
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                return null;
//            }
//
//        }
//
//    }

//    public class DownloadJSON extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... strings) {
//            URL url;
//            HttpURLConnection urlConnection;
//            String result = "";
//            try {
//                url = new URL(strings[0]);
//                urlConnection = (HttpURLConnection) url.openConnection();
//                InputStream in = urlConnection.getInputStream();
//                InputStreamReader reader = new InputStreamReader(in);
//                int data = reader.read();
//                while (data != -1) {
//
//                    char c = (char) data;
//                    result += c;
//                    data = reader.read();
//                }
//                return result;
//            } catch (Exception e) {
//                e.printStackTrace();
//                return null;
//            }
//        }

//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            try {
//                JSONObject json = new JSONObject(s);
//                String url = json.getString("url");
//                Log.i("URL: ", url);
//                urlList.add(url);
//                String title = json.getString("title");
//                Log.i("Title: ", title);
//                titleList.add(title);
//                String ID = json.getString("id");
//                Log.i("ID: ", ID);
//                idList.add(ID);
//                db.execSQL("INSERT INTO news(id,title,url) VALUES(\'" +ID+"\',\'"+title+"\',\'"+url+"\')");
////                Log.i("URL LIST", urlList.toString());
////                Log.i("TITLE LIST", titleList.toString());
////                Log.i("ID LIST", idList.toString());
////                JSONArray urlArr = new JSONArray(url);
////                Log.i("URLARR: ", urlArr.getString(0));
////                JSONArray titleArr = new JSONArray(title);
////                JSONArray idArr = new JSONArray(ID);
////
////                    JSONObject part1 = urlArr.getJSONObject(0);
////                    JSONObject part2 = titleArr.getJSONObject(0);
////                    JSONObject part3 = idArr.getJSONObject(0);
////                    Log.i("Part1 URL", part1.toString());
////                    Log.i("Part2 TITLE", part2.toString());
////                    Log.i("Part2 ID", part3.toString());
////                    urlList.add(part1.getString("url"));
////                    titleList.add(part2.getString("title"));
////                    idList.add(part3.getString("id"));
//
//            } catch (Exception e) {
//                e.printStackTrace();
//
//            }
//        }
//    }
//
//    public void splitArr(String s) {
//        JSONID.addAll(Arrays.asList(s.split(",")));
//        Log.i("JSONID", JSONID.toString());
//        Random rand = new Random();
//        for (int i = 0; i < 20; i++) {
//            int idx = rand.nextInt(JSONID.size());
//            SelectedID.add(JSONID.get(idx));
//            JSONID.remove(idx);
//        }
//        Log.i("SELECTED ID", SelectedID.toString());
//
    }
}
