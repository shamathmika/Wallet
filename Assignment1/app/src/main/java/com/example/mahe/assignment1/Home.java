package com.example.mahe.assignment1;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by MAHE on 21-Sep-16.
 */
public class Home extends Activity {
    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);



        setContentView(R.layout.activity_home);
    }



    public void onExp(View v){
        startActivity(new Intent("com.example.mahe.assignment1.MainActivity"));
    }


    public void onBud(View v){
        startActivity(new Intent("com.example.mahe.assignment1.BudgetMake"));
    }


    public void onLoan(View v){
        startActivity(new Intent("com.example.mahe.assignment1.LoanTrack"));
    }



    public void onRep(View v){
        startActivity(new Intent("com.example.mahe.assignment1.Report"));
    }


    public void onNote(View v){
        startActivity(new Intent("com.example.mahe.assignment1.Notepad"));
    }
}
