package ru.underground.test42.Tabs;

import android.support.v4.app.Fragment;

public class Tab {

    private String mTitle;
    private TabFragment mFragment;

    public Tab(String title, Fragment fragment) {
        mTitle = title;
        mFragment = (TabFragment) fragment;
    }

    public String getTitle() {
        return mTitle;
    }

    public TabFragment getFragment() {
        return mFragment;
    }

    public void setTitle(String title){
        mTitle=title;
    }
}
