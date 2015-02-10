package com.hinodesoftworks.kanagt.util;

import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hinodesoftworks.kanagt.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class KanaListViewAdapter extends RecyclerView.Adapter<KanaListViewAdapter.ViewHolder> {

    private Cursor mCursor;


    public KanaListViewAdapter(Cursor items){
        mCursor = items;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View parent = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_kana,
                viewGroup, false);
        return new ViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        mCursor.moveToPosition(i);

        //TODO make static constants in database manager for better readability
        viewHolder.kanaDisplay.setText(mCursor.getString(0));
        viewHolder.romaDisplay.setText(mCursor.getString(1));

        int correct = mCursor.getInt(2);
        int incorrect = mCursor.getInt(3);
        int total = correct + incorrect;
        int cPercent = 0;

        if (total != 0){
            cPercent = (correct * 100) / total;
        }

        viewHolder.dataView1.setText("Correct " + correct + " times (" + cPercent + "%)" );

        int prof = mCursor.getInt(4);
        switch (prof){
            case 0: //error state
                break;
            case 1: //unknown
                viewHolder.dataView2.setText("Unknown");
                viewHolder.dataView2.setTextColor(Color.RED);
                break;
            case 2: //known
                viewHolder.dataView2.setText("Known");
                viewHolder.dataView2.setTextColor(Color.parseColor("#B2B22B"));
                break;
            case 3: //well known
                viewHolder.dataView2.setText("Well Known");
                viewHolder.dataView2.setTextColor(Color.parseColor("#2983A8"));
                break;
            case 4: //mastered
                viewHolder.dataView2.setText("Mastered");
                viewHolder.dataView2.setTextColor(Color.parseColor("#2D9C2E"));
                break;
        }


        String imgName = mCursor.getString(5);

        if (imgName.matches("none")){
            viewHolder.diagramDisplay.setImageDrawable(null);
            viewHolder.diagramDisplay.setVisibility(View.GONE);
        }
        else{
            viewHolder.diagramDisplay.setVisibility(View.VISIBLE);
            String uriModel = "assets://diagrams/" + imgName + ".png";

            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .build();
            ImageLoader.getInstance().displayImage(uriModel, viewHolder.diagramDisplay, options);
        }
    }

    @Override
    public int getItemCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }

    //cursor methods

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView kanaDisplay;
        public TextView romaDisplay;
        public ImageView diagramDisplay;
        public TextView dataView1;
        public TextView dataView2;

        public ViewHolder(View itemView) {
            super(itemView);

            kanaDisplay = (TextView) itemView.findViewById(R.id.kana_card_display);
            romaDisplay = (TextView) itemView.findViewById(R.id.kana_card_roma);
            diagramDisplay = (ImageView) itemView.findViewById(R.id.kana_card_diagram);
            dataView1 = (TextView) itemView.findViewById(R.id.kana_card_info1_display);
            dataView2 = (TextView) itemView.findViewById(R.id.kana_card_info2_display);
        }
    }


}
