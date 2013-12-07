package de.f14tomcat.tenjava.util;

public class NumberUtil
{
    public static boolean isInt(String s)
    {
        try
        {
            Integer.parseInt( s );
            return true;
        } catch ( NumberFormatException ex )
        {
            return false;
        }
    }

    public static int getInt(String s)
    {
        return Integer.parseInt( s );
    }
}
