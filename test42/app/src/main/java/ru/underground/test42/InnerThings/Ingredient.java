package ru.underground.test42.InnerThings;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.net.URL;

public class Ingredient implements Searchable{

    //типо enum
    public static int ML = 0;
    public static int L = 1;
    public static int G = 2;
    public static int KG = 3;
    public static int PIECE = 4;

    private int m_id;
    private String m_name;
    private String m_pictureUrl;
    private float m_price; // цена за 1 у. е.
    private int m_unitType;
    public boolean isUnLikeable = false;
    public boolean isChecked = false;

    private float m_protein;
    private float m_fats;
    private float m_carbonyd;
    public Bitmap loadedDrawable;

    public boolean Initialize(int id, String name, String url, float price, int unitType, float protein, float fats, float carbonyd)
    {
        //Проверка url картинки
        if(URLChecker.checkURL(url) && URLChecker.checkForPicture(url))
            m_pictureUrl = url;
        else
            return false;

        m_id = id;
        m_name = name;
        m_price = price;
        m_unitType = unitType;
        m_protein = protein;
        m_fats = fats;
        m_carbonyd = carbonyd;
        Thread t = new Thread()  {
            @Override
            public void run() {
                try {
                    final Bitmap bitmap = BitmapFactory.decodeStream(new URL(getUrl()).openStream());
                    loadedDrawable=bitmap;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
        };
        t.start();
        return true;
    }

    public String getName() {
        return m_name;
    }

    public String getUrl() {
        return m_pictureUrl;
    }

    public float getPrice() {
        return m_price;
    }

    public int getUnitType() {
        return m_unitType;
    }

    public float getProtein() {
        return m_protein;
    }

    public float getFats() {
        return m_fats;
    }

    public float getCarbonyd() {
        return m_carbonyd;
    }

    public int getId(){
        return m_id;
    }

    @Override
    public String searchStr() {
        return getName();
    }

    public String parseUnitType(int type)
    {
        switch (type)
        {
            case(0):
                return "мл";
            case(1):
                return "л";
            case(2):
                return "г";
            case(3):
                return "кг";
            case(4):
                return "шт";
            default:
                return "";
        }
    }
}
