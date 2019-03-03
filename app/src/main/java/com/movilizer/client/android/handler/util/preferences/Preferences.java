package com.movilizer.client.android.handler.util.preferences;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import com.movilizer.client.android.handler.R;
import com.movilizer.client.android.handler.activity.HomeActivity;
import com.movilizer.client.android.handler.activity.MovilizerDialogActivity;
import com.movilizer.client.android.handler.util.TryParse;

import java.security.InvalidParameterException;
import java.util.Map;



/**
 * Created by Alberto Gil Tesa
 */
@SuppressWarnings({"UnusedDeclaration"})
public class Preferences
{
    protected final     Context                  context;
    protected final     SharedPreferences        read;
    protected final     SharedPreferences.Editor write;
    //
    private final       String                   PREFIX             = "APP_";
    private final       String                   MOVILIZER_CLIENT   = "MOVILIZER_CLIENT";
    protected final     String                   EVENT_ID           = "EVENT_ID";
    protected final     String                   EVENT_TYPE         = "EVENT_TYPE";
    private final       String                   OPEN_APP           = "OPEN_APP";
    private final       String                   HIDE_GUI           = "HIDE_GUI";
    //
    protected final     String                   NULLABLE_STR_VALUE = "";
    protected final     String                   NULLABLE_INT_VALUE = "0";
    //
    public final static String                   LOG_TAG            = "MOVILIZER_HANDLER";



    /**
     * @param context
     */
    public Preferences( Context context )
    {
        this.context = context;
        this.read = PreferenceManager.getDefaultSharedPreferences(context);
        this.write = read.edit();
    }



    /**
     * @param context
     * @param properties
     *
     * @throws InvalidParameterException
     */
    public Preferences( Context context, Map<String, Object> properties ) throws InvalidParameterException
    {
        this(context);


        // OPEN_APP (true/false)
        //
        if( TryParse.tryParseBoolean(properties.get(OPEN_APP), false) )
        {
            context.startActivity(new Intent(context, HomeActivity.class));
        }


        // HIDE_GUI (true/false)
        //
        boolean hideGUI = TryParse.tryParseBoolean(properties.get(HIDE_GUI), false);
        if( !hideGUI && isHideGUI() )
        {
            MovilizerDialogActivity.showInfo(context, R.string.conf_sound_info, R.string.conf_incognit_gui_enabled, R.string.movilizer_dialog_ok, 0);
            setHideGUI(hideGUI);
        }
        else if( hideGUI && !isHideGUI() )
        {
            setHideGUI(hideGUI);
        }

    }


    // GETTERS ////////////////////////////////////////////////////////////////////////////////////



    public final String getMovilizerClient()
    {
        return read.getString(PREFIX + MOVILIZER_CLIENT, NULLABLE_STR_VALUE);
    }



    public int getEventID()
    {
        return Integer.parseInt(read.getString(PREFIX + EVENT_ID, NULLABLE_INT_VALUE));
    }



    /**
     * 0 = Synchronous (default)
     * 1 = Asynchronous Guaranteed
     * 2 = Asynchronous
     *
     * @return
     */
    public int getEventType()
    {
        return Integer.valueOf(read.getString(PREFIX + EVENT_TYPE, "0"));
    }



    public final boolean isHideGUI()
    {
        return read.getBoolean(PREFIX + HIDE_GUI, false);
    }


    /// SETTERS ////////////////////////////////////////////////////////////////////////////////////



    public final void setMovilizerClient( String value )
    {
        write.putString(PREFIX + MOVILIZER_CLIENT, value);
        write.commit();
    }



    public void setEventID( String value )
    {
        write.putString(PREFIX + EVENT_ID, value);
        write.commit();
    }



    public void setEventID( int value )
    {
        setEventID(String.valueOf(value));
    }



    public void setEventType( String value )
    {
        write.putString(PREFIX + EVENT_TYPE, value);
        write.commit();
    }



    public void setEventType( int value )
    {
        setEventType(String.valueOf(value));
    }



    public final void setHideGUI( boolean value )
    {
        write.putBoolean(PREFIX + HIDE_GUI, value);
        write.commit();
    }


    /// OTHERS ////////////////////////////////////////////////////////////////////////////////////



    /**
     * @return
     */
    public final String getMovilizerClientName()
    {
        String result = null;

        try
        {
            if( context instanceof Activity )
            {
                Activity activity   = (Activity) context;
                Uri      packageUri = activity.getReferrer();

                if( packageUri != null )
                {
                    String          packageName = packageUri.getHost();
                    PackageManager  pm          = activity.getPackageManager();
                    ApplicationInfo ai          = pm.getApplicationInfo(packageName, 0);
                    PackageInfo     pInfo       = pm.getPackageInfo(packageName, 0);

                    result = pm.getApplicationLabel(ai) + " " + pInfo.versionName;
                }
            }
        }
        catch( PackageManager.NameNotFoundException e )
        {
            Log.e(PreferencesScanner.LOG_TAG, "Error PackageManager", e);
        }

        return result;
    }



    /**
     * @param context
     * @param properties
     *
     * @return
     *
     * @throws Exception
     */
    public static final Preferences getPreferencesInstance( Context context, Map<String, Object> properties ) throws InvalidParameterException
    {
        if( properties == null || properties.size() == 0 )
            throw new InvalidParameterException(context.getResources().getString(R.string.pref_missing_input_data));


        String handler = (String) properties.get("HANDLER");

        if( handler == null || handler.trim().isEmpty() )
            throw new InvalidParameterException(context.getResources().getString(R.string.pref_parameter_mandatory).replace("#1", "HANDLER"));


        if( handler.equalsIgnoreCase("APP") )
        {
            return new Preferences(context, properties);
        }
        else if( handler.equalsIgnoreCase("SCANNER") )
        {
            return new PreferencesScanner(context, properties);
        }
        else if( handler.equalsIgnoreCase("NFC") )
        {
            return new PreferencesNfc(context, properties);
        }
        else if( handler.equalsIgnoreCase("SOUND") )
        {
            return new PreferencesSound(context, properties);
        }
        else if( handler.equalsIgnoreCase("VIBRATION") )
        {
            return new PreferencesVibration(context, properties);
        }
        else if( handler.equalsIgnoreCase("TIMER") )
        {
            return new PreferencesTimer(context, properties);
        }
        else if( handler.equalsIgnoreCase("NOTIFICATION") )
        {
            return new PreferencesNotification(context, properties);
        }
        else
        {
            throw new InvalidParameterException(context.getResources().getString(R.string.pref_handler_not_supported).replace("#1", handler));
        }
    }



    /**
     * @param properties
     *
     * @return
     */
    protected int readEventID( Map<String, Object> properties )
    {
        String eventIDStr = TryParse.tryParseString(properties.get(EVENT_ID), "");

        if( eventIDStr.isEmpty() )
            throw new InvalidParameterException(context.getResources().getString(R.string.pref_parameter_mandatory).replace("#1", EVENT_ID));

        int eventID = TryParse.tryParseInteger(eventIDStr, -1);

        if( eventID == -1 )
            throw new InvalidParameterException(context.getResources().getString(R.string.pref_parameter_not_valid).replace("#1", EVENT_ID).replace("#2", eventIDStr));

        return eventID;
    }


}
