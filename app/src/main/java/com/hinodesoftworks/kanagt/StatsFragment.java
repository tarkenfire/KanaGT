package com.hinodesoftworks.kanagt;


import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class StatsFragment extends Fragment {

    private OnStatsFragmentLoadedListener mListener;

    TextView hiraTotal, hiraUnknown, hiraKnown, hiraWellKnown, hiraMastered;
    TextView kataTotal, kataUnknown, kataKnown, kataWellKnown, kataMastered;

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
            
        //TODO: quiz stats

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

    public interface OnStatsFragmentLoadedListener{
        public void onStatsFragmentLoaded(StatsFragment sender);
    }

}
