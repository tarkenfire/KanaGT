package com.hinodesoftworks.kanagt.util;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hinodesoftworks.kanagt.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

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

        String uriModel = "assets://diagrams/" + mCursor.getString(5) + ".png";

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.EXACTLY)
                .build();
        ImageLoader.getInstance().displayImage(uriModel, viewHolder.diagramDisplay, options);




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

        public ViewHolder(View itemView) {
            super(itemView);

            kanaDisplay = (TextView) itemView.findViewById(R.id.kana_card_display);
            romaDisplay = (TextView) itemView.findViewById(R.id.kana_card_roma);
            diagramDisplay = (ImageView) itemView.findViewById(R.id.kana_card_diagram);
        }
    }
}
