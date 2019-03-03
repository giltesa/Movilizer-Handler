package com.movilizer.client.android.handler.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.movilizer.client.android.handler.R;



public class HomeActivity extends AppCompatActivity implements View.OnClickListener
{
    private final int RESULT_INCOGNIT_MODE = 0;



    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        Log.i("AGIL", "onCreate");

        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        findViewById(R.id.home_btn_scanner).setOnClickListener(this);
        findViewById(R.id.home_btn_nfc).setOnClickListener(this);
        findViewById(R.id.home_btn_sound).setOnClickListener(this);
        findViewById(R.id.home_btn_vibration).setOnClickListener(this);
        findViewById(R.id.home_btn_timer).setOnClickListener(this);
        findViewById(R.id.home_btn_notification).setOnClickListener(this);
        findViewById(R.id.home_btn_incognit).setOnClickListener(this);
    }



    /**
     * @param view
     */
    @Override
    public void onClick( View view )
    {
        switch( view.getId() )
        {
            case R.id.home_btn_scanner:
                startActivity(new Intent(getApplicationContext(), ConfigScannerActivity.class));
                break;

            case R.id.home_btn_nfc:
                startActivity(new Intent(getApplicationContext(), ConfigNfcActivity.class));
                break;

            case R.id.home_btn_sound:
                startActivity(new Intent(getApplicationContext(), ConfigSoundActivity.class));
                break;

            case R.id.home_btn_vibration:
                startActivity(new Intent(getApplicationContext(), ConfigVibrationActivity.class));
                break;

            case R.id.home_btn_timer:
                startActivity(new Intent(getApplicationContext(), ConfigTimerActivity.class));
                break;

            case R.id.home_btn_notification:
                startActivity(new Intent(getApplicationContext(), ConfigNotificationActivity.class));
                break;

            case R.id.home_btn_incognit:
                startActivityForResult(new Intent(getApplicationContext(), ConfigIncognitActivity.class), RESULT_INCOGNIT_MODE);
                break;
        }
    }



    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data )
    {
        if( requestCode == RESULT_INCOGNIT_MODE && resultCode == RESULT_OK )
        {
            finish();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }



    /**
     * Inflate the menu; this adds items to the action bar if it is present.
     *
     * @param menu
     *
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    /**
     * Handle action bar item clicks here. The action bar will
     * automatically handle clicks on the Home/Up button, so long
     * as you specify a parent activity in AndroidManifest.xml.
     *
     * @param item
     *
     * @return
     */
    @Override
    public boolean onOptionsItemSelected( MenuItem item )
    {
        int id = item.getItemId();

        if( id == R.id.action_about )
        {
            startActivity(new Intent(getApplicationContext(), AboutActivity.class));
            return true;
        }
        else if( id == R.id.action_exit )
        {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
