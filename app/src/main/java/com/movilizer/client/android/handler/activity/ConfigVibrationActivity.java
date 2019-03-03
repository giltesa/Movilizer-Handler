package com.movilizer.client.android.handler.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.SeekBar;

import com.movilizer.client.android.handler.R;
import com.movilizer.client.android.handler.util.preferences.PreferencesVibration;
import com.movilizer.client.android.handler.util.Vibrator;



public class ConfigVibrationActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener
{
    private PreferencesVibration pref;
    private Vibrator             vibrator;
    private SeekBar              barInfo;
    private SeekBar              barWarning;
    private SeekBar              barError;
    private SeekBar              barFatalError;
    private SeekBar              barSuccessScan;
    private SeekBar              barErrorScan;
    private int                  oldValue;



    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_vibration);


        pref = new PreferencesVibration(this);
        vibrator = new Vibrator(this, pref);


        barInfo = findViewById(R.id.vibration_info);
        barInfo.setMax(PreferencesVibration.VIBRATION_LEVELS);
        barInfo.setProgress(pref.getVibrationInfo());
        barInfo.setOnSeekBarChangeListener(this);


        barWarning = findViewById(R.id.vibration_warning);
        barWarning.setMax(PreferencesVibration.VIBRATION_LEVELS);
        barWarning.setProgress(pref.getVibrationWarning());
        barWarning.setOnSeekBarChangeListener(this);

        barError = findViewById(R.id.vibration_error);
        barError.setMax(PreferencesVibration.VIBRATION_LEVELS);
        barError.setProgress(pref.getVibrationError());
        barError.setOnSeekBarChangeListener(this);

        barFatalError = findViewById(R.id.vibration_fatal_error);
        barFatalError.setMax(PreferencesVibration.VIBRATION_LEVELS);
        barFatalError.setProgress(pref.getVibrationFatalError());
        barFatalError.setOnSeekBarChangeListener(this);

        barSuccessScan = findViewById(R.id.vibration_success_scan);
        barSuccessScan.setMax(PreferencesVibration.VIBRATION_LEVELS);
        barSuccessScan.setProgress(pref.getVibrationSucessScan());
        barSuccessScan.setOnSeekBarChangeListener(this);

        barErrorScan = findViewById(R.id.vibration_error_scan);
        barErrorScan.setMax(PreferencesVibration.VIBRATION_LEVELS);
        barErrorScan.setProgress(pref.getVibrationErrorScan());
        barErrorScan.setOnSeekBarChangeListener(this);
    }



    /**
     *
     */
    @Override
    public boolean onOptionsItemSelected( MenuItem item )
    {
        switch( item.getItemId() )
        {
            case android.R.id.home:
                finish();
                break;

            default:
                break;
        }
        return true;
    }



    @Override
    public void onStartTrackingTouch( SeekBar seekBar )
    {
        oldValue = seekBar.getProgress();
    }



    @Override
    public void onProgressChanged( SeekBar seekBar, int i, boolean b )
    {
        vibrator.vibrate(seekBar.getProgress() * PreferencesVibration.VIBRATION_MULTIPLIER);
    }



    @Override
    public void onStopTrackingTouch( SeekBar seekBar )
    {
        int newValue = seekBar.getProgress();

        if( oldValue == newValue )
        {
            vibrator.vibrate(newValue * PreferencesVibration.VIBRATION_MULTIPLIER);
        }
        else
        {
            if( seekBar == barInfo )
            {
                pref.setVibrationInfo(seekBar.getProgress());
            }
            else if( seekBar == barWarning )
            {
                pref.setVibrationWarning(seekBar.getProgress());
            }
            else if( seekBar == barError )
            {
                pref.setVibrationError(seekBar.getProgress());
            }
            else if( seekBar == barFatalError )
            {
                pref.setVibrationFatalError(seekBar.getProgress());
            }
            else if( seekBar == barSuccessScan )
            {
                pref.setVibrationSucessScan(seekBar.getProgress());
            }
            else if( seekBar == barErrorScan )
            {
                pref.setVibrationErrorScan(seekBar.getProgress());
            }
        }
    }
}
