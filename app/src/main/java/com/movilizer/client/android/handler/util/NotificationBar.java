package com.movilizer.client.android.handler.util;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat.Builder;

import com.movilizer.client.android.handler.R;



/**
 *
 */
public class NotificationBar
{
    private static       NotificationManager notifyMgr;
    private static final int                 notificationID = 0;



    /**
     * @param context
     * @param title
     * @param description
     * @param intent
     */
    public static void create( Context context, String title, String description, Intent intent )
    {
        Builder builder = new Builder(context, String.valueOf(notificationID));
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(title);
        builder.setContentText(description);
        //builder.setTicker(tickerText);
        builder.setContentIntent(PendingIntent.getActivity(context, notificationID, intent, PendingIntent.FLAG_UPDATE_CURRENT));
        builder.setAutoCancel(false);
        builder.setOnlyAlertOnce(true);
        builder.setOngoing(true);

        notifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notifyMgr.notify(notificationID, builder.build());
    }



    /**
     *
     */
    public static void cancel()
    {
        notifyMgr.cancel(notificationID);
    }
}