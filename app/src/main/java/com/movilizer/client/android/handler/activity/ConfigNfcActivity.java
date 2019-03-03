package com.movilizer.client.android.handler.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.movilizer.client.android.handler.R;
import com.movilizer.client.android.handler.util.preferences.PreferencesNfc;



public class ConfigNfcActivity extends AppCompatActivity
{
    private PreferencesNfc pref;
    private CheckBox       cbActiveSound;
    private CheckBox       cbActiveVibration;



    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_nfc);


        pref = new PreferencesNfc(this);


        cbActiveSound = findViewById(R.id.active_sound);
        cbActiveSound.setChecked(pref.isActiveSound());
        cbActiveSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged( CompoundButton buttonView, boolean isChecked )
            {
                pref.setActiveSound(isChecked);
            }
        });


        cbActiveVibration = findViewById(R.id.active_vibration);
        cbActiveVibration.setChecked(pref.isActiveVibration());
        cbActiveVibration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged( CompoundButton buttonView, boolean isChecked )
            {
                pref.setActiveVibration(isChecked);
            }
        });
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

}
