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


    public void createTest()
    {
        Recipe recipe1 = new Recipe();
        recipe1.Initialize("плов-хуев","Знаменитое блюдо, прещедшее, по слухам, из Грузии. Быстро в готовке, " +
                "питаттельно. Сложность приготовления сможет отпугнуть неподготовленных, но в итоге останутся только избранные.",
                "http://vkusnatisha.ru/wp-content/uploads/2017/04/7fb0ba22ade79be35017e8a0f07fb089.jpg",6);
        ArrayList<Integer> ingrIDs1 = new ArrayList(Arrays.asList(new Integer[]{0,1,2,3,4,5,6}));
        ArrayList<Integer> ingrSizes1 = new ArrayList(Arrays.asList(new Integer[]{70,1,25,17,10,1,1}));
        ArrayList<Integer> toolIDs = new ArrayList(Arrays.asList(new Integer[]{0,1}));
        ArrayList<String> tags1 = new ArrayList(Arrays.asList(new String[]{"твердая","хуевая"}));
        recipe1.addDemands(ingrIDs1, ingrSizes1,toolIDs);
        recipe1.addTags(tags1);
        recipe1.parseStepsString("http://novosib-room.ru/uploads/novosib/2015/02/7372091_original.jpg|10|-1|0|ху" +
                "&http://novosib-room.ru/uploads/novosib/2015/02/7372091_original.jpg|10|-1|0|як");

        Recipe recipe2 = new Recipe();
        recipe2.Initialize("суп из семи залуп aaaaaaaa aaaaaaaaa AAAAAAAAAAA","типа супчик",
                "http://vkusnatisha.ru/wp-content/uploads/2017/04/7fb0ba22ade79be35017e8a0f07fb089.jpg",9);
        ArrayList<Integer> ingrIDs2 = new ArrayList(Arrays.asList(new Integer[]{7,8}));
        ArrayList<Integer> ingrSizes2 = new ArrayList(Arrays.asList(new Integer[]{7,6}));
        ArrayList<String> tags2 = new ArrayList(Arrays.asList(new String[]{"жидкая","хуевая"}));
        recipe2.addDemands(ingrIDs2, ingrSizes2,toolIDs);
        recipe2.addTags(tags2);
        recipe2.parseStepsString("http://novosib-room.ru/uploads/novosib/2015/02/7372091_original.jpg|10|-1|0|ху" +
                "&http://novosib-room.ru/uploads/novosib/2015/02/7372091_original.jpg|10|-1|0|як");

        m_recipes.add(recipe1);
        m_recipes.add(recipe2);


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
}
