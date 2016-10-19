package com.example.mahe.assignment1;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.mikephil.charting.data.BarData;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

/**
 * Created by MAHE on 21-Sep-16.
 */
public class Report extends Activity {

    SharedPreferences sh;
    SharedPreferences.Editor editor;

    LineGraphSeries<DataPoint> series;

    double x, y;

    BarChart chart;
    LinearLayout rep;



    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);

        setContentView(R.layout.activity_report);


        getActionBar().setDisplayShowTitleEnabled(false);


        sh = getSharedPreferences(check_logged_in.t,MODE_PRIVATE);
        editor = sh.edit();


        ActionBar actionBar = getActionBar();
        actionBar.setDisplayOptions(actionBar.getDisplayOptions()
                | ActionBar.DISPLAY_SHOW_CUSTOM);
        ImageView imageView = new ImageView(actionBar.getThemedContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

        imageView.setPadding(0,30,0,30);
        imageView.setImageResource(R.drawable.font1);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT, Gravity.LEFT
                | Gravity.CENTER_VERTICAL);
        layoutParams.rightMargin = 40;
        imageView.setLayoutParams(layoutParams);
        actionBar.setCustomView(imageView);

/*
        GraphView graph = (GraphView)findViewById(R.id.graph);

        DataPoint[] d = new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        };

        series = new LineGraphSeries<DataPoint>(d);
        graph.addSeries(series);*/



        chart = new BarChart(this);
        rep = (LinearLayout)findViewById(R.id.reportlayout);
        rep.addView(chart);

        //customise graph
        chart.setDescription("");
        chart.setNoDataTextDescription("No data available at the moment");
        chart.setHighlightFullBarEnabled(true);
        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setDrawGridBackground(false);
        chart.setPinchZoom(true);


        //add data
        BarData data = new BarData();
        chart.setData(data);










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
