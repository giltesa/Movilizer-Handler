package com.movilizer.client.android.handler.util;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;



/**
 * Created by Alberto Gil Tesa
 */
public class Permission
{


    /**
     *
     */
    private Permission()
    {

    }



    /**
     * Check external storage permission.
     * https://stackoverflow.com/a/36347338/10840871
     *
     * @return
     */
    public static boolean isStoragePermissionGranted( Activity context )
    {
        if( Build.VERSION.SDK_INT >= 23 )
        {
            if( context.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED )
            {
                return true;
            }
            else
            {
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else
        {
            //Permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }
}
