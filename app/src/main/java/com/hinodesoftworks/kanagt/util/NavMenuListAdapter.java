package com.hinodesoftworks.kanagt.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.hinodesoftworks.kanagt.R;
import com.hinodesoftworks.kanagt.util.NavMenuItem;

public class NavMenuListAdapter extends ArrayAdapter<NavMenuItem> {
    //enums for items
    public enum NavType{NAV_HEADER, NAV_ITEM}
    public enum NavLocation{LOC_NONE, LOC_HOME, LOC_HIRA_LIST, LOC_HIRA_CHART, LOC_HIRA_P_QUIZ,
                    LOC_HIRA_R_QUIZ, LOC_KATA_LIST, LOC_KATA_CHART, LOC_KATA_P_QUIZ,
                    LOC_KATA_R_QUIZ, LOC_STATS, LOC_SETTINGS}

    //private ref vars
    private Context ctx;
    private ArrayList<NavMenuItem> items;


    public NavMenuListAdapter(Context context, int resource, ArrayList<NavMenuItem> objects) {
        super(context, resource, objects);

        ctx = context;
        items = objects;
    }

    @Override
    public NavMenuItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NavMenuItem item = getItem(position);

        //since I won't be reusing any views, can get away with sloppy view handling without
        //convertView

        LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View holder = null;
        switch(item.getNavType()){
            case NAV_HEADER:
                holder = inflater.inflate(R.layout.nav_row_header, parent, false);
                TextView hText =(TextView) holder.findViewById(R.id.nav_row_header_text);
                hText.setText(item.getName());
                break;
            case NAV_ITEM:
                holder = inflater.inflate(R.layout.nav_row_item, parent, false);
                TextView iText =(TextView) holder.findViewById(R.id.nav_row_item_text);
                iText.setText(item.getName());
                break;

        }


        return holder;
    }

}
