package com.movilizer.client.android.handler.util.preferences;


import android.content.Context;

import java.security.InvalidParameterException;
import java.util.Map;



/**
 * Created by Alberto Gil Tesa
 */
@SuppressWarnings({"UnusedDeclaration"})
public class PreferencesNotification extends Preferences
{
    private final String PREFIX = "TIMER_";



    /**
     * @param context
     */
    public PreferencesNotification( Context context )
    {
        super(context);
    }



    public PreferencesNotification( Context context, Map<String, Object> properties ) throws InvalidParameterException
    {
        super(context);
    }

    /// GETTERS ////////////////////////////////////////////////////////////////////////////////////


    /// SETTERS ////////////////////////////////////////////////////////////////////////////////////



}
