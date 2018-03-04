package ru.underground.test42;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLinearLayout;

import java.util.ArrayList;

import ru.underground.test42.InnerThings.Demands;
import ru.underground.test42.InnerThings.Ingredient;
import ru.underground.test42.InnerThings.IngridientManager;
import ru.underground.test42.InnerThings.Recipe;
import ru.underground.test42.InnerThings.RecipeList;
import ru.underground.test42.Lists.IngredientAdapter;
import ru.underground.test42.Lists.RecipesAdapter;

/**
 * Created by komar on 04.03.2018.
 */

public class CheckIngrsActivity  extends AppCompatActivity {
    CheckIngrsActivity activity = this;
    ListView listView;
    Button button;
    Button checkAll;
    IngredientAdapter ingredientsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_ingrs_activity);
        // View mainLayout=findViewById(R.id.mainLayout);

        RecipeList recipeList = new RecipeList();
        //linearLayout=(LinearLayout)findViewById(R.id.listView);
        listView=(ListView)findViewById(R.id.checkIngrsList);
        ingredientsAdapter=new IngredientAdapter(this,new ArrayList<Ingredient>());
        ingredientsAdapter.type = false;
        listView.setAdapter(ingredientsAdapter);

        ArrayList<Demands.CurrentIngredient> ingredients = MainActivity.staticRecipe.getDemands().getIngredients();
        for(int i = 0; i<ingredients.size();i++)
        {
            ingredientsAdapter.add(ingredients.get(i).ingredient);
        }
        ingredientsAdapter.notifyDataSetChanged();

        button = (Button)findViewById(R.id.checkIngrsButton);
        button.setVisibility(View.INVISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((Button)findViewById(R.id.button)).setVisibility(View.VISIBLE);
                MainActivity.activity.startActivity(new Intent(MainActivity.activity, CookingActivity.class));
                activity.onBackPressed();
            }
        });

        checkAll = (Button)findViewById(R.id.checkAll);
        checkAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((Button)findViewById(R.id.button)).setVisibility(View.VISIBLE);
                for(int i = 0;i<ingredientsAdapter.getCount();i++)
                    ((CheckBox)listView.getChildAt(i).findViewById(R.id.checkbox)).setChecked(true);
                button.setVisibility(View.VISIBLE);
            }
        });
    }
}
