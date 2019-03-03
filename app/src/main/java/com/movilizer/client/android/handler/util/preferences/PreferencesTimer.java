package com.movilizer.client.android.handler.util.preferences;


import android.content.Context;

import com.movilizer.client.android.handler.R;
import com.movilizer.client.android.handler.util.TryParse;

import java.security.InvalidParameterException;
import java.util.Map;



/**
 * Created by Alberto Gil Tesa
 */
@SuppressWarnings({"UnusedDeclaration"})
public class PreferencesTimer extends Preferences
{
    private final String PREFIX = "TIMER_";
    //Parameters from MEL:
    private final String TASK   = "TASK";
    private final String REPEAT = "REPEAT";



    /**
     * @param context
     */
    public PreferencesTimer( Context context )
    {
        super(context);
    }



    public PreferencesTimer( Context context, Map<String, Object> properties ) throws InvalidParameterException
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
        setEventType(TryParse.tryParseInteger(properties.get(EVENT_TYPE), 0));


        // REPEAT (Numbers seconds)
        //
        setRepeat(TryParse.tryParseInteger(properties.get(REPEAT), 1));
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



    public int getRepeat()
    {
        return read.getInt(PREFIX + REPEAT, 1);
    }


    /// SETTERS ////////////////////////////////////////////////////////////////////////////////////



    public void setTask( String value )
    {
        write.putString(PREFIX + TASK, value);
        write.commit();
    }



    public void setEventID( int value )
    {
        setEventID(String.valueOf(value));
    }



    public void setRepeat( int value )
    {
        write.putInt(PREFIX + REPEAT, value);
        write.commit();
    }



}
