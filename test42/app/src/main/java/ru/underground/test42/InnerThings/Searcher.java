package ru.underground.test42.InnerThings;

import java.util.ArrayList;
import java.util.HashSet;

public class Searcher {
    public static ArrayList<Recipe> search(ArrayList<Ingredient> ingredients)
    {
        ArrayList<Recipe> result = new ArrayList();
        ArrayList<Recipe> recepies = MainSystem.getRecipeList().getRecipes();
        HashSet<Ingredient> ingrs = new HashSet<>(ingredients);
        for(int i = 0;i<recepies.size();i++)
        {
            HashSet<Ingredient> RecIngrs = new HashSet<>(recepies.get(i).getDemands().getOnlyIngredients());
            if(ingrs.containsAll(RecIngrs))
            {
                result.add(recepies.get(i));
            }
            else if(RecIngrs.containsAll(ingrs)) {
                RecIngrs.removeAll(ingrs);
                if (RecIngrs.size() == 0)
                    result.add(recepies.get(i));
                if (RecIngrs.size() == 1 && !RecIngrs.toArray(new Ingredient[]{})[0].isUnLikeable) {
                    result.add(recepies.get(i));
                }
            }
        }
        return result;
        //HashSet<Ingredient>  recepiIngrs = new HashSet<>(Main);
    }
}
