package com.movilizer.client.android.handler.util;


import org.json.JSONObject;



/**
 * Created by Alberto Gil Tesa on 09/03/2018.
 */
@SuppressWarnings({"UnusedDeclaration"})
public class TryParse
{


    /**
     *
     */
    private TryParse()
    {
    }



    /**
     * @param value
     * @param defaultValue
     *
     * @return
     */
    public static short tryParseShort( String value, short defaultValue )
    {
        try
        {
            return Short.parseShort(trim(value));
        }
        catch( Exception ex )
        {
            return defaultValue;
        }
    }



    /**
     * @param value
     * @param defaultValue
     *
     * @return
     */
    public static byte tryParseByte( String value, byte defaultValue )
    {
        try
        {
            return Byte.parseByte(trim(value));
        }
        catch( Exception ex )
        {
            return defaultValue;
        }
    }



    /**
     * @param value
     * @param defaultValue
     *
     * @return
     */
    public static long tryParseLong( String value, long defaultValue )
    {
        try
        {
            return Long.parseLong(trim(value));
        }
        catch( Exception ex )
        {
            return defaultValue;
        }
    }



    /**
     * @param value
     * @param defaultValue
     *
     * @return
     */
    public static boolean tryParseBoolean( String value, boolean defaultValue )
    {
        try
        {
            return Boolean.parseBoolean(trim(value));
        }
        catch( Exception ex )
        {
            return defaultValue;
        }
    }



    public static boolean tryParseBoolean( Object rawValue, boolean defaultValue )
    {
        try
        {
            return Boolean.parseBoolean(tryParseString(rawValue, String.valueOf(defaultValue)));
        }
        catch( Exception ex )
        {
            return defaultValue;
        }
    }



    public static boolean tryParseBoolean( JSONObject json, String key, boolean defaultValue )
    {
        try
        {
            return json.getBoolean(key);
        }
        catch( Exception ex )
        {
            return defaultValue;
        }
    }



    /**
     * @param rawValue
     * @param defaultValue
     *
     * @return
     */
    public static int tryParseInteger( String rawValue, Integer defaultValue )
    {
        try
        {
            String value = String.valueOf(defaultValue);

            if( rawValue != null )
            {
                value = rawValue.trim();

                if( value.endsWith(".0") )
                {
                    value = value.substring(0, value.indexOf(".0"));
                }
            }

            return Integer.parseInt(value);
        }
        catch( Exception ex )
        {
            return defaultValue;
        }
    }



    public static int tryParseInteger( Object rawValue, Integer defaultValue )
    {
        try
        {
            return tryParseInteger(tryParseString(rawValue, String.valueOf(defaultValue)), defaultValue);
        }
        catch( Exception ex )
        {
            return defaultValue;
        }
    }



    /**
     * @param value
     * @param defaultValue
     *
     * @return
     */
    public static double tryParseDouble( String value, double defaultValue )
    {
        try
        {
            return Double.parseDouble(trim(value));
        }
        catch( Exception ex )
        {
            return defaultValue;
        }
    }



    /**
     * @param value
     * @param defaultValue
     *
     * @return
     */
    public static String tryParseString( String value, String defaultValue )
    {
        return tryParseString((Object) value, defaultValue);
    }



    public static String tryParseString( Object rawValue, String defaultValue )
    {
        try
        {
            String value = null;

            if( rawValue != null )
                value = String.valueOf(rawValue).trim();

            return (value != null && !value.isEmpty()) ? value : defaultValue;
        }
        catch( Exception ex )
        {
            ex.printStackTrace();
            return defaultValue;
        }
    }



    public static String tryParseString( JSONObject json, String key, String defaultValue )
    {
        try
        {
            String rawValue = json.getString(key);
            String value    = null;

            if( rawValue != null )
            {
                value = String.valueOf(rawValue).trim();
            }

            return (value != null && !value.isEmpty()) ? value : defaultValue;
        }
        catch( Exception ex )
        {
            ex.printStackTrace();
            return defaultValue;
        }
    }



    /**
     * @param rawValue
     *
     * @return
     */
    private static String trim( String rawValue )
    {
        return rawValue != null ? rawValue.replace("\r", "").replace("\n", "").trim() : null;
    }

}
