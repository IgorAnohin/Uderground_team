package ru.underground.test42.InnerThings;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

public class History {
    static protected class HistoryUnit
    {
        public Timestamp time;
        public int recipeID;
    }

    static protected ArrayList<HistoryUnit> m_history;


    public static void Initialize(){
        m_history = new ArrayList<>();
    }

    static public void addRecipe(int id)
    {
        HistoryUnit historyUnit = new HistoryUnit();
        historyUnit.recipeID = id;
        historyUnit.time = new Timestamp(System.currentTimeMillis());
        m_history.add(historyUnit);
    }

    public ArrayList<HistoryUnit> getHistory()
    {
        return m_history;
    }
    public static JsonArray save()
    {
        JsonArray jArray = new JsonArray();
        for(int i = 0;i<m_history.size();i++)
        {
            JsonObject jObj = new JsonObject();
            jObj.addProperty("id", m_history.get(i).recipeID);
            jObj.addProperty("time", m_history.get(i).time.toString());
            jArray.add(jObj);
        }
        return  jArray;
    }

    public static void load(JsonArray jArray)
    {
        for(int i = 0;i<jArray.size();i++)
        {
            JsonObject jObj = jArray.get(i).getAsJsonObject();
            HistoryUnit unit = new HistoryUnit();
            unit.recipeID = jObj.get("id").getAsInt();
            unit.time = Timestamp.valueOf(jObj.get("time").getAsString());
            m_history.add(unit);
        }
    }
}
