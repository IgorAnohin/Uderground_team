package com.example.igor.profile.InnerThings;

import com.google.gson.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class IngridientManager {
    private static Map<Integer, Ingredient> m_ingredients;

    private static void createTest()
    {
        for(int i = 0;i<9;i++)
        {
            addIngredient(0,"Укроп", "https://domoidostavim.ru/images/static/products/3dd6925c-e756-409d-bb43-756ac637c0b5.jpg",
                    30, 2,100,20,10);
            addIngredient(1,"Кошачья жопа", "http://kotomatrix.ru/images/lolz/2009/01/27/Rz.jpg",
                    5430, 4,100,2000,170);
            addIngredient(2,"Картошина", "http://novosib-room.ru/uploads/novosib/2015/02/7372091_original.jpg",
                    20, 4,1030,30,40);
            addIngredient(3,"Мандавошина", "https://static.baza.farpost.ru/v/1500266049017_hugeBlock",
                    3000, 4,200,60,130);
            addIngredient(4,"Вода", "http://kangenwaterbantul.com/wp-content/uploads/2013/11/Front-image_drinking-water.jpg",
                    60, 1,100,20,10);
            addIngredient(5,"Хуй туды", "https://static.baza.farpost.ru/v/1500266049017_hugeBlock",
                    15, 4,200,10,40);
            addIngredient(6,"Охапка дров", "https://domoidostavim.ru/images/static/products/3dd6925c-e756-409d-bb43-756ac637c0b5.jpg",
                    300, 3,180,20,10);
            addIngredient(7,"Залупа", "https://upload.wikimedia.org/wikipedia/commons/c/c7/Jms_003.JPG",
                    80, 2,100,20,10);
            addIngredient(8,"Порошенко", "http://vestikavkaza.ru/upload/nvk/2015_Jul/Poroshenko-vstretitsya-s-glavoy-Evroparlamenta.jpg",
                    30000, 2,100,20,10);
        }
    }

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

        createTest();
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
                Ingredient ingr = new Ingredient();
                jIngr.addProperty("id", ingr.getId());
                jIngr.addProperty("name", ingr.getName());
                jIngr.addProperty("url", ingr.getUrl());
                jIngr.addProperty("price", ingr.getPrice());
                jIngr.addProperty("unitType", ingr.getUnitType());
                jIngr.addProperty("protein", ingr.getProtein());
                jIngr.addProperty("fats", ingr.getFats());
                jIngr.addProperty("carbonyd", ingr.getCarbonyd());
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
            }
            catch (Exception e) {
            }
        }
    }
}
