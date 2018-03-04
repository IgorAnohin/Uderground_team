package ru.underground.test42.Tabs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.MimeTypeMap;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.scalified.fab.ActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import ru.underground.test42.InnerThings.Step;
import ru.underground.test42.R;

@SuppressLint("ValidFragment")
public class TabFragment extends Fragment {

    int minutes=0;
    int seconds=0;
    boolean paused=false;

    public ListView listView;
    View rootView;
    Step step;
    int count;
    int number;
    TextView timerText;
    Handler handler;
    ActionButton button;

    View.OnClickListener start,stop,clear;


    public TabFragment(final Step step, int number, int count) {
        super();
        this.step = step;
        this.count=count;
        this.number=number;
        handler=new Handler();
        start=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paused=false;
                recursiveTimer();
                if(button!=null) {
                    button.setImageResource(android.R.drawable.ic_media_pause);
                    button.setOnClickListener(stop);
                }
            }
        };
        stop=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paused=true;
                if(button!=null) {
                    button.setImageResource(android.R.drawable.ic_media_play);
                    button.setOnClickListener(start);
                }
            }
        };
        clear=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paused=true;
                minutes=step.getTime();
                seconds=0;
                if(timerText!=null){
                    if(minutes<10){
                        timerText.setText("0"+minutes+":00");
                    }else
                        timerText.setText(minutes+":00");
                }
                if(button!=null) {
                    button.setImageResource(android.R.drawable.ic_media_play);
                    button.setOnClickListener(start);
                }
            }
        };
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = getLayoutInflater(savedInstanceState).inflate(R.layout.fragment_cooking, null, false);
        View timerLayout=rootView.findViewById(R.id.timerLayout);
        if(step.getType()== Step.BASE){
            timerLayout.setVisibility(View.GONE);
        }else {
            minutes=step.getTime();
            timerText = (TextView) rootView.findViewById(R.id.timer);
            if(minutes<10){
                timerText.setText("0"+minutes+":00");
            }else
                timerText.setText(minutes+":00");
            button = (ActionButton)rootView.findViewById(R.id.action_button);
            button.setOnClickListener(start);
            ActionButton clearButton= (ActionButton) rootView.findViewById(R.id.clearButton);
            clearButton.setOnClickListener(clear);
        }
        TextView descText=(TextView)rootView.findViewById(R.id.descText);
        TextView stepText=(TextView)rootView.findViewById(R.id.stepNumber);
        ImageView stepImage=(ImageView)rootView.findViewById(R.id.imageView);
        stepText.setText("Шаг "+number+" из "+count);
        descText.setText(step.getDesc());
        if(step.loadedDrawable==null){
            stepImage.setImageResource(android.R.drawable.ic_dialog_alert);
        }else {
            stepImage.setImageBitmap(step.loadedDrawable);
        }
        return rootView;
    }

    public void recursiveTimer(){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if(seconds--==0){
                    minutes--;
                    seconds=59;
                }
                if(minutes<0){
                    //готово
                }
                if(timerText!=null){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(paused)
                                return;
                            String timetext;
                            if(minutes<10)
                               timetext=("0"+minutes);
                            else
                                timetext=("0"+minutes);
                            if(seconds<10){
                                timetext+=":0"+seconds;
                            }else {
                                timetext+=":"+seconds;
                            }
                            timerText.setText(timetext);
                        }
                    });
                }
                if(!paused)
                    recursiveTimer();
            }
        },1000);
    }
}
