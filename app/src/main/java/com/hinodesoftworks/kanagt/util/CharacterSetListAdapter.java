package com.hinodesoftworks.kanagt.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hinodesoftworks.kanagt.R;

import java.util.ArrayList;

public class CharacterSetListAdapter extends ArrayAdapter<KanaSet> {

    private final ArrayList<KanaSet> mItems;
    private final Context ctx;

    public CharacterSetListAdapter(Context context, int resource, ArrayList<KanaSet> items) {
        super(context, resource, items);

        this.ctx = context;
        this.mItems = items;

    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public KanaSet getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View holder = convertView;
        if (holder == null){
            LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            holder = inflater.inflate(R.layout.row_kana_set, parent, false);
        }

        KanaSet curSet = getItem(position);

        TextView title = (TextView)holder.findViewById(R.id.row_kana_set_title);
        TextView desc = (TextView)holder.findViewById(R.id.row_kana_set_desc);

        title.setText(curSet.title);
        desc.setText(curSet.desc);


        return holder;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View holder = convertView;
        if (holder == null){
            LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            holder = inflater.inflate(R.layout.row_kana_set, parent, false);
        }

        KanaSet curSet = getItem(position);

        TextView title = (TextView)holder.findViewById(R.id.row_kana_set_title);
        TextView desc = (TextView)holder.findViewById(R.id.row_kana_set_desc);

        title.setText(curSet.title);
        desc.setText(curSet.desc);


        return holder;
    }
}
