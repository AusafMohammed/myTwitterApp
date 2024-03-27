package com.example.mytwitter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity3 extends AppCompatActivity {
    myadapter ad;
    ArrayList<Tweets>arrayList;
RecyclerView recyclerView;
Button btn;
EditText txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList=new ArrayList<>();

        ad=new myadapter(this,arrayList);
        try {
            ParseQuery<ParseObject> myquery = ParseQuery.getQuery("tweetclass");
            myquery.whereContainedIn("username", ParseUser.getCurrentUser().getList("fanof"));
            myquery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    String name, t;
                    if (e == null && objects.size() > 0) {

                        for (ParseObject tweets : objects) {
                            name = tweets.get("username").toString();
                            t = tweets.get("tweet").toString();
                            arrayList.add(new Tweets(name, t));


                        }
                    }
                    recyclerView.setAdapter(ad);

                }

            });
        }catch (Exception e){

        }







    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity3.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.mydialogbox, viewGroup, false);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        btn=alertDialog.findViewById(R.id.button2);
        txt=alertDialog.findViewById(R.id.texttweet);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txt.getText().toString().equals("")){
                    FancyToast.makeText(MainActivity3.this,"Noting to tweet",FancyToast.LENGTH_LONG, FancyToast.CONFUSING,true).show();
                    return;
                }
                ParseObject obj=new ParseObject("tweetclass");
                obj.put("username",ParseUser.getCurrentUser().getUsername());
                obj.put("tweet",txt.getText().toString());
                ProgressDialog d=new ProgressDialog(MainActivity3.this);
                d.setMessage("Tweeting..Plzz wait");
                d.show();
                obj.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            FancyToast.makeText(MainActivity3.this,"Tweet sent",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();


                        }
                        d.dismiss();
                        alertDialog.dismiss();
                    }
                });


            }
        });

        return super.onOptionsItemSelected(item);
    }
}