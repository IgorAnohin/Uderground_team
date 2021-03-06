package ru.underground.test42.Lists;



import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLinearLayout;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import ru.underground.test42.InnerThings.Ingredient;
import ru.underground.test42.Lists.RecipesAdapter;
import ru.underground.test42.R;

public class IngredientAdapter extends ArrayAdapter<Ingredient> {
    IngredientAdapter adapter = this;
    private final Activity context;
    SharedPreferences preferences;
    public boolean type = true;

    public IngredientAdapter(Activity context, ArrayList<Ingredient> list) {
        super(context, R.layout.list_item_recipe_ingridient, list);
        this.context = context;
        preferences= PreferenceManager.getDefaultSharedPreferences(context);
    }

    public HashSet<String> getDislikes() {
        HashSet<String> dis=new HashSet<>();
        for (int i=0;i<getCount();i++){
            if(getItem(i).isUnLikeable){
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
        final View rowView = inflater.inflate(R.layout.ingr_selectable, null, true);
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

        final Ingredient ingredient=getItem(position);

        if(type)
        {
            if (ingredient.isUnLikeable) {
                holder.checkBox.setChecked(true);
            } else {
                holder.checkBox.setChecked(false);
            }
        }
        else
            holder.checkBox.setChecked(false);

        holder.foreground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type)
                {
                    ingredient.isUnLikeable=!ingredient.isUnLikeable;
                    if (ingredient.isUnLikeable) {
                        holder.checkBox.setChecked(true);
                    } else {
                        holder.checkBox.setChecked(false);
                    }
                }
                else
                {
                    ingredient.isChecked=!ingredient.isChecked;
                    if (ingredient.isChecked) {
                        holder.checkBox.setChecked(true);
                    } else {
                        holder.checkBox.setChecked(false);
                    }
                }
                boolean b = true;
                for(int i = 0;i<adapter.getCount();i++)
                {
                    if(!adapter.getItem(i).isChecked)
                        b = false;
                }
                if(b)
                {
                    ((Button)context.findViewById(R.id.checkIngrsButton)).setVisibility(View.VISIBLE);
                }
            }
        });
        if(ingredient.loadedDrawable==null){
            holder.ingredientDrawable.setImageResource(android.R.drawable.ic_dialog_alert);
        }else {
            holder.ingredientDrawable.setImageBitmap(ingredient.loadedDrawable);
        }
        //  holder.sizeBa
        return rowView;
    }
}