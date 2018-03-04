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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import ru.underground.test42.InnerThings.Step;
import ru.underground.test42.R;

@SuppressLint("ValidFragment")
public class TabFragment extends Fragment {

    public ListView listView;
    View rootView;
    Step step;


    LinearLayout storageLayout, historyLayout, favoritesLayout, storageList, historyList;

    TextView storageText, historyText, favoritesText;


    public TabFragment(Step step) {
        super();
        this.step = step;
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = getLayoutInflater(savedInstanceState).inflate(R.layout.fragment_cooking, null, false);
        TextView descText=(TextView)rootView.findViewById(R.id.descText);
        descText.setText(step.getDesc());
        return rootView;
    }
}
