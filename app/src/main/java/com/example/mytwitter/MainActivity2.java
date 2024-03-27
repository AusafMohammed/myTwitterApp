package com.example.mytwitter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listView;
    ArrayAdapter arrayAdapter;
    ArrayList<String>arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        String name= ParseUser.getCurrentUser().getUsername();
        if(ParseUser.getCurrentUser().getList("fanof")==null){
            ParseUser.getCurrentUser().add("fanof",ParseUser.getCurrentUser().getUsername());
            ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    FancyToast.makeText(MainActivity2.this,"Welcome "+name+"!!",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                }
            });
        }

        listView=findViewById(R.id.listview);
        arrayList=new ArrayList<>();
        arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_checked,arrayList);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(this);
        ParseQuery<ParseUser>myquery=ParseUser.getQuery();
        myquery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        myquery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e==null&&objects.size()>0){
                    for(ParseUser user:objects){
                        arrayList.add(user.getUsername());
                    }
                }else{
                    FancyToast.makeText(MainActivity2.this,objects.size()+"",FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();
                }

                listView.setAdapter(arrayAdapter);
                if(ParseUser.getCurrentUser().getList("fanof")!=null) {
                    for (ParseUser users : objects) {
                        if (ParseUser.getCurrentUser().getList("fanof").contains(users.getUsername())) {
                            listView.setItemChecked(arrayList.indexOf(users.getUsername()), true);
                        }
                    }
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.Log_Out) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);

            // Set the message show for the Alert time
            builder.setMessage("Are you sure you want to logOut ?");

            // Set Alert Title
            builder.setTitle("Alert !");

            // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
            builder.setCancelable(false);

            // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
            builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                // When the user click yes button then app will close
                FancyToast.makeText(this, "GoodBye " + ParseUser.getCurrentUser().getUsername(), FancyToast.LENGTH_LONG, FancyToast.CONFUSING, true).show();


                ParseUser.getCurrentUser().logOut();

                finish();
            });

            // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
            builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                // If user click no then dialog box is canceled.
                dialog.cancel();
            });

            // Create the Alert dialog
            AlertDialog alertDialog = builder.create();
            // Show the Alert Dialog box
            alertDialog.show();
        }
        if(item.getItemId()==R.id.tweets){
            Intent i=new Intent(MainActivity2.this,MainActivity3.class);
            startActivity(i);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        CheckedTextView checkedTextView=(CheckedTextView) view;
        if(checkedTextView.isChecked()){
           // Toast.makeText(this, arrayList.get(i)+" is followed", Toast.LENGTH_SHORT).show();
            ParseUser.getCurrentUser().add("fanof",arrayList.get(i));

        }
        else{
            //ParseUser.getCurrentUser().getList("fanof").remove(arrayList.get(i));
            List curlis=ParseUser.getCurrentUser().getList("fanof");
            curlis.remove(arrayList.get(i));
            ParseUser.getCurrentUser().remove("fanof");
            ParseUser.getCurrentUser().put("fanof",curlis);
            //Toast.makeText(this, arrayList.get(i)+" is unfollowed", Toast.LENGTH_SHORT).show();
        }
        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Toast.makeText(MainActivity2.this, "Done", Toast.LENGTH_SHORT).show();

            }
        });
    }
}