package ru.underground.test42.Lists;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLinearLayout;

import java.util.ArrayList;

import ru.underground.test42.CookingActivity;
import ru.underground.test42.InnerThings.Demands;
import ru.underground.test42.InnerThings.Recipe;
import ru.underground.test42.InnerThings.Tool;
import ru.underground.test42.R;


public class RecipesAdapter extends ArrayAdapter<Recipe> {
    private Activity context;

    public RecipesAdapter(Activity context, ArrayList<Recipe> list) {
        super(context, R.layout.list_item_reciepe, list);
        this.context = context;
    }


    class ViewHolder {
        CardView mainView;
        ImageView recipeDrawable;
        ImageView mask;
        TextView recipeTitle;
        TextView shortDescriptionText;
        ExpandableLinearLayout layout;

        TextView proteinCount;
        TextView fatsCount;
        TextView carbonsCount;
        TextView caloriesCount;

        LinearLayout listView;
        LinearLayout toolsList;

        Button startButton;

    }

        class ViewHolder2 {
            ImageView ingredientDrawable;
            TextView ingredientTitle;
            TextView ingredientCount;
        }

        public View addIngr(Demands.CurrentIngredient ingredient) {

            LayoutInflater inflater = context.getLayoutInflater();
            final View rowView = inflater.inflate(R.layout.list_item_recipe_ingridient, null, true);

            final ViewHolder2 holder = new ViewHolder2();
            holder.ingredientDrawable=(ImageView)rowView.findViewById(R.id.ingrImage);
            holder.ingredientTitle=(TextView)rowView.findViewById(R.id.ingrTitle);
            holder.ingredientCount=(TextView)rowView.findViewById(R.id.countText);

            rowView.setTag(holder);

            holder.ingredientTitle.setText(ingredient.ingredient.getName());
            holder.ingredientCount.setText(ingredient.size+" "+ingredient.ingredient.getUnitType());

            return rowView;
        }

    public View addTool(Tool tool) {

        LayoutInflater inflater = context.getLayoutInflater();
        final View rowView = inflater.inflate(R.layout.list_item_recipe_ingridient, null, true);

        final ViewHolder2 holder = new ViewHolder2();
        holder.ingredientDrawable=(ImageView)rowView.findViewById(R.id.ingrImage);
        holder.ingredientTitle=(TextView)rowView.findViewById(R.id.ingrTitle);
        holder.ingredientCount=(TextView)rowView.findViewById(R.id.countText);

        rowView.setTag(holder);

        holder.ingredientTitle.setText(tool.getName());
        holder.ingredientCount.setText("");

        return rowView;
    }

    @NonNull
    public View getView(final int position, final View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        final View rowView = inflater.inflate(R.layout.list_item_reciepe, null, true);

        Recipe recipe=getItem(position);
        final String title = recipe.getName();

        final ViewHolder holder = new ViewHolder();
        holder.recipeDrawable = (ImageView) rowView.findViewById(R.id.food_image);
        holder.recipeTitle = (TextView) rowView.findViewById(R.id.recipeTitle);
        holder.shortDescriptionText = (TextView) rowView.findViewById(R.id.shortDescriptionText);
        holder.mask = (ImageView) rowView.findViewById(R.id.mask);
        holder.mainView = (CardView) rowView.findViewById(R.id.mainLayout);
        holder.layout = (ExpandableLinearLayout) rowView.findViewById(R.id.expandableLayout);
        holder.startButton=(Button)rowView.findViewById(R.id.startButton);
        holder.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, CookingActivity.class));
            }
        });

        holder.listView=(LinearLayout)rowView.findViewById(R.id.ingrList);
        holder.toolsList=(LinearLayout)rowView.findViewById(R.id.toolsList);

        holder.proteinCount=(TextView)rowView.findViewById(R.id.proteinCount);
        holder.fatsCount=(TextView)rowView.findViewById(R.id.fatsCount);
        holder.carbonsCount=(TextView)rowView.findViewById(R.id.carbonsCount);
        holder.caloriesCount=(TextView)rowView.findViewById(R.id.caloriesCount);

        holder.proteinCount.setText(String.valueOf(recipe.getDemands().getProtein()));
        holder.fatsCount.setText(String.valueOf(recipe.getDemands().getFats()));
        holder.carbonsCount.setText(String.valueOf(recipe.getDemands().getCarbonyd()));
        holder.caloriesCount.setText("Где!?");

        rowView.setTag(holder);

        ArrayList<Demands.CurrentIngredient> ingr=recipe.getDemands().getIngredients();
        for (Demands.CurrentIngredient ingredient: ingr){
            holder.listView.addView(addIngr(ingredient));
        }

        ArrayList<Tool> tools=recipe.getDemands().getTools();
        for (Tool tool: tools){
            holder.toolsList.addView(addTool(tool));
        }

        holder.mainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("logger", "click");
                int startSize = holder.mainView.getHeight();
                //  ViewGroup.LayoutParams params=holder.recipeDrawable.getLayoutParams();
                //   params.height*=2;
                // holder.recipeDrawable.setLayoutParams(params);
                boolean expanded = holder.layout.isExpanded();
                Animation animation;
                Animation animation1;
                final float mul;
                //holder.mainView.startAnimation(expandCardAnimation);
                if (expanded) {
                    //holder.recipeTitle.startAnimation( AnimationUtils.loadAnimation(context,R.anim.recipe_image_expand));
                    //holder.recipeTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                    mul=0.6666666666666666666666666666666666666666f;
                    animation = AnimationUtils.loadAnimation(context, R.anim.recipe_title_expand);
                    holder.layout.collapse();
                    holder.recipeTitle.setMaxLines(1);
                    animation1=AnimationUtils.loadAnimation(context,R.anim.recipe_image_collapse);
                } else {
                    mul=1.5f;
                    //holder.recipeTitle.startAnimation( AnimationUtils.loadAnimation(context,R.anim.recipe_image_collapse));
                    animation = AnimationUtils.loadAnimation(context, R.anim.recipe_title_collapse);
                    holder.layout.expand();
                    holder.recipeTitle.setMaxLines(99);
                    animation1=AnimationUtils.loadAnimation(context,R.anim.recipe_image_expand);
                }

                //Log.d("anim","end");

                //rowView.findViewById(R.id.relativeLayout).setLayoutParams(params);
                Animation.AnimationListener animationListener=new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        ViewGroup.LayoutParams params= rowView.findViewById(R.id.relativeLayout).getLayoutParams();
                        params.width*=mul;
                        params.height*=mul;
                        rowView.findViewById(R.id.relativeLayout).clearAnimation();
                        rowView.findViewById(R.id.relativeLayout).setLayoutParams(params);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                };
                animation1.setAnimationListener(animationListener);
                //rowView.findViewById(R.id.relativeLayout).startAnimation(animation1);
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
        return rowView;
    }

}
