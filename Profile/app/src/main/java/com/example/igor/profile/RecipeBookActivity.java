package com.example.igor.profile;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.util.ArrayList;
import java.util.Arrays;

import ru.underground.test42.InnerThings.Demands;
import ru.underground.test42.InnerThings.MainSystem;
import ru.underground.test42.InnerThings.Recipe;
import ru.underground.test42.InnerThings.RecipeList;
import ru.underground.test42.Lists.RecipeItem;
import ru.underground.test42.Lists.RecipesAdapter;


public class RecipeBookActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    ListView listView;
    RecipesAdapter recipesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_book_activity);
        // View mainLayout=findViewById(R.id.mainLayout);
        MainSystem.Initialize();

        RecipeList recipeList = new RecipeList();
        recipeList.createTest();
        //linearLayout=(LinearLayout)findViewById(R.id.listView);
        listView=(ListView)findViewById(R.id.recipeList);
        recipesAdapter=new RecipesAdapter(this,new ArrayList<Recipe>());
        listView.setAdapter(recipesAdapter);

        recipesAdapter.add(recipeList.getRecipes().get(0));
        recipesAdapter.add(recipeList.getRecipes().get(1));
        recipesAdapter.notifyDataSetChanged();
    }

    class ViewHolder {
        CardView mainView;
        ImageView recipeDrawable;
        ImageView mask;
        TextView recipeTitle;
        TextView shortDescriptionText;
        ExpandableLinearLayout layout;
    }

    void addRecipe(Recipe recipe) {

        LayoutInflater inflater = getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_item_reciepe, null, true);

        String title = recipe.getName();

        final ViewHolder holder = new ViewHolder();
        holder.recipeDrawable = (ImageView) rowView.findViewById(R.id.food_image);
        holder.recipeTitle = (TextView) rowView.findViewById(R.id.recipeTitle);
        holder.shortDescriptionText = (TextView) rowView.findViewById(R.id.shortDescriptionText);
        holder.mask = (ImageView)rowView.findViewById(R.id.mask);
        holder.mainView = (CardView) rowView.findViewById(R.id.mainLayout);
        holder.layout=(ExpandableLinearLayout) rowView.findViewById(R.id.expandableLayout);
        rowView.setTag(holder);

        holder.mainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("logger", "click");
                int startSize=holder.mainView.getHeight();
              //  ViewGroup.LayoutParams params=holder.recipeDrawable.getLayoutParams();
             //   params.height*=2;
               // holder.recipeDrawable.setLayoutParams(params);
                boolean expanded=holder.layout.isExpanded();
                Animation animation;
                Animation expandCardAnimation=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.card_expand);
                //holder.mainView.startAnimation(expandCardAnimation);
                if(expanded) {
                    animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.recipe_title_expand);
                    holder.layout.collapse();
                    holder.recipeTitle.setMaxLines(1);
                } else {
                    animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.recipe_title_collapse);
                    holder.layout.expand();
                    holder.recipeTitle.setMaxLines(99);
                }

                holder.shortDescriptionText.startAnimation(animation);


            }
        });

        holder.recipeTitle.setText(title);
        Demands.CurrentIngredient[] ingredients = recipe.getDemands().getIngredients().toArray(new Demands.CurrentIngredient[]{});
        StringBuilder stringBuilder = new StringBuilder();
        for (Demands.CurrentIngredient ingredient : ingredients) {
            stringBuilder.append(ingredient.ingredient.getName());
            stringBuilder.append(", ");
        }
        String text = stringBuilder.toString();
        if (text.endsWith(" "))
            text = text.substring(0, text.length() - 1);
        if (text.endsWith(","))
            text = text.substring(0, text.length() - 1);
        holder.shortDescriptionText.setText(text);
        //  holder.sizeBar=(ImageView) rowView.findViewById(R.id.sizeBar); jk
        linearLayout.addView(rowView);
    }
}
