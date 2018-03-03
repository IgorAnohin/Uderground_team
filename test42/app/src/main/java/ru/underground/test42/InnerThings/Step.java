package ru.underground.test42.InnerThings;

import java.util.ArrayList;

public class Step {

    //типо enum
    public static int BASE = 0;
    public static int TIMER = 1;

    private String m_pictureUrl;
    private int m_time;
    private ArrayList<Integer>  m_lastSteps;
    private int m_type;
    private String m_desc;

    private boolean m_done;

    public boolean Initialize(String url, int time,ArrayList<Integer>  lastSteps, int type, String desc) {
        //Проверка url картинки
        if (URLChecker.checkURL(url) && URLChecker.checkForPicture(url))
            m_pictureUrl = url;
        else
            return false;

        m_time = time;
        m_lastSteps = lastSteps;
        m_type = type;
        m_desc = desc;
        m_done = false;

        return true;
    }

    public String getUrl() {
        return m_pictureUrl;
    }

    public int getTime() {
        return m_time;
    }


    public ArrayList<Integer> getLastSteps() {
        return m_lastSteps;
    }

    public int getType() {
        return m_type;
    }

    public String getDesc() {
        return m_desc;
    }

    public boolean isDone() {
        return m_done;
    }

    public void setDone(boolean done) {
        m_done = done;
    }
}
