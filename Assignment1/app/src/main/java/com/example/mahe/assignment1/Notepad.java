package com.example.mahe.assignment1;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

/**
 * Created by MAHE on 21-Sep-16.
 */
public class Notepad extends Activity{


    SharedPreferences sh;
    SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
    setContentView(R.layout.activity_note);




    getActionBar().setDisplayShowTitleEnabled(false);


    sh = getSharedPreferences(check_logged_in.t,MODE_PRIVATE);
    editor = sh.edit();



    ActionBar actionBar = getActionBar();
    actionBar.setDisplayOptions(actionBar.getDisplayOptions()
            | ActionBar.DISPLAY_SHOW_CUSTOM);
    ImageView imageView = new ImageView(actionBar.getThemedContext());
    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

    imageView.setPadding(0, 30, 0, 30);
    imageView.setImageResource(R.drawable.font1);
    ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
            ActionBar.LayoutParams.WRAP_CONTENT,
            ActionBar.LayoutParams.WRAP_CONTENT, Gravity.LEFT
            | Gravity.CENTER_VERTICAL);
    layoutParams.rightMargin = 40;
    imageView.setLayoutParams(layoutParams);
    actionBar.setCustomView(imageView);
}


    @Override
    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.menu_options, m);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem mi) {
        switch(mi.getItemId()) {
            case R.id.logout: {
                editor.remove("first");
                editor.commit();
                Intent i = new Intent("com.example.mahe.assignment1.LoginActivity");
                i.putExtra("finishingallact", "yes");
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                finishAffinity();
                startActivity(i);
                finish();
            }
            break;

            default:
                return false;
        }
        return true;

    }
}
