package com.example.mahe.assignment1;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;




public class MainActivity extends Activity {
    int y, m, d;
    java.util.Calendar c= java.util.Calendar.getInstance();
    int ye = c.get(c.YEAR);
    int mo = c.get(c.MONTH);
    int da = c.get(c.DAY_OF_MONTH);
    Spinner spinner1;
    boolean check;
    private int request_code;
    ListView lv;
    ArrayAdapter<Expense> adapter;
    List<Expense> exp;


    SharedPreferences sh;
    SharedPreferences.Editor editor;


    public static float totexp = 0;




    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);


        exp = new ArrayList<Expense>();
        populateListView();


        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        getActionBar().setDisplayShowTitleEnabled(false);

        sh = getSharedPreferences(check_logged_in.t,MODE_PRIVATE);
        editor = sh.edit();





        registerClickCallback();



        final EditText e = (EditText) findViewById(R.id.cal);
        //Date picker dialog to open when edit text is clicked
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                y = year;
                m = monthOfYear;
                d = dayOfMonth;
                if (y > ye || (y <= ye && m > mo) || (y <= ye && m <= mo && d > da)) {
                    Toast.makeText(getBaseContext(), "Oops. That's in the future!", Toast.LENGTH_LONG).show();
                } else {
                    e.setText(d + "-" + getMonth(m) + "-" + y);
                }
            }

        };


        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new DatePickerDialog(MainActivity.this, R.style.MyDialogTheme, date, ye, mo, da).show();
            }

        });


        spinner1 = (Spinner) findViewById(R.id.chooseSpinner);
        //spinner1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
        spinner1 = (Spinner) findViewById(R.id.chooseSpinner);
        String spin = spinner1.getSelectedItem().toString();
        final EditText date1 = (EditText) findViewById(R.id.cal);
        String date = date1.getText().toString();
        final EditText amount1 = (EditText) findViewById(R.id.amt);
        String amount = amount1.getText().toString();
        final EditText categ1 = (EditText) findViewById(R.id.categ);
        String categ = categ1.getText().toString();

        if(spin.equals("Choose spent/earned"))
            Toast.makeText(getApplicationContext(),"Please choose \"Spent\" or \"Earned\"",Toast.LENGTH_LONG).show();
        else if(date.equals(""))
            date1.setError("Please choose a date");
        else if(amount.equals(""))
            amount1.setError("Please enter amount");
        else if(categ.equals(""))
            categ1.setText("-");

        else
        {
            TextView t = (TextView)findViewById(R.id.listtxt);
            t.setVisibility(View.VISIBLE);
           // totexp+= Integer.parseInt(amount);
            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            populateExpenseList();
            populateListView();
        }



    }


    private void populateExpenseList() {
        spinner1 = (Spinner) findViewById(R.id.chooseSpinner);
        String spin = spinner1.getSelectedItem().toString();
        final EditText date1 = (EditText) findViewById(R.id.cal);
        String date = date1.getText().toString();
        final EditText amount1 = (EditText) findViewById(R.id.amt);
        String amount = amount1.getText().toString();
        final EditText categ1 = (EditText) findViewById(R.id.categ);
        String categ = categ1.getText().toString();
        adapter.setNotifyOnChange(true);
        exp.add(new Expense(spin,date,amount,categ));
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


    private  class MyListAdapter extends ArrayAdapter<Expense>
    {

        public MyListAdapter() {
            super(MainActivity.this, R.layout.item_view, exp);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if(itemView == null)
            {
                itemView = getLayoutInflater().inflate(R.layout.item_view,parent, false);
            }

            Expense currentExpense = exp.get(position);

            TextView makeText = (TextView) itemView.findViewById(R.id.item_txtSpin);
            makeText.setText(currentExpense.getSpin());
            makeText = (TextView) itemView.findViewById(R.id.item_txtDate);
            makeText.setText(currentExpense.getDate1());
            makeText = (TextView) itemView.findViewById(R.id.item_txtAmount);
            makeText.setText(currentExpense.getAmt());
            makeText = (TextView) itemView.findViewById(R.id.item_txtCateg);
            makeText.setText(currentExpense.getCateg());

            return itemView;
            //return super.getView(position, convertView, parent);
        }




    }























}


