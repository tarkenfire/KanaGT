package com.hinodesoftworks.kanagt;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.hinodesoftworks.kanagt.util.NavMenuListAdapter;
import com.hinodesoftworks.kanagt.util.NavMenuItem;
import com.hinodesoftworks.kanagt.util.NavMenuListAdapter.NavType;
import com.hinodesoftworks.kanagt.util.NavMenuListAdapter.NavLocation;

import java.util.ArrayList;

public class NavDrawerFragment extends Fragment implements View.OnClickListener,
            ListView.OnItemClickListener{

    private ListView navList;
    private OnNavMenuInteractionListener mListener;
    private NavMenuListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_nav_drawer, container, false);

        navList = (ListView)layout.findViewById(R.id.nav_menu_nav_list);

        ArrayList<NavMenuItem> items = new ArrayList<>();

        //hiragana menu
        items.add(new NavMenuItem(NavType.NAV_HEADER, NavLocation.LOC_NONE, "Hiragana"));
        items.add(new NavMenuItem(NavType.NAV_ITEM, NavLocation.LOC_HIRA_LIST, "Hiragana List"));
        items.add(new NavMenuItem(NavType.NAV_ITEM, NavLocation.LOC_HIRA_CHART, "Hiragana Chart"));
        items.add(new NavMenuItem(NavType.NAV_ITEM, NavLocation.LOC_HIRA_P_QUIZ, "Hiragana Practice Quiz"));
        items.add(new NavMenuItem(NavType.NAV_ITEM, NavLocation.LOC_HIRA_R_QUIZ, "Hiragana Ranking Quiz"));
        //katakana menu
        items.add(new NavMenuItem(NavType.NAV_HEADER, NavLocation.LOC_NONE, "Katakana"));
        items.add(new NavMenuItem(NavType.NAV_ITEM, NavLocation.LOC_KATA_LIST, "Katakana List"));
        items.add(new NavMenuItem(NavType.NAV_ITEM, NavLocation.LOC_KATA_CHART, "Katakana Chart"));
        items.add(new NavMenuItem(NavType.NAV_ITEM, NavLocation.LOC_KATA_P_QUIZ, "Katakana Practice Quiz"));
        items.add(new NavMenuItem(NavType.NAV_ITEM, NavLocation.LOC_KATA_R_QUIZ, "Katakana Ranking Quiz"));


        View header = inflater.inflate(R.layout.nav_menu_header, container, false);
        View footer = inflater.inflate(R.layout.nav_menu_footer, container, false);

        Button homeButton = (Button)header.findViewById(R.id.nav_menu_home_button);
        Button statsButton = (Button)footer.findViewById(R.id.nav_menu_stats_button);
        Button settingsButton = (Button)footer.findViewById(R.id.nav_menu_settings_button);

        homeButton.setOnClickListener(this);
        statsButton.setOnClickListener(this);
        settingsButton.setOnClickListener(this);

        navList.addHeaderView(header);
        navList.addFooterView(footer);


        mAdapter = new NavMenuListAdapter(getActivity(), 0, items);
        navList.setOnItemClickListener(this);
        navList.setAdapter(mAdapter);
        navList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnNavMenuInteractionListener) activity;
        } catch (ClassCastException e){
            throw new ClassCastException(activity.toString() +
                " must implement OnNavMenuInteractionListener");
        }
    }


    public interface OnNavMenuInteractionListener{
        public void onNavItemSelected(NavLocation location);
    }

    //implemented interface methods
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nav_menu_home_button:
                mListener.onNavItemSelected(NavLocation.LOC_HOME);
                break;
            case R.id.nav_menu_stats_button:
                mListener.onNavItemSelected(NavLocation.LOC_STATS);
                break;
            case R.id.nav_menu_settings_button:
                mListener.onNavItemSelected(NavLocation.LOC_SETTINGS);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        NavMenuItem selectedItem = mAdapter.getItem(position - 1);
        navList.setItemChecked(position, true);
        mListener.onNavItemSelected(selectedItem.getNavLocation());
    }
}
