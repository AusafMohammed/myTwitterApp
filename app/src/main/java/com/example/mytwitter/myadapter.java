package com.example.mytwitter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.ParseUser;

import java.util.ArrayList;

public class myadapter extends RecyclerView.Adapter<myadapter.ViewHolder> {
    Context context;
    ArrayList<Tweets>arrayList=new ArrayList<>();
    myadapter(Context context, ArrayList<Tweets > arralist){
        this.context=context;

        this.arrayList=arralist;

    }
    @NonNull
    @Override
    public myadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.my_layout,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myadapter.ViewHolder holder, int position) {
        if(arrayList.get(position).getName().equals(ParseUser.getCurrentUser().getUsername())){
            holder.user.setText("YOU");
        }else {
            holder.user.setText(arrayList.get(position).getName());
        }
        holder.tweet.setText(arrayList.get(position).getTweet());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView user,tweet;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            user=itemView.findViewById(R.id.senderoftweet);
            tweet=itemView.findViewById(R.id.tweetsent);
        }
    }
}
