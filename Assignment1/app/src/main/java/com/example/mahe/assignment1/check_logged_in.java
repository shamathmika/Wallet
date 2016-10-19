package com.example.mahe.assignment1;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Created by MAHE on 12-Sep-16.
 */
public class check_logged_in extends Activity {

    Animation animation;
    ImageView img;
    ProgressBar p;
    int i;

    public static final String t = "PREFERENCE";
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_check);
        p = (ProgressBar)findViewById(R.id.prog);


        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (i=0;i<100;i++) {
                    try {
                        Thread.sleep(10);
                        p.setProgress(i);
                    }catch (Exception e){

                    }


                }

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        new Task().execute();
                    }
                }, 1000);

            }
        }).start();




    }



    class Task extends AsyncTask<Void,Integer,Boolean>{
        @Override
        protected void onPreExecute() {
            setContentView(R.layout.activity_check);


        }
        @Override
        public void onPostExecute(Boolean b){




            if(b) {
                Intent i = new Intent("com.example.mahe.assignment1.LoginActivity");

                startActivity(i);
                finish();
            }
            else
            {


                Intent i = new Intent("com.example.mahe.assignment1.Home");

                startActivity(i);
                finish();

            }
        }

        public Boolean doInBackground(Void... v)
        {



            boolean check = getSharedPreferences(t, Context.MODE_PRIVATE).contains("first");



            if (!check) {
                return true;

            } else {
                return false;
            }



        }

        public void onProgressUpdate(Integer... i){





        }


    }



}