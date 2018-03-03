package com.example.igor.profile;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLinearLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import ru.underground.test42.InnerThings.Ingredient;
import ru.underground.test42.Lists.RecipesAdapter;

class IngredientAdapter extends ArrayAdapter<Ingredient> {
    private final Activity context;
    ArrayList<Boolean> selectedList;
    SharedPreferences preferences;

    public IngredientAdapter(Activity context, ArrayList<Ingredient> list) {
        super(context, R.layout.list_item_recipe_ingridient, list);
        this.context = context;
        selectedList = new ArrayList<Boolean>();
        preferences= PreferenceManager.getDefaultSharedPreferences(context);
    }

    public HashSet<String> getDislikes() {
        HashSet<String> dis=new HashSet<>();
        for (int i=0;i<getCount();i++){
            if(selectedList.get(i)){
                dis.add(getItem(i).getName());
            }
        }

        return dis;
    }


    class ViewHolder {
        ImageView ingredientDrawable;
        TextView ingredientTitle;
        TextView ingredientCount;
        View mainLayout;
        CheckBox checkBox;
        View foreground;
    }

    @NonNull
    public View getView(final int position, final View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        final View rowView = inflater.inflate(R.layout.list_item_recipe_ingridient, null, true);
        final ViewHolder holder = new ViewHolder();
        holder.ingredientDrawable = (ImageView) rowView.findViewById(R.id.ingrImage);
        holder.ingredientTitle = (TextView) rowView.findViewById(R.id.ingrTitle);
        holder.ingredientCount = (TextView) rowView.findViewById(R.id.countText);
        holder.mainLayout = rowView.findViewById(R.id.mainLayout);
        holder.checkBox= (CheckBox) rowView.findViewById(R.id.checkbox);
        holder.foreground=rowView.findViewById(R.id.foreground);
        rowView.setTag(holder);
        holder.ingredientTitle.setText(getItem(position).getName());
        holder.ingredientCount.setText("");

        holder.checkBox.setVisibility(View.VISIBLE);

        if (selectedList.get(position)) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }

        holder.foreground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedList.set(position,!selectedList.get(position));
                Log.d("aaa","a"+ Arrays.toString(getDislikes().toArray()));
                preferences.edit().putStringSet("dislikes",getDislikes()).apply();
                Log.d("click", String.valueOf(selectedList.get(position)));
                if (selectedList.get(position)) {
                    holder.checkBox.setChecked(true);
                } else {
                    holder.checkBox.setChecked(false);
                }

            }
        });

        return rowView;
    }

    public void add(@Nullable Ingredient object, boolean sel) {
        selectedList.add(sel);
        super.add(object);
    }
}