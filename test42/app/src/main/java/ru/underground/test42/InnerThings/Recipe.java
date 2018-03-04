package ru.underground.test42.InnerThings;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.*;

public class Recipe implements Searchable{

    private int m_id;
    private String m_name;
    private String m_desc;
    private String m_pictureUrl;
    private int m_difficult;

    private Demands m_demands;
    private ArrayList<Step> m_steps;
    private ArrayList<String> m_tags;

    private int m_currentStep = -1;

    public boolean Initialize(String jsonStr)
    {
        return true;
    }

    public boolean Initialize(int id, String name, String desc, String url,int difficult)
    {
        //Проверка url картинки
        if (URLChecker.checkURL(url) && URLChecker.checkForPicture(url))
            m_pictureUrl = url;
        else
            return false;

        m_id = id;
        m_name = name;
        m_desc = desc;
        m_difficult = difficult;

        try {
            m_demands = new Demands();
            m_steps = new ArrayList();
        }
        catch (Exception e){
            return false;
        }

        return true;
    }

    public void addDemands(ArrayList<Integer> ingrIDs, ArrayList<Integer> ingrSizes, ArrayList<Integer> toolIDs)
    {
        m_demands.Initialize(ingrIDs,ingrSizes,toolIDs);
    }

    public void addTags(ArrayList<String> tags) {
        m_tags = tags;
    }

    public void parseStepsString(String str)
    {
        String[] splitedStr = str.split("\\&");
        for(int i = 0;i<splitedStr.length;i++)
            addStep(splitedStr[i]);
    }

    public void addStep(String str)
    {
        String[] splitedStr = str.split("\\|");
        try{
            String url = splitedStr[0];
            int time = Integer.parseInt(splitedStr[1]);
            ArrayList<Integer> lastSteps = strToIntArray(splitedStr[2]);
           int type = Integer.parseInt(splitedStr[3]);
            String desc = splitedStr[4];
            addStep(url,time,lastSteps,type,desc);
        }
        catch (Exception e){
        }
    }

    public void addStep(String url, int time, ArrayList<Integer>  lastSteps, int type, String desc)
    {
        boolean result;

        Step step = new Step();
        result = step.Initialize(url,time,lastSteps,type,desc);
        if(!result)
            return;
        m_steps.add(step);
    }

    private ArrayList<Integer> strToIntArray(String str)
    {
        ArrayList<Integer> list = new ArrayList();
        String[] splitedStr = str.split(",");
        try{
            for(int i = 0;i<splitedStr.length;i++)
            {
                int number = Integer.parseInt(splitedStr[i]);
                list.add(number);
            }
        }
        catch (Exception e) {
        }
        return list;
    }

    // Готовность к следующему шагу
    public boolean isReady()
    {
        if(m_currentStep <= -1)
            return true;

        if(m_currentStep >= m_steps.size())
            return false;

        ArrayList<Integer> lastSteps = m_steps.get(m_currentStep).getLastSteps();
        for(int i = 0;i<lastSteps.size();i++)
        {
            if(lastSteps.get(i)<m_currentStep && !m_steps.get(lastSteps.get(i)).isDone())
                return false;
        }
        return true;
    }

    public void setDone(int number)
    {
        try{
            m_steps.get(number).setDone(true);
        }
        catch (Exception e){}
    }

    public boolean nextStep()
    {
        if(m_currentStep<m_steps.size())
        {
            m_currentStep++;
            return false;
        }
        else
            return true;
    }

    public void clearSteps()
    {
        for(int i = 0;i<m_steps.size();i++)
        {
            m_steps.get(i).setDone(false);
        }
        m_currentStep = -1;
    }

    public void setTimer(int number, int minutes)
    {
        //Код для таймера
    }

    public String getName() {
        return m_name;
    }

    public String getUrl() {
        return m_pictureUrl;
    }

    public String getDesc() {
        return m_desc;
    }

    public int getId()
    {
        return m_id;
    }

    public int getDifficult()
    {
        return m_difficult;
    }

    public ArrayList<Step> getSteps()
    {
        return m_steps;
    }

    public ArrayList<String> getTags()
    {
        return m_tags;
    }

    public Demands getDemands() {
        return m_demands;
    }

    public float getKal()
    {
        return m_demands.getProtein()*4+m_demands.getCarbonyd()*9+m_demands.getFats()*4;
    }

