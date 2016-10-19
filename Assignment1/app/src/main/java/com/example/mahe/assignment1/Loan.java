package com.example.mahe.assignment1;

/**
 * Created by MAHE on 22-Sep-16.
 */
public class Loan {
    private String spin,date,amount,friend,categ;




    public Loan(String spin, String date, String amount, String friend, String categ)
    {
        super();
        this.spin = spin;
        this.date = date;
        this.amount = amount;
        this.friend = friend;
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
    public String getFriend()
    {
        return friend;
    }
    public String getCateg()
    {
        return categ;
    }










}
