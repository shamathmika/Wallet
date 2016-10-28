package com.example.mahe.assignment1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by MAHE on 12-Sep-16.
 */
public class RegisterActivity extends Activity {

    Button reg;
    EditText username, email, pass;
    TextView wel, ent;
    ImageButton imgb;
    Animation animation,anim1;
    AnimationSet an;
    Drawable d;
    SharedPreferences sh ;
    SharedPreferences.Editor editor;
    InsertUserDBAdapter insdba;
    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.activity_reg);

        insdba = new InsertUserDBAdapter(getApplicationContext());
        insdba.open();

        reg = (Button)findViewById(R.id.reg_btn);
        username = (EditText)findViewById(R.id.name_et);
        email = (EditText)findViewById(R.id.email_et);
        pass = (EditText)findViewById(R.id.password_et);
        wel = (TextView)findViewById(R.id.welcome_tv);
        ent = (TextView)findViewById(R.id.enterdetail_tv);
        imgb = (ImageButton)findViewById(R.id.imageButton);



        d = (Drawable)getResources().getDrawable(R.drawable.exclamation);
        d.setBounds(0,0, 5, 5);




        an=new AnimationSet(true);
        an.setDuration(800);
        anim1 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);
        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_in_up);
        an.addAnimation(anim1);
        an.addAnimation(animation);
        username.startAnimation(an);
        pass.startAnimation(an);
        email.setAnimation(an);
        reg.setAnimation(an);
        imgb.startAnimation(an);
        wel.startAnimation(an);
        ent.startAnimation(an);




        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().matches("")){
                    username.setError("This field can't be empty");
                    //Toast.makeText(getApplicationContext(), "Please enter a username", Toast.LENGTH_LONG).show();
                }
                else if(email.getText().toString().matches("")){
                    email.setError("This field can't be empty");
                    //Toast.makeText(getApplicationContext(), "Please enter an email ID", Toast.LENGTH_LONG).show();
                }
                else if(pass.getText().toString().matches("")){
                    pass.setError("This field cant be empty");
                    //Toast.makeText(getApplicationContext(), "Please enter a password", Toast.LENGTH_LONG).show();
                }
                else
                {
                    if(attemptRegister()) {

                        String u = username.getText().toString();
                        String e = email.getText().toString();
                        String p = pass.getText().toString();

                        boolean st = insdba.addVal(u, e, p);
                        if(st)
                            Toast.makeText(getApplicationContext(), "Successfully registered", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getApplicationContext(), "This account has already been registered", Toast.LENGTH_LONG).show();

                        sh = getSharedPreferences(check_logged_in.t, Context.MODE_PRIVATE);
                        editor=sh.edit();
                        editor.putString("@strings/username",username.getText().toString());
                        editor.putString("@strings/pass",pass.getText().toString());
                        editor.putString("@strings/email",email.getText().toString());


                        Intent i = new Intent("com.example.mahe.assignment1.LoginActivity");
                        startActivity(i);
                        finish();
                    }

                }
            }
        });





    }
    
    private boolean attemptRegister(){
        boolean res = false;
        email.setError(null);
        if(!(isEmailValid(email.getText().toString())))
        {
            email.setError("Enter valid email address");  //Couldn't set drawable icon. Because of scaling. d.setBounds()
        }
        else
        {
            res=true;
        }
        if(!(isPasswordValid(pass.getText().toString())))
        {
            pass.setError("Password should be 6 or more characters");
            res=false;
        }

        return res;
    }

    private boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
    private boolean isPasswordValid(String pass) {
        boolean isValid=false;
        if(pass.length()>=6)
            isValid=true;
        return isValid;
    }




}
