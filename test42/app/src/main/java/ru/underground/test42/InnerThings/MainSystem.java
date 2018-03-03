package ru.underground.test42.InnerThings;

import com.google.gson.*;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;



public class MainSystem {
    private static UserState m_userResources;
    private static RecipeList m_recipes;

    public static void Initialize()
    {
//      DBConnect.init();
        m_recipes = new RecipeList();
        IngridientManager.Initialize();
        ToolManager.Initialize();
        m_recipes.createTest();
        save("file.txt");
        load("file.txt");

        m_userResources = new UserState();
    }

    private static void load(String filename)
    {
        try{
            JsonParser parser = new JsonParser();
            JsonObject jObj = parser.parse(readFromFile(filename)).getAsJsonObject();
            IngridientManager.load(jObj.get("ingredients").getAsJsonArray());
            ToolManager.load(jObj.get("tools").getAsJsonArray());
            m_recipes = new RecipeList();
            m_recipes.load(jObj.get("recepies").getAsJsonArray());
        }
        catch (Exception e){
        }

    }

    public static void save(String filename)
    {
        JsonObject jObj = new JsonObject();
        jObj.add("ingredients",IngridientManager.save());
        jObj.add("tools",ToolManager.save());
        jObj.add("recepies",m_recipes.save());
        saveToFile(filename, jObj.toString());
    }

    private static String readFromFile(String filename)
    {
        try{
            Scanner input = new Scanner(new FileInputStream(filename));
            String str = input.nextLine();
            input.close();
            return str;
        }
        catch (Exception e)
        {

        }
        return "";
    }

    private static void saveToFile(String filename, String str)
    {
        try{
            FileOutputStream file = new FileOutputStream(filename);
            file.write(str.getBytes());
            file.close();
        }
        catch (Exception e)
        {

        }
    }

    public UserState getUserResources() {
        return m_userResources;
    }

    public static RecipeList getRecipeList() {
        return m_recipes;
    }
}
