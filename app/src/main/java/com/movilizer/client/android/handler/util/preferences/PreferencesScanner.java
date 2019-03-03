package com.movilizer.client.android.handler.util.preferences;


import android.content.Context;

import com.movilizer.client.android.handler.R;
import com.movilizer.client.android.handler.util.BarcodeType;
import com.movilizer.client.android.handler.util.TryParse;

import java.security.InvalidParameterException;
import java.util.Map;



/**
 * Created by Alberto Gil Tesa
 */
@SuppressWarnings({"UnusedDeclaration"})
public class PreferencesScanner extends Preferences
{
    private final String PREFIX           = "SCAN_";
    //Parameters from MEL:
    private final String TASK             = "TASK";
    private final String DRIVER_SOUNDS    = "DRIVER_SOUNDS";
    private final String BARCODE_TYPES    = "BARCODE_TYPES";
    //Parameters from MEL & APP UI:
    private final String ACTIVE_SOUND     = "ACTIVE_SOUND";
    private final String ACTIVE_VIBRATION = "ACTIVE_VIBRATION";
    //Parameters from APP UI:
    private final String DEVICE_MODEL     = "DEVICE_MODEL";
    private final String DEVICE_SDK_GROUP = "DEVICE_SDK_GROUP";



    /**
     * @param context
     */
    public PreferencesScanner( Context context )
    {
        super(context);
    }



    /**
     * @param context
     * @param properties
     *
     * @throws InvalidParameterException
     */
    public PreferencesScanner( Context context, Map<String, Object> properties ) throws InvalidParameterException
    {
        super(context);


        // TASK (START/STOP)
        //
        String task = TryParse.tryParseString(properties.get(TASK), "");
        if( !task.equalsIgnoreCase("START") && !task.equalsIgnoreCase("STOP") )
            throw new InvalidParameterException(context.getResources().getString(R.string.pref_parameter_not_valid).replace("#1", TASK).replace("#2", task));
        setTask(task);


        // EVENT_ID (Number)
        //
        if( task.equalsIgnoreCase("START") )
            setEventID(readEventID(properties));


        // EVENT_TYPE (Number: 0=Synchronous(default) | 1=Asynchronous Guaranteed | 2=Asynchronous)
        //
        setEventType(TryParse.tryParseString(properties.get(EVENT_TYPE), "0"));


        // DRIVER_SOUNDS (true/false)
        //
        setDriverSounds(TryParse.tryParseBoolean(properties.get(DRIVER_SOUNDS), false));


        // BARCODE_TYPES (Map<String, Object>)
        //
        //setBarcodeTypes(TryParse.tryParseString(BARCODE_TYPES, "")); //This contains another json //REVISAR


        // SDK GROUP (GUI)
        //
        String deviceSdkGroup = getDeviceSdkGroup();
        if( deviceSdkGroup == null || deviceSdkGroup.isEmpty() )
            throw new InvalidParameterException(context.getResources().getString(R.string.movilizer_dialog_device_field_missing));


        // ACTIVE_SOUND (true/false)
        //
        setActiveSound(TryParse.tryParseBoolean(properties.get(ACTIVE_SOUND), isActiveSound()));


        // ACTIVE_VIBRATION (true/false)
        //
        setActiveVibration(TryParse.tryParseBoolean(properties.get(ACTIVE_VIBRATION), isActiveVibration()));
    }


    /// GETTERS ////////////////////////////////////////////////////////////////////////////////////



    public String getTask()
    {
        return read.getString(PREFIX + TASK, NULLABLE_STR_VALUE);
    }



    public boolean isStartTask()
    {
        return getTask().equalsIgnoreCase("START");
    }



    public boolean getDriverSounds()
    {
        return read.getBoolean(PREFIX + DRIVER_SOUNDS, false);
    }



    private String _getBarcodeTypes()
    {
        return read.getString(PREFIX + BARCODE_TYPES, "");
    }



    public Map<String, BarcodeType> getBarcodeTypes()
    {
        return BarcodeType.getBarcodeTypesList(_getBarcodeTypes());
    }



    public String getDeviceModel()
    {
        return read.getString(PREFIX + DEVICE_MODEL, NULLABLE_STR_VALUE);
    }



    public String getDeviceSdkGroup()
    {
        return read.getString(PREFIX + DEVICE_SDK_GROUP, NULLABLE_STR_VALUE);
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



    public void setTask( String value )
    {
        write.putString(PREFIX + TASK, value);
        write.commit();
    }



    public void setEventType( int value )
    {
        setEventType(String.valueOf(value));
    }



    public void setDriverSounds( boolean value )
    {
        write.putBoolean(PREFIX + DRIVER_SOUNDS, value);
        write.commit();
    }



    public void setBarcodeTypes( String value )
    {
        write.putString(PREFIX + BARCODE_TYPES, value);
        write.commit();
    }



    public void setDeviceModel( String value )
    {
        write.putString(PREFIX + DEVICE_MODEL, value);
        write.commit();
    }



    public void setDeviceSdkGroup( String value )
    {
        write.putString(PREFIX + DEVICE_SDK_GROUP, value);
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
