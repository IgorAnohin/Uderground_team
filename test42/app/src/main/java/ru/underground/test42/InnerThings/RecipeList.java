package ru.underground.test42.InnerThings;

import com.google.gson.JsonArray;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


public class RecipeList {
    private ArrayList<Recipe> m_recipes;

    public RecipeList(){
        m_recipes = new ArrayList();
    }

    public void add_recipe(Recipe recipe) {
        m_recipes.add(recipe);
    }



    public ArrayList<Recipe> getRecipes(){
        return m_recipes;
    }

    public void load(JsonArray jArray)
    {
        for(int i = 0;i<jArray.size();i++)
        {
            Recipe recipe = new Recipe();
            recipe.Initialize(jArray.get(i).getAsJsonObject());
            m_recipes.add(recipe);
        }
    }
    public JsonArray save ()
    {
        JsonArray jArray = new JsonArray();
        for(int i = 0;i<m_recipes.size();i++)
        {
            jArray.add(m_recipes.get(i).save());
        }
        return jArray;
    }

    public Recipe find(int id)
    {
        int i = 0;
        while(i<m_recipes.size())
        {
            if(m_recipes.get(i).getId()==id)
                return m_recipes.get(i);
            i++;
        }
        return null;
    }
}
