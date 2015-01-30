package com.hinodesoftworks.kanagt.util;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hinodesoftworks.kanagt.R;

public class KanaChartViewAdapter extends RecyclerView.Adapter<KanaChartViewAdapter.ViewHolder> {

    private Cursor mCursor;

    public KanaChartViewAdapter(Cursor items){
        mCursor = items;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View parent = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chart_item_kana,
                viewGroup, false);
        return new ViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        mCursor.moveToPosition(i);

        //TODO make static constants in database manager for better readability
        viewHolder.kanaDisplay.setText(mCursor.getString(0));
        viewHolder.romaDisplay.setText(mCursor.getString(1));
    }


    @Override
    public int getItemCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView kanaDisplay;
        public TextView romaDisplay;

        public ViewHolder(View itemView) {
            super(itemView);

            kanaDisplay = (TextView) itemView.findViewById(R.id.kana_chart_display);
            romaDisplay = (TextView) itemView.findViewById(R.id.kana_chart_roma);
        }
    }


}
