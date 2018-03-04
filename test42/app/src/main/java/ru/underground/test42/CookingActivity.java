package ru.underground.test42;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ru.underground.test42.InnerThings.Recipe;
import ru.underground.test42.InnerThings.Step;
import ru.underground.test42.Tabs.SectionsPagerAdapter;

public class CookingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooking);
        ViewPager pager=(ViewPager)findViewById(R.id.viewPager);
        SectionsPagerAdapter sectionsPagerAdapter=new SectionsPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(sectionsPagerAdapter);
        Recipe recipe=MainActivity.staticRecipe;
        for (Step step: recipe.getSteps()){
            sectionsPagerAdapter.addTab(step);
        }
        //sectionsPagerAdapter.notifyDataSetChanged();
    }
}
