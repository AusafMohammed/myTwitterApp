package com.example.mytwitter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity {
    EditText email,name,pass1,pass2;
    Button btn;
    TextView t;
    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email=findViewById(R.id.myemail);
        name=findViewById(R.id.myusername);
        pass1=findViewById(R.id.password);
        pass2=findViewById(R.id.repassword);
        btn=findViewById(R.id.btn);
        t=findViewById(R.id.textView);
        if(ParseUser.getCurrentUser()!=null){
            Intent i=new Intent(MainActivity.this,MainActivity2.class);
            startActivity(i);

        }




    }
    public void btnistapped(View v){
        if(flag==0){
            ProgressDialog dialog=new ProgressDialog(MainActivity.this);
            dialog.setMessage("Signing in...Plzz wait");
            if(email.getText().toString().equals("")||
            name.getText().toString().equals("")||
            pass1.getText().toString().equals("")||
            pass2.getText().toString().equals("")){
                FancyToast.makeText(this,"Please fill in the details",FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();
                return;
            }
            if(!pass1.getText().toString().equals(pass2.getText().toString())){
                pass1.setError("Passwords not matching");
                pass2.setError("Passwords not matching");
                return;
            }
            ParseUser user=new ParseUser();
            String emailstr,namestr,strpass1,strpass2;
            emailstr=email.getText().toString();
            namestr=name.getText().toString();
            strpass1=pass1.getText().toString();
            strpass2=pass2.getText().toString();
            user.setUsername(namestr);
            user.setEmail(emailstr);
            user.setPassword(strpass1);
            dialog.show();
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if(e==null){
                        Intent i=new Intent(MainActivity.this,MainActivity2.class);
                        startActivity(i);


                    }else {
                        FancyToast.makeText(MainActivity.this,"Error...Something went wrong",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                    }
                    dialog.dismiss();
                }
            });


        }
        else {
            if(name.getText().toString().equals("")||
            pass1.getText().toString().equals("")){
                FancyToast.makeText(this,"Please fill in the details",FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();
            }
            String strname,password;
            strname=name.getText().toString();
            password=pass1.getText().toString();
            ProgressDialog d=new ProgressDialog(this);
            d.setMessage("Logging in...Plzz wait");
            d.show();
            ParseUser.logInInBackground(strname, password, new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if(user!=null&&e==null){
                    Intent i=new Intent(MainActivity.this,MainActivity2.class);
                    startActivity(i);
                    }
                    else{
                        FancyToast.makeText(MainActivity.this,"Error...Plzz check your details",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();


                    }
                    d.dismiss();

                }
            });

        }
    }
    public void txtistapped(View v){
        if(flag==0){
            flag=1;
            email.setAlpha(0f);
            pass2.setAlpha(0f);
            name.setText("");
            pass1.setText("");
            btn.setText("LOG IN");
            t.setText("CLICK HERE TO SIGN UP");

        }else{
            flag=0;
            email.setAlpha(1f);
            name.setText("");
            pass1.setText("");
            btn.setText("SIGN IN");
            pass2.setAlpha(1f);
            t.setText("CLICK HERE TO LOG IN");
        }
    }
}