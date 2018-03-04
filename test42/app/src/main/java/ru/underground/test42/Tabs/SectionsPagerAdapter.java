package ru.underground.test42.Tabs;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.io.File;
import java.util.ArrayList;

import ru.underground.test42.InnerThings.Recipe;
import ru.underground.test42.InnerThings.Step;

public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

    public ArrayList<Tab> tabs;

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
        tabs = new ArrayList<>();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public TabFragment getItem(int position) {
        return tabs.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).getTitle();
    }

    public void addTab(Step step, Recipe recipe) {
        final TabFragment tabFragment=new TabFragment(step,tabs.size()+1,recipe.getSteps().size());
        tabs.add(new Tab("", tabFragment));

        notifyDataSetChanged();
        //viewPager.addOnPageChangeListener(ExplorerActivity.listener);
    }
    public void removeTab(final ViewPager viewPager, TabLayout tabLayout,int position) {
        tabLayout.removeTabAt(position);
        tabs.remove(position);
        notifyDataSetChanged();
        if(position==0)
            viewPager.setCurrentItem(0);
        else
            viewPager.setCurrentItem(position-1);
    }
}