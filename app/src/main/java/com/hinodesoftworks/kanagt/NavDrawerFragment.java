package com.hinodesoftworks.kanagt;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.hinodesoftworks.kanagt.util.NavMenuListAdapter;
import com.hinodesoftworks.kanagt.util.NavMenuItem;
import com.hinodesoftworks.kanagt.util.NavMenuListAdapter.NavType;
import com.hinodesoftworks.kanagt.util.NavMenuListAdapter.NavLocation;

import java.util.ArrayList;

public class NavDrawerFragment extends Fragment implements View.OnClickListener {

    private ListView navList;

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

        navList.addHeaderView(header);
        navList.addFooterView(footer);


        NavMenuListAdapter adapter = new NavMenuListAdapter(getActivity(), 0, items);
        navList.setAdapter(adapter);

        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }


    public interface OnNavMenuInteractionListener{
        public void onNavItemSelected(NavLocation location);
    }

    //implemented interface methods
    @Override
    public void onClick(View v) {

    }
}
