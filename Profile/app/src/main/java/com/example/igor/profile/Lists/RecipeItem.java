package ru.underground.test42.Lists;

import ru.underground.test42.InnerThings.Recipe;

/**
 * Created by HukuToc2288 on 02.03.2018.
 */

public class RecipeItem {

    Recipe recipe;
    public boolean expanded=false;

    public RecipeItem(Recipe recipe){
        this.recipe=recipe;
    }

    public Recipe getRecipe() {
        return recipe;
    }
}
