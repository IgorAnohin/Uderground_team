package ru.underground.test42.InnerThings;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;


public class Future_history extends History {

    static public void addRecipe(int id, String date)
    {
        HistoryUnit historyUnit = new HistoryUnit();
        historyUnit.recipeID = id;
        historyUnit.time = Timestamp.valueOf(date);
        m_history.add(historyUnit);
    }
}
