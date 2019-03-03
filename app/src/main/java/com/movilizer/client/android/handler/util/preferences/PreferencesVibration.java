package com.movilizer.client.android.handler.util.preferences;


import android.content.Context;

import java.security.InvalidParameterException;
import java.util.Map;



/**
 * Created by Alberto Gil Tesa
 */
@SuppressWarnings({"UnusedDeclaration"})
public class PreferencesVibration extends Preferences
{
    private final       String PREFIX                     = "VIBRATION_";
    //Parameters from APP UI:
    private final       String VIBRATION_INFO         = PREFIX + "INFO";
    private final       String VIBRATION_WARNING      = PREFIX + "WARNING";
    private final       String VIBRATION_ERROR        = PREFIX + "ERROR";
    private final       String VIBRATION_FATAL_ERROR  = PREFIX + "FATAL_ERROR";
    private final       String VIBRATION_SUCCESS_SCAN = PREFIX + "SUCCESS_SCAN";
    private final       String VIBRATION_ERROR_SCAN   = PREFIX + "ERROR_SCAN";
    //
    public final static int    VIBRATION_LEVELS           = 15;
    public final static int    VIBRATION_MULTIPLIER       = 100;     //Milliseconds



    /**
     * @param context
     */
    public PreferencesVibration( Context context )
    {
        super(context);
    }



    public PreferencesVibration( Context context, Map<String, Object> properties ) throws InvalidParameterException
    {
        super(context);
    }


    /// GETTERS ////////////////////////////////////////////////////////////////////////////////////



    public int getVibrationInfo()
    {
        int value = Integer.parseInt(read.getString(PREFIX +VIBRATION_INFO, NULLABLE_INT_VALUE));

        if( value > VIBRATION_LEVELS )
        {
            value = VIBRATION_LEVELS;
            setVibrationInfo(value);
        }

        return value;
    }



    public int getVibrationWarning()
    {
        int value = Integer.parseInt(read.getString(PREFIX +VIBRATION_WARNING, NULLABLE_INT_VALUE));

        if( value > VIBRATION_LEVELS )
        {
            value = VIBRATION_LEVELS;
            setVibrationWarning(value);
        }

        return value;
    }



    public int getVibrationError()
    {
        int value = Integer.parseInt(read.getString(PREFIX +VIBRATION_ERROR, NULLABLE_INT_VALUE));

        if( value > VIBRATION_LEVELS )
        {
            value = VIBRATION_LEVELS;
            setVibrationError(value);
        }

        return value;
    }



    public int getVibrationFatalError()
    {
        int value = Integer.parseInt(read.getString(PREFIX +VIBRATION_FATAL_ERROR, NULLABLE_INT_VALUE));

        if( value > VIBRATION_LEVELS )
        {
            value = VIBRATION_LEVELS;
            setVibrationFatalError(value);
        }

        return value;
    }



    public int getVibrationSucessScan()
    {
        int value = Integer.parseInt(read.getString(PREFIX +VIBRATION_SUCCESS_SCAN, NULLABLE_INT_VALUE));

        if( value > VIBRATION_LEVELS )
        {
            value = VIBRATION_LEVELS;
            setVibrationSucessScan(value);
        }

        return value;
    }



    public int getVibrationErrorScan()
    {
        int value = Integer.parseInt(read.getString(PREFIX +VIBRATION_ERROR_SCAN, NULLABLE_INT_VALUE));

        if( value > VIBRATION_LEVELS )
        {
            value = VIBRATION_LEVELS;
            setVibrationErrorScan(value);
        }

        return value;
    }


    /// SETTERS ////////////////////////////////////////////////////////////////////////////////////



    public void setVibrationInfo( int value )
    {
        write.putString(PREFIX +VIBRATION_INFO, String.valueOf(value));
        write.commit();
    }



    public void setVibrationWarning( int value )
    {
        write.putString(PREFIX +VIBRATION_WARNING, String.valueOf(value));
        write.commit();
    }



    public void setVibrationError( int value )
    {
        write.putString(PREFIX +VIBRATION_ERROR, String.valueOf(value));
        write.commit();
    }



    public void setVibrationFatalError( int value )
    {
        write.putString(PREFIX +VIBRATION_FATAL_ERROR, String.valueOf(value));
        write.commit();
    }



    public void setVibrationSucessScan( int value )
    {
        write.putString(PREFIX +VIBRATION_SUCCESS_SCAN, String.valueOf(value));
        write.commit();
    }



    public void setVibrationErrorScan( int value )
    {
        write.putString(PREFIX +VIBRATION_ERROR_SCAN, String.valueOf(value));
        write.commit();
    }

}
