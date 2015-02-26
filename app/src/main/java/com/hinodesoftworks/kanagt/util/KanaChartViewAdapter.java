package com.hinodesoftworks.kanagt.util;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hinodesoftworks.kanagt.R;

public class KanaChartViewAdapter extends RecyclerView.Adapter<KanaChartViewHolder>
            implements KanaChartViewHolder.OnHolderClickedListener{

    private final Cursor mCursor;
    private final OnChartItemClickListener mListener;

    public KanaChartViewAdapter(Cursor items, OnChartItemClickListener listener){
        mCursor = items;
        mListener = listener;
    }


    @Override
    public KanaChartViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View parent = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chart_item_kana,
                viewGroup, false);
        return new KanaChartViewHolder(parent, this);
    }

    @Override
    public void onBindViewHolder(KanaChartViewHolder viewHolder, int i) {
        mCursor.moveToPosition(i);

        //TODO make static constants in database manager for better readability
        viewHolder.kanaDisplay.setText(mCursor.getString(0));
        viewHolder.romaDisplay.setText(mCursor.getString(1));
    }


    @Override
    public int getItemCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }

    //callback from view holder
    @Override
    public void onItemClicked(String kana) {
        mListener.onItemClicked(kana);
    }

    //callback interface
    public interface OnChartItemClickListener{
        public void onItemClicked(String selectedCharacter);
    }
}
