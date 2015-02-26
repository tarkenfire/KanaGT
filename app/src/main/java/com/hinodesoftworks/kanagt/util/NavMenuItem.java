package com.hinodesoftworks.kanagt.util;

import com.hinodesoftworks.kanagt.util.NavMenuListAdapter.NavType;
import com.hinodesoftworks.kanagt.util.NavMenuListAdapter.NavLocation;

public class NavMenuItem{
    private final NavType navType;
    private final NavLocation navLocation;
    private final String name;

    public NavMenuItem(NavType navType, NavLocation navLocation, String name){
        this.navType = navType; this.navLocation = navLocation; this.name = name;
    }

    public NavType getNavType() {
        return navType;
    }

    public NavLocation getNavLocation() {
        return navLocation;
    }

    public String getName() {
        return name;
    }
}
