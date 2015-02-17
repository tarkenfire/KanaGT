package com.hinodesoftworks.kanagt;


import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hinodesoftworks.kanagt.dialogs.QuizHistoryDialog;
import com.hinodesoftworks.kanagt.util.QuizResult;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class StatsFragment extends Fragment implements View.OnClickListener {

    private OnStatsFragmentLoadedListener mListener;


    TextView hiraTotal, hiraUnknown, hiraKnown, hiraWellKnown, hiraMastered;
    TextView kataTotal, kataUnknown, kataKnown, kataWellKnown, kataMastered;
    TextView avgScore, highScore, avgTime, lowTime;



    public StatsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View holder = inflater.inflate(R.layout.fragment_stats, container, false);

        hiraTotal = (TextView)holder.findViewById(R.id.main_kana_hiragana_total_label);
        hiraUnknown = (TextView)holder.findViewById(R.id.main_kana_hiragana_unknown_label);
        hiraKnown = (TextView)holder.findViewById(R.id.main_kana_hiragana_known_label);
        hiraWellKnown = (TextView)holder.findViewById(R.id.main_kana_hiragana_well_known_label);
        hiraMastered = (TextView)holder.findViewById(R.id.main_kana_hiragana_mastered_label);

        kataTotal = (TextView)holder.findViewById(R.id.main_kana_katakana_total_label);
        kataUnknown = (TextView)holder.findViewById(R.id.main_kana_katakana_unknown_label);
        kataKnown = (TextView)holder.findViewById(R.id.main_kana_katakana_known_label);
        kataWellKnown = (TextView)holder.findViewById(R.id.main_kana_katakana_well_known_label);
        kataMastered = (TextView)holder.findViewById(R.id.main_kana_katakana_mastered_label);

        avgScore = (TextView)holder.findViewById(R.id.main_quiz_average_score);
        highScore = (TextView)holder.findViewById(R.id.main_quiz_high_score);
        avgTime = (TextView)holder.findViewById(R.id.main_quiz_average_time);
        lowTime = (TextView)holder.findViewById(R.id.main_quiz_lowest_time);

        Button b = (Button)holder.findViewById(R.id.main_quiz_show_button);
        b.setOnClickListener(this);

        return holder;
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        mListener = (OnStatsFragmentLoadedListener) activity;
    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.onStatsFragmentLoaded(this);
    }

    public void updateStats(Cursor hiraStats, Cursor kataStats, Cursor quizStats){
        int hTotal = 0, hUnknown = 0, hKnown = 0, hWellKnown = 0, hMastered = 0,
            kTotal = 0, kUnknown = 0, kKnown = 0, kWellKnown = 0, kMastered = 0;

        //hiragana stats
        int prof = hiraStats.getInt(0);
        hTotal++;

        switch (prof){
            case 1: //unknown
                hUnknown++;
                break;
            case 2: //known
                hKnown++;
                break;
            case 3: //well known
                hWellKnown++;
                break;
            case 4: //mastered
                hMastered++;
                break;
        }

        while (hiraStats.moveToNext()){
            hTotal++;
            prof = hiraStats.getInt(0);

            switch (prof){
                case 1: //unknown
                    hUnknown++;
                    break;
                case 2: //known
                    hKnown++;
                    break;
                case 3: //well known
                    hWellKnown++;
                    break;
                case 4: //mastered
                    hMastered++;
                    break;
            }
        }
        
        hiraStats.close();

        //katakana stats
        prof = kataStats.getInt(0);
        kTotal++;

        switch (prof){
            case 1: //unknown
                kUnknown++;
                break;
            case 2: //known
                kKnown++;
                break;
            case 3: //well known
                kWellKnown++;
                break;
            case 4: //mastered
                kMastered++;
                break;
        }
        
        while (kataStats.moveToNext()){
            kTotal++;
            prof = kataStats.getInt(0);

            switch (prof){
                case 1: //unknown
                    kUnknown++;
                    break;
                case 2: //known
                    kKnown++;
                    break;
                case 3: //well known
                    kWellKnown++;
                    break;
                case 4: //mastered
                    kMastered++;
                    break;
            }
        }
        
        kataStats.close();

        //quiz stats

        if (quizStats.getCount() > 0) {
            int score[] = new int[quizStats.getCount()];
            long time[] = new long[quizStats.getCount()];
            score[quizStats.getPosition()] = quizStats.getInt(0);
            time[quizStats.getPosition()] = quizStats.getLong(2);

            while (quizStats.moveToNext()) {
                score[quizStats.getPosition()] = quizStats.getInt(0);
                time[quizStats.getPosition()] = quizStats.getLong(2);
            }

            int maxScore = getHighestScore(score);
            long lowTimeLong = getShortestTime(time);

            int averageScore = getAverageScore(score);
            long averageTime = getAverageTimeMillis(time);

            avgScore.setText("Average score: " + averageScore + "%");
            highScore.setText("Highest score: " + maxScore + "%");


            long seconds = TimeUnit.SECONDS.convert(lowTimeLong, TimeUnit.MILLISECONDS);
            String lowTStringFormat = "" + seconds;
            if (seconds < 10 ){
                lowTStringFormat = "0" + lowTStringFormat;
            }
            lowTime.setText("Shortest Quiz Time: " + TimeUnit.MINUTES.convert(lowTimeLong,
                                    TimeUnit.MILLISECONDS) + ":" + lowTStringFormat);

            seconds = TimeUnit.SECONDS.convert(averageTime, TimeUnit.MILLISECONDS);
            String avgTStringFormat = "" + seconds;
            if (seconds < 10){
                avgTStringFormat = "0" + avgTStringFormat;
            }

            avgTime.setText("Average Quiz Time: " + TimeUnit.MINUTES.convert(averageTime,
                                    TimeUnit.MILLISECONDS) + ":" + avgTStringFormat);




        }

        //display data
        hiraTotal.setText(hTotal + " Total");
        hiraUnknown.setText(hUnknown + " Unknown");
        hiraKnown.setText(hKnown + " Known");
        hiraWellKnown.setText(hWellKnown + " Well Known");
        hiraMastered.setText(hMastered + " Mastered");

        kataTotal.setText(kTotal + " Total");
        kataUnknown.setText(kUnknown + " Unknown");
        kataKnown.setText(kKnown + " Known");
        kataWellKnown.setText(kWellKnown + " Well Known");
        kataMastered.setText(kMastered + " Mastered");
    }

    public void showHistoryDialog(Cursor data){
        if (data.getCount() == 0){
            Toast.makeText(getActivity(), "No quiz history to display", Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayList<QuizResult> results = new ArrayList<>();

        results.add(new QuizResult(data.getString(1), data.getInt(0), data.getLong(3),
                        data.getLong(2)));

        while (data.moveToNext()){
            results.add(new QuizResult(data.getString(1), data.getInt(0), data.getLong(3),
                    data.getLong(2)));
        }

        QuizHistoryDialog dialog = new QuizHistoryDialog();
        dialog.setup(results);
        dialog.show(getFragmentManager(), "quizHistory");
    }

    //utility methods
    private int getAverageScore(int[] scores){
        int total = 0;
        int count = scores.length;

        for (int s : scores){
            total+= s;
        }


        return total / count;
    }

    private long getAverageTimeMillis(long[] times){
        long total = 0;
        int count = times.length;

        for (long t : times){
            total += t;
        }

        return total / count;
    }

    private int getHighestScore(int[] scores){
        int max = scores[0];

        for (int score : scores){
            if (score > max){
                max = score;
            }
        }
        return max;
    }

    private long getShortestTime(long[] times){
        long min = times[0];

        for (long time : times){
            if (time < min){
                min = time;
            }
        }
        return min;
    }

    //interface
    @Override
    public void onClick(View v) {
        mListener.onHistoryButtonPressed(this);
    }

    public interface OnStatsFragmentLoadedListener{
        public void onStatsFragmentLoaded(StatsFragment sender);
        public void onHistoryButtonPressed(StatsFragment sender);
    }

}
