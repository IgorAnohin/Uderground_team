package ru.underground.test42.InnerThings;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

public class History {
    static private class HistoryUnit
    {
        public Timestamp time;
        public int recipeID;
    }

    static ArrayList<HistoryUnit> history;

    public void Initialize(){
        history = new ArrayList<>();
    }

    static public void addRecipe(int id)
    {
        HistoryUnit historyUnit = new HistoryUnit();
        historyUnit.recipeID = id;
        historyUnit.time = new Timestamp(System.currentTimeMillis());
    }
}
