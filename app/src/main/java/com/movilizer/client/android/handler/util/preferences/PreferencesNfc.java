package com.movilizer.client.android.handler.util.preferences;


import android.content.Context;

import com.movilizer.client.android.handler.util.TryParse;

import java.security.InvalidParameterException;
import java.util.Map;



/**
 * Created by Alberto Gil Tesa
 */
@SuppressWarnings({"UnusedDeclaration"})
public class PreferencesNfc extends Preferences
{
    private final String PREFIX           = "NFC_";
    //Parameters from MEL:
    private final String TIMEOUT          = "TIMEOUT";
    //Parameters from MEL & APP UI:
    private final String ACTIVE_SOUND     = "ACTIVE_SOUND";
    private final String ACTIVE_VIBRATION = "ACTIVE_VIBRATION";



    /**
     * @param context
     */
    public PreferencesNfc( Context context )
    {
        super(context);
    }



    /**
     * @param context
     * @param properties
     *
     * @throws InvalidParameterException
     */
    public PreferencesNfc( Context context, Map<String, Object> properties ) throws InvalidParameterException
    {
        super(context);


        // EVENT_ID (Number)
        //
        setEventID(readEventID(properties));


        // EVENT_TYPE (Number: 0=Synchronous(default) | 1=Asynchronous Guaranteed | 2=Asynchronous)
        //
        setEventType(TryParse.tryParseInteger(properties.get(EVENT_TYPE), 0));


        // TIMEOUT (Number seconds)
        //
        setTimeout(TryParse.tryParseInteger(properties.get(TIMEOUT), 0));


        // ACTIVE_SOUND (true/false)
        //
        setActiveSound(TryParse.tryParseBoolean(properties.get(ACTIVE_SOUND), isActiveSound()));


        // ACTIVE_VIBRATION (true/false)
        //
        setActiveVibration(TryParse.tryParseBoolean(properties.get(ACTIVE_VIBRATION), isActiveVibration()));
    }


    /// GETTERS ////////////////////////////////////////////////////////////////////////////////////



    public int getTimeout()
    {
        return read.getInt(PREFIX + TIMEOUT, 0);
    }



    public boolean isActiveSound()
    {
        return read.getBoolean(PREFIX + ACTIVE_SOUND, false);
    }



    public boolean isActiveVibration()
    {
        return read.getBoolean(PREFIX + ACTIVE_VIBRATION, false);
    }


    /// SETTERS ////////////////////////////////////////////////////////////////////////////////////



    public void setTimeout( int value )
    {
        write.putInt(PREFIX + TIMEOUT, value);
        write.commit();
    }



    public void setActiveSound( boolean value )
    {
        write.putBoolean(PREFIX + ACTIVE_SOUND, value);
        write.commit();
    }



    public void setActiveVibration( boolean value )
    {
        write.putBoolean(PREFIX + ACTIVE_VIBRATION, value);
        write.commit();
    }

}
