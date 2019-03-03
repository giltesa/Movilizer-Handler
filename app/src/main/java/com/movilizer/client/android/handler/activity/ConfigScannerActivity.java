package com.movilizer.client.android.handler.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

import com.movilizer.client.android.handler.R;
import com.movilizer.client.android.handler.adapters.SpinnerAdapter;
import com.movilizer.client.android.handler.models.SpinnerRow;
import com.movilizer.client.android.handler.service.ScannerService_S1;
import com.movilizer.client.android.handler.util.preferences.PreferencesScanner;

import java.util.ArrayList;
import java.util.Collections;



public class ConfigScannerActivity extends AppCompatActivity
{
    private PreferencesScanner pref;
    private Spinner            devModelSpinner;
    private CheckBox           cbActiveSound;
    private CheckBox           cbActiveVibration;



    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_scanner);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        pref = new PreferencesScanner(this);


        ///////////////////////////////////////////////////////
        // Add the values for the dropdown of Device Model:
        //
        devModelSpinner = findViewById(R.id.device_model_spinner);
        ArrayList<SpinnerRow> devModelList = new ArrayList<>();

        //Recover compatible SDK devices, if you add more SDKs, return them in the same way to add them to the interface.
        devModelList.addAll(ScannerService_S1.getSupportedDevices());

        Collections.sort(devModelList);

        SpinnerAdapter devModelAdapter = new SpinnerAdapter(this, devModelList);
        devModelAdapter.setDropDownViewResource(R.layout.view_spinner);
        devModelSpinner.setAdapter(devModelAdapter);

        int position = devModelAdapter.getPositionFromValue(pref.getDeviceModel());
        if( position >= 0 )
        {
            devModelSpinner.setSelection(position);
        }


        devModelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected( AdapterView<?> parentView, View selectedItemView, int position, long id )
            {
                SpinnerRow devModelItem = (SpinnerRow) parentView.getSelectedItem();
                if( devModelItem != null )
                {
                    pref.setDeviceModel(devModelItem.getValue());
                    pref.setDeviceSdkGroup(devModelItem.getValueAux());
                }
            }



            @Override
            public void onNothingSelected( AdapterView<?> parentView )
            {
            }

        });


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
