package com.hinodesoftworks.kanagt.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hinodesoftworks.kanagt.R;

//view holder
public class KanaChartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView kanaDisplay;
    public TextView romaDisplay;
    private OnHolderClickedListener onHolderClickedListener;

    public KanaChartViewHolder(View itemView, OnHolderClickedListener listener) {
        super(itemView);

        kanaDisplay = (TextView) itemView.findViewById(R.id.kana_chart_display);
        romaDisplay = (TextView) itemView.findViewById(R.id.kana_chart_roma);

        onHolderClickedListener = listener;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        kanaDisplay = (TextView) v.findViewById(R.id.kana_chart_display);
        onHolderClickedListener.onItemClicked(kanaDisplay.getText().toString());
    }


    public interface OnHolderClickedListener{
        public void onItemClicked(String kana);
    }
}