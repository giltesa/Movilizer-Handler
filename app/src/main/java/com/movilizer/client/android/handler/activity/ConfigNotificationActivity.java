package com.movilizer.client.android.handler.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.movilizer.client.android.handler.R;



public class ConfigNotificationActivity extends AppCompatActivity
{


    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_notification);
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
