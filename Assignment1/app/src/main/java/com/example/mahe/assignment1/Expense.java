package com.example.mahe.assignment1;

/**
 * Created by MAHE on 22-Sep-16.
 */
public class Expense {
    private String spin,date,amount,categ;




    public Expense(String spin, String date, String amount, String categ)
    {
        super();
        this.spin = spin;
        this.date = date;
        this.amount = amount;
        this.categ = categ;


    }

    public String getSpin()
    {
        return spin;
    }
    public String getDate1()
    {
        return date;
    }
    public String getAmt()
    {
        return amount;
    }
    public String getCateg()
    {
        return categ;
    }










}
