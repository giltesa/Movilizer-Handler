package com.movilizer.client.android.handler.activity;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.movilizer.client.android.handler.R;

import java.util.Calendar;



/**
 * Created by Alberto Gil Tesa on 15/03/2018.
 */
public class AboutActivity extends AppCompatActivity implements OnClickListener
{
    private Context context;



    /**
     *
     */
    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        context = this;

        ((TextView) findViewById(R.id.about_version)).setText(getString(R.string.about_version) + " " + getVersionName() + " © " + Calendar.getInstance().get(Calendar.YEAR));
    }



    /**
     *
     */
    public void onClick( View view )
    {
        if( view.getId() == R.id.about_url )
        {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://movicoders.com")));
        }
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



    /**
     * @return
     */
    private String getVersionName()
    {
        try
        {
            PackageManager pm = context.getPackageManager();
            PackageInfo    pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        }
        catch( PackageManager.NameNotFoundException e )
        {
            return "";
        }
    }
}
