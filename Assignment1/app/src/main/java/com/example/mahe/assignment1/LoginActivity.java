package com.example.mahe.assignment1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.TextView;



/**
 * Created by MAHE on 11-Sep-16.
 */
public class LoginActivity extends Activity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */





    EditText username;
    Button loginbtn;
    TextView regtv;
    EditText pass;
    ImageButton imgb;
    Animation animation;
    public static Boolean isFirst=true;
    public static boolean ch;
    SharedPreferences shar;
    SharedPreferences.Editor editor;
    InsertUserDBAdapter insdba;


    public static Boolean getisFirst() {


        return isFirst;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        insdba = new InsertUserDBAdapter(getApplicationContext());
        insdba.open();



        username = (EditText) findViewById(R.id.name_et);
        pass = (EditText) findViewById(R.id.password_et);
        loginbtn = (Button) findViewById(R.id.login_btn);
        regtv = (TextView) findViewById(R.id.register_tv);
        imgb = (ImageButton) findViewById(R.id.imageButton);
        shar = getSharedPreferences(check_logged_in.t,Context.MODE_PRIVATE);
        editor = shar.edit();


        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);
        regtv.startAnimation(animation);
        //animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_left);
        username.startAnimation(animation);
        //animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        pass.startAnimation(animation);
        //animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_left);
        loginbtn.setAnimation(animation);
        //animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_down);
        imgb.setAnimation(animation);


        regtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent("com.example.mahe.assignment1.RegisterActivity");
                startActivity(i);

            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ch = check_login();
                /*Intent i = new Intent("com.example.mahe.assignment1.check_logged_in");
                startActivity(i);
                finish();
*/
            }
        });



    }

    public boolean check_login()
    {
        int flag=0;
        if (username.getText().toString().equals("")) {
            username.setError("This field is required");
        }
        else if (pass.getText().toString().equals("")) {
            pass.setError("This field is required");
        }
        Cursor c = insdba.getAll();
        while(c.moveToNext()) {


            if (username.getText().toString().equals(c.getString(0)) && pass.getText().toString().equals(c.getString(2))) {

                flag=1;

                editor.putBoolean("first", true);
                editor.putString("@string/username", username.getText().toString());
                editor.putString("@string/pass", pass.getText().toString());
                isFirst = true;
                editor.commit();
                Intent i = new Intent("com.example.mahe.assignment1.check_logged_in");
                startActivity(i);
                finish();


            }
        }
        if(flag==0) {

            {
                Toast.makeText(getApplicationContext(),"Wrong username or password",Toast.LENGTH_LONG).show();
                editor.remove("first");
                isFirst = false;
                editor.commit();
            }

        }

        return isFirst;

    }




}