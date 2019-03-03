package com.movilizer.client.android.handler.service;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore;



/**
 * Created by Alberto Gil Tesa
 */
public class SoundService extends Service
{


    /**
     *
     */
    @Override
    public void onCreate()
    {
        super.onCreate();
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
        Uri uri = Uri.parse(intent.getExtras().getString("uri"));

        if( uri == null )
        {
            stopSelf();
        }
        else
        {
            MediaPlayer mp = new MediaPlayer();
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
            {
                @Override
                public void onCompletion( MediaPlayer mp )
                {
                    mp.release();
                    stopSelf();
                }
            });

            try
            {
                mp.setDataSource(getRealPathFromURI(this, uri));
                mp.prepare();
                mp.start();
            }
            catch( Exception ex )
            {
                ex.printStackTrace();
            }
        }

        return START_NOT_STICKY;
    }



    /**
     * Get absolute path from uri resource.
     * https://stackoverflow.com/a/28615533/10840871
     *
     * @param context
     * @param uri
     *
     * @return
     */
    private String getRealPathFromURI( Context context, Uri uri )
    {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        int    idx;

        if( uri.getPath().startsWith("/external/audio") || uri.getPath().startsWith("/internal/audio") )
        {
            idx = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA);
        }
        else if( uri.getPath().startsWith("/external/image") || uri.getPath().startsWith("/internal/image") )
        {
            idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        }
        else if( uri.getPath().startsWith("/external/video") || uri.getPath().startsWith("/internal/video") )
        {
            idx = cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA);
        }
        else
        {
            return uri.getPath();
        }

        if( cursor != null && cursor.moveToFirst() )
        {
            return cursor.getString(idx);
        }

        return null;
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
    }


}