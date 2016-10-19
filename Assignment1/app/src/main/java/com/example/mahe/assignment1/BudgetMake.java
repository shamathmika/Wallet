package com.example.mahe.assignment1;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MAHE on 21-Sep-16.
 */
public class BudgetMake extends Activity {

    int y, m, d;
    java.util.Calendar c= java.util.Calendar.getInstance();
    int ye = c.get(c.YEAR);
    int mo = c.get(c.MONTH);
    int da = c.get(c.DAY_OF_MONTH);
    EditText amt;
    ArrayAdapter<Budget> adapter;
    List<Budget> bud;


    SharedPreferences sh;
    SharedPreferences.Editor editor;



    @Override
    public void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.activity_budget);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        getActionBar().setDisplayShowTitleEnabled(false);


        sh = getSharedPreferences(check_logged_in.t,MODE_PRIVATE);
        editor = sh.edit();


        bud = new ArrayList<Budget>();
        populateListView();

        registerClickCallback();


        final EditText e = (EditText) findViewById(R.id.cal);
        //Date picker dialog to open when edit text is clicked
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                y = year;
                m = monthOfYear;
                d = dayOfMonth;
                if (y < ye || (y >= ye && m < mo) || (y >= ye && m >= mo && d < da)) {
                    Toast.makeText(getBaseContext(), "Oops. That's in the past!", Toast.LENGTH_LONG).show();
                } else {
                    e.setText(d + "-" + getMonth(m) + "-" + y);
                }
            }

        };

        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new DatePickerDialog(BudgetMake.this, R.style.MyDialogTheme, date, ye, mo, da).show();
            }

        });


        //To put an image in actionbar/toolbar
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





    }


    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month];
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


    //TODO onSave store in database
    public void onSave(View view)
    {

        final EditText inc1 = (EditText) findViewById(R.id.income);
        String inc = inc1.getText().toString();
        final EditText date1 = (EditText) findViewById(R.id.cal);
        String date = date1.getText().toString();
        final EditText budg1 = (EditText) findViewById(R.id.budg);
        String budg = budg1.getText().toString();

       if(inc.equals(""))
            inc1.setText("-");
        else if(date.equals(""))
            date1.setError("Please choose a date");
        else if(budg.equals(""))
            budg1.setError("Please enter amount");
       else if(MainActivity.totexp > (Integer.parseInt(budg)-100)){

           //TODO send notification

           Toast.makeText(getApplicationContext(),"Expense is almost closing on budget!",Toast.LENGTH_LONG).show();

       }


        else
        {
            TextView t = (TextView)findViewById(R.id.listtxt);
            t.setVisibility(View.VISIBLE);

            // totexp+= Integer.parseInt(amount);
            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            populateBudgetList();
            populateListView();
        }



    }


    private void populateBudgetList() {
        final EditText inc1 = (EditText) findViewById(R.id.income);
        String inc = inc1.getText().toString();
        final EditText date1 = (EditText) findViewById(R.id.cal);
        String date = date1.getText().toString();
        final EditText budg1 = (EditText) findViewById(R.id.budg);
        String budg = budg1.getText().toString();

        adapter.setNotifyOnChange(true);
        bud.add(new Budget(inc,date,budg));
        adapter.notifyDataSetChanged();



    }


    private void populateListView(){
        adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.lv);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void registerClickCallback()
    {
        final int pos[] = new int[1];
        final ListView list = (ListView)findViewById((R.id.lv));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                //TODO display popup menu with edit and delete
                //list.removeViewAt(position);



            }
        });
        int posi = pos[0];
    }


    private  class MyListAdapter extends ArrayAdapter<Budget>
    {

        public MyListAdapter() {
            super(BudgetMake.this, R.layout.item_viewb, bud);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if(itemView == null)
            {
                itemView = getLayoutInflater().inflate(R.layout.item_viewb,parent, false);
            }

            Budget currentBudget = bud.get(position);

            TextView makeText = (TextView) itemView.findViewById(R.id.item_txtInc);
            makeText.setText(currentBudget.getInc());
            makeText = (TextView) itemView.findViewById(R.id.item_txtDate);
            makeText.setText(currentBudget.getDate1());
            makeText = (TextView) itemView.findViewById(R.id.item_txtBudg);
            makeText.setText(currentBudget.getBudg());


            return itemView;
            //return super.getView(position, convertView, parent);
        }




    }




}
