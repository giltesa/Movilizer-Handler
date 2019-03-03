package com.movilizer.client.android.handler.activity;


import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.movilizer.client.android.handler.R;
import com.movilizer.client.android.handler.service.ScannerService_S1;
import com.movilizer.client.android.handler.service.SoundService;
import com.movilizer.client.android.handler.service.TimerService;
import com.movilizer.client.android.handler.util.Permission;
import com.movilizer.client.android.handler.util.preferences.Preferences;
import com.movilizer.client.android.handler.util.preferences.PreferencesNfc;
import com.movilizer.client.android.handler.util.preferences.PreferencesNotification;
import com.movilizer.client.android.handler.util.preferences.PreferencesScanner;
import com.movilizer.client.android.handler.util.preferences.PreferencesSound;
import com.movilizer.client.android.handler.util.preferences.PreferencesTimer;
import com.movilizer.client.android.handler.util.preferences.PreferencesVibration;
import com.movilizer.client.android.handler.util.Vibrator;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * Created by Alberto Gil Tesa on 06/03/2018.
 */
public class MainActivity extends AppCompatActivity
{


    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);

        Preferences pref     = new Preferences(this);
        Vibrator    vibrator = new Vibrator(this);


        // Execute the service or graphic interface depending on whether or not there are input parameters.
        Intent moviIntent = getIntent();

        if( moviIntent == null || !Intent.ACTION_VIEW.equals(moviIntent.getAction()) )
        {
            if( pref.isHideGUI() )
                Toast.makeText(this, getText(R.string.conf_incognit_gui_disabled), Toast.LENGTH_SHORT).show();
            else
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }
        else
        {
            try
            {
                String              json       = moviIntent.getDataString();
                Map<String, Object> properties = new Gson().fromJson(json, new TypeToken<HashMap<String, Object>>() {}.getType());

                pref = Preferences.getPreferencesInstance(this, properties);
            }
            catch( InvalidParameterException ex )
            {
                Log.e(Preferences.LOG_TAG, "Error input parameters", ex);
                vibrator.error();
                MovilizerDialogActivity.showError(this, R.string.movilizer_dialog_error, ex.getMessage(), R.string.movilizer_dialog_cancel, 0);
            }
            catch( Exception ex )
            {
                Log.e(Preferences.LOG_TAG, "Error input parameters", ex);
                vibrator.error();
                MovilizerDialogActivity.showError(this, R.string.movilizer_dialog_error, R.string.movilizer_dialog_fatal_error, R.string.movilizer_dialog_cancel, 0);
            }


            if( pref != null )
            {
                pref.setMovilizerClient(getReferrer().getHost()); //NOTE: This line requires have Android SDK 22...

                if( pref instanceof PreferencesScanner )
                {
                    runScannerHandler((PreferencesScanner) pref);
                }
                else if( pref instanceof PreferencesNfc )
                {
                    runNfcHandler((PreferencesNfc) pref);
                }
                else if( pref instanceof PreferencesSound )
                {
                    runSoundHandler((PreferencesSound) pref);
                }
                else if( pref instanceof PreferencesVibration )
                {
                    runVibrationHandler((PreferencesVibration) pref);
                }
                else if( pref instanceof PreferencesTimer )
                {
                    runTimerHandler((PreferencesTimer) pref);
                }
                else if( pref instanceof PreferencesNotification )
                {
                    runNotificationHandler((PreferencesNotification) pref);
                }
                else if( pref instanceof Preferences ) //App
                {
                    runAppHandler(pref);
                }
            }
        }

        finish();
    }



    /**
     * @param pref
     */
    private void runAppHandler( Preferences pref )
    {
    }



    /**
     * @param pref
     */
    private void runScannerHandler( PreferencesScanner pref )
    {
        //Execute the appropriate service for each SDK / device.
        Class<?> cls    = null;
        Intent   intent = null;

        if( ScannerService_S1.SDK.equals(pref.getDeviceSdkGroup()) )
        {
            cls = ScannerService_S1.class;
            intent = new Intent(this, cls);
        }
        else if( false )
        {
            //Add here as many "else if" as you need to support other scanner SDKs.
        }


        if( intent == null )
        {
            MovilizerDialogActivity.showError(this, R.string.movilizer_dialog_error, R.string.movilizer_dialog_impossible_run_scanner_service, R.string.movilizer_dialog_cancel, 0);
        }
        else
        {
            if( pref.isStartTask() && !isMyServiceRunning(cls) )
            {
                startService(intent);
            }
            else if( !pref.isStartTask() )
            {
                stopService(intent);
            }
        }
    }



    /**
     * @param pref
     */
    private void runNfcHandler( PreferencesNfc pref )
    {
        MovilizerDialogActivity.showNFC(this, R.string.movilizer_dialog_nfc, R.string.movilizer_dialog_nfc_message, R.string.movilizer_dialog_cancel, pref.getTimeout());
    }



    /**
     * @param pref
     */
    private void runSoundHandler( PreferencesSound pref )
    {
        Uri uri = pref.getSound(pref.getPlaySound());

        if( uri != null )
        {
            boolean isExtResource   = uri.getPath().startsWith("/external/audio");
            boolean isExtPermission = false;

            if( isExtResource )
            {
                isExtPermission = Permission.isStoragePermissionGranted(this);
            }

            if( !isExtResource || isExtPermission )
            {
                Intent intent = new Intent(this, SoundService.class);
                intent.putExtra("uri", uri.toString());
                startService(intent);
            }
        }
    }



    /**
     * @param pref
     */
    private void runVibrationHandler( PreferencesVibration pref )
    {
    }



    /**
     * @param pref
     */
    private void runTimerHandler( PreferencesTimer pref )
    {
        Class<?> cls    = TimerService.class;
        Intent   intent = new Intent(this, cls);

        if( pref.isStartTask() && !isMyServiceRunning(cls) )
        {
            startService(intent);
        }
        else if( !pref.isStartTask() )
        {
            stopService(intent);
        }
    }



    /**
     * @param pref
     */
    private void runNotificationHandler( PreferencesNotification pref )
    {
    }



    /**
     * https://stackoverflow.com/questions/600207/how-to-check-if-a-service-is-running-on-android/5921190#5921190
     *
     * @param serviceClass
     *
     * @return
     */
    private boolean isMyServiceRunning( Class<?> serviceClass )
    {
        ActivityManager                          manager  = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> services = manager.getRunningServices(Integer.MAX_VALUE);

        for( ActivityManager.RunningServiceInfo service : services )
        {
            if( serviceClass.getName().equals(service.service.getClassName()) )
            {
                return true;
            }
        }
        return false;
    }

}
