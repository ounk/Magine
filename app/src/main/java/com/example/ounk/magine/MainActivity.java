package com.example.ounk.magine;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Movie> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new LoadData().execute();
    }

    private class LoadData extends AsyncTask<View, Void, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressBar progressBar = findViewById(R.id.progressBar_cyclic);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(View... arg0) {
            try {
                //load and parse json
                JSONObject jOb = new JSONObject(ServiceHandler.LoadJson());
                JSONArray jArr = jOb.getJSONArray("categories");
                jOb = jArr.getJSONObject(0);
                jArr = jOb.getJSONArray("videos");

                for (int i = 0; i < jArr.length(); i++) {
                    jOb = jArr.getJSONObject(i);
                    mList.add(new Movie(jOb.getString("title"), jOb.getString("subtitle"),
                            jOb.getString("studio"), JsonToArray(jOb.getJSONArray("sources")),
                            jOb.getString("thumb"), jOb.getString("image-480x270"),
                            jOb.getString("image-780x1200"), null));
                }

                //load thumbnails
                Movie movie;
                for (int i = 0; i < mList.size(); i++) {
                    movie = mList.get(i);

                    InputStream in = new java.net.URL(ServiceHandler.strUrl + movie.getThumb()).openStream();
                    movie.setBmpIcon(BitmapFactory.decodeStream(in));
                    mList.set(i, movie);
                }
                return 0;
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        }

        @Override
        protected void onPostExecute(Integer nRes) {
            super.onPostExecute(nRes);

            if (nRes == 0) {
                ListView lv = findViewById(R.id.list_view);
                final MovieAdapter movieAdapter = new MovieAdapter(getApplicationContext(), mList);

                lv.setAdapter(movieAdapter);

                ProgressBar progressBar = findViewById(R.id.progressBar_cyclic);
                progressBar.setVisibility(View.GONE);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Movie movie = (Movie) adapterView.getItemAtPosition(i);
                        String url = movie.getSources().get(0);
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        intent.setDataAndType(Uri.parse(url), "video/mp4");
                        startActivity(intent);
                    }
                });
            } else {
                ProgressBar progressBar = findViewById(R.id.progressBar_cyclic);
                progressBar.setVisibility(View.GONE);

                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Refresh")
                        .setMessage("No data could be fetched. Try again?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                new LoadData().execute();
                            }
                        })
                        .setNegativeButton("close", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                System.exit(0);
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        }
    }


    ArrayList<String> JsonToArray(JSONArray json) {
        try {
            ArrayList<String> listdata = new ArrayList<String>();
            if (json != null) {
                for (int i = 0; i < json.length(); i++) {
                    listdata.add(json.getString(i));
                }
            }
            return listdata;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