    public JsonObject save() {

        try{
            JsonObject jObj = new JsonObject();
            //базовые поля класса
            jObj.addProperty("id", m_id);
            jObj.addProperty("name", m_name);
            jObj.addProperty("desc", m_desc);
            jObj.addProperty("url", m_pictureUrl);
            jObj.addProperty("difficult", m_difficult);
            jObj.addProperty("steps", createStepsStr());
            String tagsStr = new String();
            for(int i = 0;i<m_tags.size();i++)
                tagsStr+=m_tags.get(i) + ",";
            jObj.addProperty("tags",tagsStr);
            //ингридиенты
            JsonArray jIngrs = new JsonArray();
            ArrayList<Demands.CurrentIngredient> ingrs = m_demands.getIngredients();
            for(int i = 0;i < ingrs.size();i++)
            {
                JsonObject jIngr = new JsonObject();
                jIngr.addProperty("id", ingrs.get(i).ingredient.getId());
                jIngr.addProperty("size", ingrs.get(i).size);
                jIngrs.add(jIngr);
            }
            jObj.add("ingredients", jIngrs);
            //инструменты
            JsonArray jTools = new JsonArray();
            ArrayList<Tool> tools = m_demands.getTools();
            for(int i = 0;i < tools.size();i++)
            {
                JsonObject jTool = new JsonObject();
                jTool.addProperty("id", tools.get(i).getId());
                jTools.add(jTool);
            }
            jObj.add("tools", jTools);
            return jObj;
        }
        catch (Exception e){
        }
        return null;

    }

    public boolean Initialize(JsonObject jObj)
    {
        try{
            m_id = jObj.get("id").getAsInt();
            m_name = jObj.get("name").getAsString();
            m_desc = jObj.get("desc").getAsString();
            m_pictureUrl = jObj.get("url").getAsString();
            m_difficult = jObj.get("difficult").getAsInt();

            m_demands = new Demands();
            m_steps = new ArrayList();

            parseStepsString(jObj.get("steps").getAsString());
            m_tags = new ArrayList(Arrays.asList(jObj.get("tags").getAsString().split(",")));

            //загрузка ингредиентов
            ArrayList<Integer> ingrIDs = new ArrayList<>();
            ArrayList<Integer> ingrSizes = new ArrayList<>();
            JsonArray jIngrs = jObj.get("ingredients").getAsJsonArray();
            for(int i = 0;i < jIngrs.size();i++)
            {
                JsonObject jIngr = jIngrs.get(i).getAsJsonObject();
                ingrIDs.add(jIngr.get("id").getAsInt());
                ingrSizes.add(jIngr.get("size").getAsInt());
            }

            //инструменты
            ArrayList<Integer> toolIDs = new ArrayList<>();
            JsonArray jTools = jObj.get("tools").getAsJsonArray();
            for(int i = 0;i < jTools.size();i++)
            {
                JsonObject jTool = jTools.get(i).getAsJsonObject();
                toolIDs.add(jTool.get("id").getAsInt());
            }

            addDemands(ingrIDs,ingrSizes,toolIDs);
        }
        catch (Exception e){
            return false;
        }


        return true;
    }

    private String createStepsStr()
    {
        String str = new String();
        for(int i = 0;i<m_steps.size();i++)
        {
            Step step = m_steps.get(i);
            str+="&" + step.getUrl() + "|" + step.getTime() + "|";
            ArrayList<Integer> lastSteps = step.getLastSteps();
            for(int j = 0;j<lastSteps.size();j++)
                str += lastSteps.get(j) + ",";
            str += "|" + step.getType() + "|" + step.getDesc();
        }
        return str;
    }

    @Override
    public String searchStr() {
        return getName();
    }

    static void getQuery() {
        DBConnect.init();

        ResultSet res = DBConnect.sendQuery(DBConnect.TypeQuery.SELECT, "select * from recipe");

        try {
            while (res.next()) {

                int id = res.getInt("id");
                String name = res.getString("name");
                String url = res.getString("url");
                String desc = res.getString("desc");
                String steps = (res.getString("steps"));
                String[] tags = (res.getString("tags")).split(",");
                int difficult = Integer.valueOf(res.getString("difficulty"));
                Recipe temp = new Recipe();
                temp.Initialize(id, name, desc,url,difficult);
                temp.addTags(new ArrayList<>(Arrays.asList(tags)));

                temp.addStep(steps);

                MainSystem.add_recipe(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}