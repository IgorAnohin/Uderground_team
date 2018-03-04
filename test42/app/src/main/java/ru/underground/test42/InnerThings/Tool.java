package ru.underground.test42.InnerThings;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.net.URL;

public class Tool implements Searchable{
    private int m_id;
    private String m_name;
    private String m_pictureUrl;
    public Bitmap loadedDrawable;

    public boolean Initialize(int id, String name, String url) {
        //Проверка url картинки
        if (URLChecker.checkURL(url) && URLChecker.checkForPicture(url))
            m_pictureUrl = url;
        else
            return false;
        Thread t = new Thread()  {
            @Override
            public void run() {
                try {
                    final Bitmap bitmap = BitmapFactory.decodeStream(new URL(getUrl()).openStream());
                    loadedDrawable=bitmap;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
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

    @Override
    public String searchStr() {
        return getName();
    }
}
