package ru.underground.test42.InnerThings;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.preference.Preference;
import android.preference.PreferenceManager;


import com.google.gson.*;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;



public class MainSystem {
    private static UserState m_userResources;
    private static RecipeList m_recipes = new RecipeList();
    private static String filename = "file.txt";
    private static Activity m_activity;

    public static void Initialize(Activity activity)
    {
        //if pfgeotyо в превый раз
//        Recipe.getQuery();
        /* IngridientManager.Initialize();

        ToolManager.Initialize();
        m_recipes.createTest();
        save(); */
        m_activity = activity;

        loadAssets();
        History.Initialize();
//      DBConnect.init();
        //m_recipes = new RecipeList();
        //IngridientManager.Initialize();
        //ToolManager.Initialize();
        //m_recipes.createTest();
        //saveToFile("file.txt", "tSETrtgre");
        load();
        save();
        //load();

        m_userResources = new UserState();
    }

    public static void add_recipe(Recipe recipe) {
        m_recipes.add_recipe(recipe);
    }

    public static void loadAssets()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(m_activity.getApplicationContext());
        if(preferences.getBoolean("isFirstLaunch",true));
        {
            preferences.edit().putBoolean("isFirstLaunch",false);
            try{
                Scanner input = new Scanner(m_activity.getAssets().open("file.txt"));
                String str = input.nextLine();
                input.close();
                saveToFile("file.txt",str);
            }
            catch (Exception e){}
        }
    }
    private static void load()
    {
        try{
            JsonParser parser = new JsonParser();
            JsonObject jObj = parser.parse(readFromFile()).getAsJsonObject();
            IngridientManager.load(jObj.get("ingredients").getAsJsonArray());
            ToolManager.load(jObj.get("tools").getAsJsonArray());
            m_recipes = new RecipeList();
            m_recipes.load(jObj.get("recepies").getAsJsonArray());
            History.load(jObj.get("history").getAsJsonArray());
            Future_history.load(jObj.get("history").getAsJsonArray());
        }
        catch (Exception e){
        }

    }

    public static void save()
    {
        JsonObject jObj = new JsonObject();
        jObj.add("ingredients",IngridientManager.save());
        jObj.add("tools",ToolManager.save());
        jObj.add("recepies",m_recipes.save());
        jObj.add("history", History.save());
        saveToFile("file.txt", jObj.toString());
    }

    private static String readFromFile()
    {
        try{
            InputStreamReader isr = new InputStreamReader(m_activity.openFileInput(filename));
            BufferedReader reader = new BufferedReader(isr);
            String str = reader.readLine();
            reader.close();
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
            OutputStream outputStream = m_activity.openFileOutput(filename, 0);
            OutputStreamWriter osw = new OutputStreamWriter(outputStream);
            osw.write(str);
            osw.close();
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

    public static float getDayKal()
    {
        ArrayList<History.HistoryUnit> m_historyUnit = History.getHistory();
        float kal = 0;
        for(int i = 0;i<m_historyUnit.size();i++)
        {
            Timestamp today = new Timestamp(System.currentTimeMillis());
            if(m_historyUnit.get(i).time.getDay() == today.getDay())
                kal+=m_recipes.find(m_historyUnit.get(i).recipeID).getKal();
        }
        return kal;
    }
}
