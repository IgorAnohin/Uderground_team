package com.example.igor.profile.InnerThings;

public class Tool {
    private int m_id;
    private String m_name;
    private String m_pictureUrl;

    public boolean Initialize(int id, String name, String url) {
        //Проверка url картинки
        if (URLChecker.checkURL(url) && URLChecker.checkForPicture(url))
            m_pictureUrl = url;
        else
            return false;

        m_id = id;
        m_name = name;

        return true;
    }

    public int getId(){
        return m_id;
    }

    public String getName() {
        return m_name;
    }


    public String getUrl() {
        return m_pictureUrl;
    }
}
