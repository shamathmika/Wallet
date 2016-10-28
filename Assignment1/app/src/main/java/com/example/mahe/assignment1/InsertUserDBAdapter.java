package com.example.mahe.assignment1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by MAHE on 08-Oct-16.
 */
public class InsertUserDBAdapter {
    //TODO when adding multiple accounts add email id column and reference it from User.
    private static final String DB_NAME="names.db";
    public static final String USER_TABLE_NAME = "User";
    private static final int DB_VERSION = 1;
    private static final String username = "username";
    private static final String email = "email";
    private static final String password = "password";
    private static final String USER_TABLE_CREATE = "create table " + USER_TABLE_NAME + " (" + username + " text, " + email + " text, " + password + " text, PRIMARY KEY(" + username + ", " + email + ", " + password +"));";

    public static final String EXPENSE_TABLE_NAME = "Expense";
    private static final String choose = "choose";
    private static final String date = "date";
    private static final String amount = "amount";
    private static final String category = "category";
    private static final String EXPENSE_TABLE_CREATE = "create table " + EXPENSE_TABLE_NAME + " (" + choose + " text, " + date + " text, " + amount + " float, " +  category + " text, PRIMARY KEY(" + choose + ", " + date + ", " + amount + ", " + category +"));";

    public static final String BUDGET_TABLE_NAME = "Budget";
    private static final String salary = "salary";
    private static final String BUDGET_TABLE_CREATE = "create table " + BUDGET_TABLE_NAME + " (" + salary + " float, " + date + " text, " + amount + " float, PRIMARY KEY(" + salary + ", " + date + ", " + amount +  "));";

    public static final String LOAN_TABLE_NAME = "Loan";
    private static final String name = "name";
    private static final String LOAN_TABLE_CREATE = "create table " + LOAN_TABLE_NAME + " (" + choose + " text, " + date + " text, " + amount + " float, " +  name + " text, " + category + " text, PRIMARY KEY(" + choose + ", " + date + ", " + amount + ", " + category + ", " + name + "));";

    public static final String CATEG_LIST_TABLE_NAME = "Category_List";
    private static final String CATEG_LIST_TABLE_CREATE = "create table " + CATEG_LIST_TABLE_NAME + " (" + category + ");";


    private SQLiteDatabase sqdb;
    private final Context context;
    private MyDBHelper helper;

    public InsertUserDBAdapter(Context context)
    {
        this.context = context;
        helper = new MyDBHelper(context,DB_NAME,null,DB_VERSION);
    }


    private static class MyDBHelper extends SQLiteOpenHelper
    {
        public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory, int version )
        {
            super(context,name,cursorFactory,version);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(USER_TABLE_CREATE);
            db.execSQL(EXPENSE_TABLE_CREATE);
            db.execSQL(BUDGET_TABLE_CREATE);
            db.execSQL(LOAN_TABLE_CREATE);
            db.execSQL(CATEG_LIST_TABLE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqdb, int oldVersion, int newVersion)
        {
            sqdb.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
            sqdb.execSQL("DROP TABLE IF EXISTS " + EXPENSE_TABLE_NAME);
            sqdb.execSQL("DROP TABLE IF EXISTS " + BUDGET_TABLE_NAME);
            sqdb.execSQL("DROP TABLE IF EXISTS " + LOAN_TABLE_NAME);
            sqdb.execSQL("DROP TABLE IF EXISTS " + CATEG_LIST_TABLE_NAME);
            onCreate(sqdb);
        }

    }


    public InsertUserDBAdapter open()
    {
        sqdb = helper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        sqdb.close();
    }

    public Cursor getAll()
    {
        return sqdb.query(USER_TABLE_NAME,null,null,null,null,null,null);
    }

    public boolean addVal(String username, String email, String password)
    {
        String sqlQuery = "insert into " + USER_TABLE_NAME + " values( '" + username + "', '" + email + "', '" + password +  "')";
        try
        {
            sqdb.execSQL(sqlQuery);
            return true;
        }catch(Exception e)
        {
            Log.e("error", e.toString());
            return false;
        }


    }

    public Cursor getExpenseAll()
    {
        return sqdb.query(EXPENSE_TABLE_NAME,null,null,null,null,null,null);
    }

    public boolean addExpenseVal(String choose, String date, float amount, String category)
    {
        String sqlQuery = "insert into " + EXPENSE_TABLE_NAME + " values( '" + choose + "', '" + date + "', " + amount + ", '" + category + "')";
        try
        {
            sqdb.execSQL(sqlQuery);
            return true;
        }catch(Exception e)
        {
            Log.e("error", e.toString());
            return false;
        }


    }

    public Cursor getBudgetAll()
    {
        return sqdb.query(BUDGET_TABLE_NAME,null,null,null,null,null,null);
    }

    public boolean addBudgetVal(float salary, String date, float amount)
    {
        String sqlQuery = "insert into " + BUDGET_TABLE_NAME + " values( '" + salary + "', '" + date + "', " + amount + ")";
        try
        {
            sqdb.execSQL(sqlQuery);
            return true;
        }catch(Exception e)
        {
            Log.e("error", e.toString());
            return false;
        }


    }

    public Cursor getLoanAll()
    {
        return sqdb.query(LOAN_TABLE_NAME,null,null,null,null,null,null);
    }

    public boolean addLoanVal(String choose, String date, float amount, String category, String name)
    {
        String sqlQuery = "insert into " + LOAN_TABLE_NAME + " values( '" + choose + "', '" + date + "', " + amount + ", '" + name + "', '" + category + "');";
        try
        {
            sqdb.execSQL(sqlQuery);
            return true;
        }catch(Exception e)
        {
            Log.e("error", e.toString());
            return false;
        }


    }

    public Cursor getCategoryAll()
    {
        return sqdb.query(CATEG_LIST_TABLE_NAME,null,null,null,null,null,null);
    }

    public boolean addCategoryVal(String category)
    {
        String sqlQuery = "insert into " + CATEG_LIST_TABLE_NAME + " values( '" + category + "')";
        try
        {
            sqdb.execSQL(sqlQuery);
            return true;
        }catch(Exception e)
        {
            Log.e("error", e.toString());
            return false;
        }


    }


}
