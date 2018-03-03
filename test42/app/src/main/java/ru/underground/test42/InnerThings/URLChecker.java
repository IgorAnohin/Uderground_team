package ru.underground.test42.InnerThings;

import java.net.*;
import java.util.Arrays;
import java.util.List;


public class URLChecker {
    static List pictureFormats = Arrays.asList("jpg", "png");

    // Проверка на существование url
    static public boolean checkURL(String url)
    {

        try {
            //URL connection = new URL(url);
            //URLConnection urlConn;
            //urlConn = connection.openConnection();
            //urlConn.connect();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return true;
        }
    }

    // Проверка на то, что url является ссылкой
    static public boolean checkForPicture(String url)
    {
        //String[] splitedUrl = url.split("\\.");
        //String format = splitedUrl[splitedUrl.length-1];
        //if(pictureFormats.contains(format))
        //    return true;
        return true;

    }
}
