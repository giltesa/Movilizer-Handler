package com.movilizer.client.android.handler.util;


import android.content.Context;

import com.movilizer.client.android.handler.util.preferences.PreferencesVibration;



/**
 * Created by Alberto Gil Tesa on 13/03/2018.
 */
public class Vibrator
{
    private static Context              context;
    private static PreferencesVibration pref;




    public Vibrator( Context context )
    {
        this(context, new PreferencesVibration(context));
    }



    public Vibrator( Context context, PreferencesVibration pref )
    {
        this.context = context;
        this.pref = pref;
    }



    public void success()
    {
        vibrate(pref.getVibrationSucessScan() * PreferencesVibration.VIBRATION_MULTIPLIER);
    }



    public void error()
    {
        vibrate(pref.getVibrationErrorScan() * PreferencesVibration.VIBRATION_MULTIPLIER);
    }



    public void vibrate( int milliseconds )
    {
        ((android.os.Vibrator) context.getSystemService(context.VIBRATOR_SERVICE)).vibrate(milliseconds);
    }
}
