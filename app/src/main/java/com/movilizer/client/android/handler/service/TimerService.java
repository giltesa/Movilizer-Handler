package com.movilizer.client.android.handler.service;


import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import com.movilizer.client.android.handler.R;
import com.movilizer.client.android.handler.util.Movilizer;
import com.movilizer.client.android.handler.util.NotificationBar;
import com.movilizer.client.android.handler.util.preferences.PreferencesTimer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;



/**
 * Created by Alberto Gil Tesa
 */
public class TimerService extends Service
{
    private Movilizer           movilizer;
    private Context             context;
    private PreferencesTimer    pref;
    private TimerTask           timerTask           = null;
    private NotificationManager notificationManager = null;



    /**
     * A constructor of Class
     */
    public TimerService()
    {
        context = this;
    }



    /**
     *
     */
    @Override
    public void onCreate()
    {
        super.onCreate();

        movilizer = new Movilizer(context, new Handler());
        pref = new PreferencesTimer(context);


        NotificationBar.create(context, getString(R.string.notifbar_sound_service_active), null, new Intent(context, TimerService.class));


        int repeat = pref.getRepeat() * 1000;

        if( timerTask == null )
        {
            timerTask = new TimerTask()
            {
                @Override
                public void run()
                {
                    Map<String, Object> values = new HashMap<>();
                    values.put("TIMESTAMP", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    movilizer.doSendMessage(values);
                }
            };
        }
        new Timer().schedule(timerTask, 0, repeat);
    }



    /**
     * @param intent
     * @param flags
     * @param startId
     *
     * @return
     */
    @Override
    public int onStartCommand( Intent intent, int flags, int startId )
    {
        return super.onStartCommand(intent, flags, startId); //START_NOT_STICKY;
    }



    /**
     * @param intent
     *
     * @return
     */
    @Override
    public IBinder onBind( Intent intent )
    {
        return null;
    }



    /**
     *
     */
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        timerTask.cancel();
        NotificationBar.cancel();
    }


}