package com.hinodesoftworks.kanagt.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hinodesoftworks.kanagt.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class QuizHistoryListAdapter extends ArrayAdapter<QuizResult> {

    private final ArrayList<QuizResult> mItems;
    private final Context ctx;

    public QuizHistoryListAdapter(Context context, int resource, ArrayList<QuizResult> objects) {
        super(context, resource, objects);

        this.mItems = objects;
        this.ctx = context;
    }

    @Override
    public int getCount(){
        return mItems.size();
    }

    @Override
    public QuizResult getItem(int position){
        return mItems.get(position);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View holder = convertView;

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            holder = inflater.inflate(R.layout.row_quiz_result, parent, false);
        }

        QuizResult curResult = getItem(position);

        long timeTaken = curResult.timeTake;
        long minutes = TimeUnit.MINUTES.convert(timeTaken, TimeUnit.MILLISECONDS);
        long seconds = TimeUnit.SECONDS.convert(timeTaken, TimeUnit.MILLISECONDS);

        String secondsString = "" + seconds;
        if (seconds < 10){
            secondsString = "0" + secondsString;
        }

        String type = curResult.type;

        TextView title = (TextView)holder.findViewById(R.id.row_quiz_result_title);
        title.setText(type +  " " + minutes + ":" + secondsString);

        TextView date = (TextView)holder.findViewById(R.id.row_quiz_result_date);
        long dateLong = curResult.date;

        SimpleDateFormat sdFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm", Locale.getDefault());
        String dateString = sdFormat.format(new Date(dateLong));

        date.setText("Date Taken: " + dateString);

        TextView score = (TextView)holder.findViewById(R.id.row_quiz_result_score);
        int scoreInt = curResult.score;
        score.setText("Score: " + scoreInt + "%");

        if (scoreInt >= 70){
           score.setTextColor(Color.parseColor("#40BA46"));
        }
        else{
            score.setTextColor(Color.RED);
        }
        return holder;
    }
}
