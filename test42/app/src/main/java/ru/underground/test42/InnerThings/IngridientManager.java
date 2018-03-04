package ru.underground.test42.InnerThings;

import com.google.gson.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class IngridientManager {
    private static Map<Integer, Ingredient> m_ingredients;



    public static void Initialize()
    {
        m_ingredients = new HashMap<>();
        try {
            ResultSet rs = DBConnect.sendQuery(DBConnect.TypeQuery.SELECT, "select * from ingredients");
            while (rs != null && rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String url = rs.getString("url");
                float price = rs.getFloat("price");
                int unitType = rs.getInt("unitType");
                float protein = rs.getFloat("protein");
                float fats = rs.getFloat("fats");
                float carbonyd = rs.getFloat("carbonyd");
                addIngredient(id,name,url,price,unitType,protein,fats,carbonyd);
            }
        } catch (Exception e) {
            //throw new RuntimeException("Something wrong with results.");
        }

    }

    public static Ingredient getIngredient(int id)
    {
        return m_ingredients.get(id);
    }

    public static void deleteIngredient (int id)
    {
        m_ingredients.remove(id);
    }



    public static void addIngredient(int id, String name, String url, float price, int unitType ,float protein, float fats, float carbonyd)
    {
        boolean result;

        Ingredient ingr = new Ingredient();
        result = ingr.Initialize(id, name,url,price,unitType, protein, fats, carbonyd);
        if(!result)
            return;

        m_ingredients.put(id, ingr);
    }

    public static JsonArray save()
    {
        JsonArray jArray = new JsonArray();
        for(int i = 0;i<m_ingredients.size();i++)
        {
            try{
                JsonObject jIngr = new JsonObject();
                Ingredient ingr = m_ingredients.get(i);
                jIngr.addProperty("id", ingr.getId());
                jIngr.addProperty("name", ingr.getName());
                jIngr.addProperty("url", ingr.getUrl());
                jIngr.addProperty("price", ingr.getPrice());
                jIngr.addProperty("unitType", ingr.getUnitType());
                jIngr.addProperty("protein", ingr.getProtein());
                jIngr.addProperty("fats", ingr.getFats());
                jIngr.addProperty("carbonyd", ingr.getCarbonyd());
                jIngr.addProperty("isUnLikeable", ingr.isUnLikeable);
                jArray.add(jIngr);
            }
            catch (Exception e)
            {
            }

        }
        return jArray;
    }

    public static void load (JsonArray jArray)
    {
        m_ingredients = new HashMap<>();
        for(int i = 0;i<jArray.size();i++)
        {
            try{
                JsonObject jIngr = jArray.get(i).getAsJsonObject();
                int id = jIngr.get("id").getAsInt();
                String name = jIngr.get("name").getAsString();
                String url = jIngr.get("url").getAsString();
                float price = (float)jIngr.get("price").getAsFloat();
                int unitType = jIngr.get("unitType").getAsInt();
                float protein = (float)jIngr.get("protein").getAsFloat();
                float fats = (float)jIngr.get("fats").getAsFloat();
                float carbonyd = (float)jIngr.get("carbonyd").getAsFloat();
                addIngredient(id,name,url,price,unitType,protein,fats,carbonyd);
                m_ingredients.get(id).isUnLikeable=(jIngr.get("isUnLikeable").getAsBoolean());
            }
            catch (Exception e) {
            }
        }
    }

    public static Ingredient[] getAllIngredients()
    {
        return m_ingredients.values().toArray(new Ingredient[]{});
    }
}
