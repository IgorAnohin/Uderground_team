package ru.underground.test42;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.scalified.fab.ActionButton;

import ru.underground.test42.InnerThings.History;
import ru.underground.test42.InnerThings.Recipe;
import ru.underground.test42.InnerThings.Step;
import ru.underground.test42.Tabs.SectionsPagerAdapter;

public class CookingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooking);
        final ViewPager pager=(ViewPager)findViewById(R.id.viewPager);
        final SectionsPagerAdapter sectionsPagerAdapter=new SectionsPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(sectionsPagerAdapter);
        final Recipe recipe=MainActivity.staticRecipe;
        for (Step step: recipe.getSteps()){
            sectionsPagerAdapter.addTab(step,recipe);
        }
        final ActionButton prev=(ActionButton)findViewById(R.id.prev);
        final ActionButton next=(ActionButton)findViewById(R.id.next);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(pager.getCurrentItem()==0){
                    prev.setVisibility(View.GONE);
                }else {
                    prev.setVisibility(View.VISIBLE);
                }
                if(pager.getCurrentItem()==sectionsPagerAdapter.tabs.size()-1){
                    next.setButtonColor(Color.rgb(85,255,85));
                    next.setImageResource(R.drawable.tick);
                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder=new AlertDialog.Builder(CookingActivity.this);
                            builder.setTitle("Завершить?")
                                    .setMessage("Вы собираетесь завершить приготовление, вы уверены?")
                                    .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            History.addRecipe(recipe.getId());
                                            Toast.makeText(getApplicationContext(),"Готово!",Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    })
                                    .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    })
                                    .show();

                        }
                    });
                }else {
                    next.setButtonColor(Color.rgb(255,85,85));
                    next.setImageResource(R.drawable.next);
                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pager.setCurrentItem(pager.getCurrentItem()+1,true);
                        }
                    });
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(pager.getCurrentItem()+1,true);
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(pager.getCurrentItem()-1,true);
            }
        });
        //sectionsPagerAdapter.notifyDataSetChanged();
    }
}
