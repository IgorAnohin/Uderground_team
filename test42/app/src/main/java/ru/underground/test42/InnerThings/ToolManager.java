package ru.underground.test42.InnerThings;

import com.google.gson.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ToolManager {
    private static Map<Integer, Tool> m_tools;



    public static void Initialize()
    {
        m_tools = new HashMap();
        try {
            ResultSet rs = DBConnect.sendQuery(DBConnect.TypeQuery.SELECT, "select * from tools");
            while (rs != null && rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String url = rs.getString("url");
                addTool(id,name,url);
            }
        } catch (Exception e) {
            //throw new RuntimeException("Something wrong with results.");
        }
    }

    public static Tool getTool(int id)
    {
        return m_tools.get(id);
    }

    public static void deleteTool (int id)
    {
        m_tools.remove(id);
    }

    public static void addTool(int id, String name, String url)
    {
        boolean result;

        Tool tool = new Tool();
        result = tool.Initialize(id, name,url);
        if(!result)
            return;

        m_tools.put(id, tool);
    }

    public static JsonArray save()
    {
        JsonArray jArray = new JsonArray();
        for(int i = 0;i<m_tools.size();i++)
        {
            try{
                JsonObject jTool = new JsonObject();
                Tool tool = m_tools.get(i);
                jTool.addProperty("id", tool.getId());
                jTool.addProperty("name", tool.getName());
                jTool.addProperty("url", tool.getUrl());
                jArray.add(jTool);
            }
            catch (Exception e)
            {
            }
        }
        return jArray;
    }

    public static void load (JsonArray jArray)
    {
        m_tools = new HashMap();
        for(int i = 0;i<jArray.size();i++)
        {
            try{
                JsonObject jTool = jArray.get(i).getAsJsonObject();
                int id = jTool.get("id").getAsInt();
                String name = jTool.get("name").getAsString();
                String url = jTool.get("url").getAsString();
                addTool(id,name,url);
            }
            catch (Exception e) {
            }
        }
    }
}
