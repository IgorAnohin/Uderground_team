package ru.underground.test42.InnerThings;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.sql.Timestamp;
import java.util.ArrayList;


public class Future_history {

        static protected class HistoryUnit
        {
            public Timestamp time;
            public int recipeID;
        }

        static private ArrayList<HistoryUnit> m_future_history;


        public static void Initialize(){
            m_future_history = new ArrayList<>();
        }

        static public void addRecipe(int id, String date)
        {
            HistoryUnit historyUnit = new HistoryUnit();
            historyUnit.recipeID = id;
            historyUnit.time = Timestamp.valueOf(date);
            m_future_history.add(historyUnit);
        }
        public static ArrayList<HistoryUnit> getHistory()
        {
            return m_future_history;
        }
        public static JsonArray save()
        {
            JsonArray jArray = new JsonArray();
            for(int i = 0; i< m_future_history.size(); i++)
            {
                JsonObject jObj = new JsonObject();
                jObj.addProperty("id", m_future_history.get(i).recipeID);
                jObj.addProperty("time", m_future_history.get(i).time.toString());
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
                m_future_history.add(unit);
            }
        }
}


