package com.example.mahe.assignment1;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
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


public class LoanTrack extends Activity {
    int y, m, d;
    java.util.Calendar c= java.util.Calendar.getInstance();
    int ye = c.get(c.YEAR);
    int mo = c.get(c.MONTH);
    int da = c.get(c.DAY_OF_MONTH);
    Spinner spinner1;
    boolean check;
    private int request_code;
    ListView lv;


    public static float totexp = 0;
    SharedPreferences sh;
    SharedPreferences.Editor editor;
    ArrayAdapter<Loan> adapter;
    List<Loan> loan;

    InsertUserDBAdapter insdba;




    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);

        setContentView(R.layout.activity_loan);

        insdba = new InsertUserDBAdapter(getApplicationContext());

        insdba.open();


        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        getActionBar().setDisplayShowTitleEnabled(false);


        sh = getSharedPreferences(check_logged_in.t,MODE_PRIVATE);
        editor = sh.edit();

        loan = new ArrayList<Loan>();
        populateListView();

        Cursor c = insdba.getLoanAll();

        if(c.getCount()!=0)
        {

            TextView t = (TextView)findViewById(R.id.listtxt);
            t.setVisibility(View.VISIBLE);
            populateLoanList();
            populateListView();
        }


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


                new DatePickerDialog(LoanTrack.this, R.style.MyDialogTheme, date, ye, mo, da).show();
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
        final EditText amt1 = (EditText) findViewById(R.id.amt);
        String amount = amt1.getText().toString();
        final EditText date1 = (EditText) findViewById(R.id.cal);
        String date = date1.getText().toString();
        final EditText friend1 = (EditText) findViewById(R.id.friend);
        String friend = friend1.getText().toString();
        final EditText categ1 = (EditText) findViewById(R.id.friend);
        String categ = categ1.getText().toString();

        if(spin.equals("Choose lent/borrowed"))
            Toast.makeText(getApplicationContext(),"Please select Lent or Borrowed",Toast.LENGTH_LONG).show();
        else if(date.equals(""))
            date1.setError("Please choose a date");
        else if(amount.equals(""))
            amt1.setError("Please enter amount");
        else if (friend.equals(""))
            friend1.setError("Please enter a name");
        else if(categ.equals(""))
            categ1.setText("-");


        else
        {
            boolean st = insdba.addLoanVal(spin, date, Float.parseFloat(amount), categ, friend);
            if(st)
            {
                loan.clear();
                Toast.makeText(this, "Insertion successful", Toast.LENGTH_LONG).show();
                populateListView();
                populateLoanList();
                populateListView();
            }
            else
                Toast.makeText(this, "This entry already exists", Toast.LENGTH_LONG).show();


            TextView t = (TextView)findViewById(R.id.listtxt);
            t.setVisibility(View.VISIBLE);
            // totexp+= Integer.parseInt(amount);

        }



    }


    private void populateLoanList() {
        Cursor c = insdba.getLoanAll();
        while(c.moveToNext())
        {
            adapter.setNotifyOnChange(true);
            loan.add(new Loan(c.getString(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4)));
            adapter.notifyDataSetChanged();
        }




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

                //TODO display popup menu with delete
                //list.removeViewAt(position);



            }
        });
        int posi = pos[0];
    }


    private  class MyListAdapter extends ArrayAdapter<Loan>
    {

        public MyListAdapter() {
            super(LoanTrack.this, R.layout.item_viewl, loan);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if(itemView == null)
            {
                itemView = getLayoutInflater().inflate(R.layout.item_viewl,parent, false);
            }

            Loan currentLoan = loan.get(position);

            TextView makeText = (TextView) itemView.findViewById(R.id.item_txtSpin);
            makeText.setText(currentLoan.getSpin());
            makeText = (TextView) itemView.findViewById(R.id.item_txtDate);
            makeText.setText(currentLoan.getDate1());
            makeText = (TextView) itemView.findViewById(R.id.item_txtAmt);
            makeText.setText(currentLoan.getAmt());
            makeText = (TextView) itemView.findViewById(R.id.item_txtFriend);
            makeText.setText(currentLoan.getFriend());
            makeText = (TextView) itemView.findViewById(R.id.item_txtCateg);
            makeText.setText(currentLoan.getCateg());



            return itemView;
            //return super.getView(position, convertView, parent);
        }




    }









}


