package com.movilizer.client.android.handler.util;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;



/**
 * Created by Alberto Gil Tesa on 19/03/2018.
 */
public class BarcodeType
{
    private String type          = null;
    private String minimumLength = null;
    private String maximumLength = null;


    public BarcodeType( String type )
    {
        this.type = type;
    }


    public static Map< String, BarcodeType > getBarcodeTypesList( String value )
    {
        Map< String, BarcodeType > barcodeTypes = new HashMap<>();

        if( value != null && !value.isEmpty() )
        {
            try
            {
                JSONObject    json = new JSONObject(value); //{"QRCODE":{"MIN_LENGTH":5},"MAX_LENGTH":10,"DATAMATRIX":{}}
                Iterator< ? > keys = json.keys();

                while( keys.hasNext() )
                {
                    String key = (String)keys.next();
                    if( json.get(key) instanceof JSONObject )
                    {
                        BarcodeType bType = new BarcodeType(key);

                        JSONObject json2 = new JSONObject(json.get(key).toString());
                        if( json2.length() > 0 )
                        {
                            bType.setMinimumLength(TryParse.tryParseString(json2.get("MIN_LENGTH"), null));
                            bType.setMaximumLength(TryParse.tryParseString(json2.get("MAX_LENGTH"), null));
                        }

                        barcodeTypes.put(key, bType);
                    }
                }
            }
            catch( Exception ex )
            {
                ex.printStackTrace();
            }
        }
        return barcodeTypes;
    }


    public String getType()
    {
        return type;
    }


    public String getMinimumLength()
    {
        return minimumLength;
    }


    public String getMaximumLength()
    {
        return maximumLength;
    }


    public void setType( String type )
    {
        this.type = type;
    }


    public void setMinimumLength( String minimumLength )
    {
        this.minimumLength = minimumLength;
    }


    public void setMaximumLength( String maximumLength )
    {
        this.maximumLength = maximumLength;
    }
}
