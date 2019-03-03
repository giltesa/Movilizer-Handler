package com.movilizer.client.android.handler.activity;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.movilizer.client.android.handler.R;
import com.movilizer.client.android.handler.util.preferences.Preferences;



public class ConfigIncognitActivity extends AppCompatActivity
{
    private Preferences pref;
    private Context     context;
    private CheckBox    cbIncognitMode;



    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_incognit);

        context = this;
        pref = new Preferences(this);


        cbIncognitMode = findViewById(R.id.incognit_mode);
        cbIncognitMode.setChecked(pref.isHideGUI());
        cbIncognitMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged( CompoundButton buttonView, boolean isChecked )
            {
                pref.setHideGUI(isChecked);

                if( isChecked )
                {
                    Toast.makeText(context, context.getResources().getText(R.string.conf_incognit_doodbye), Toast.LENGTH_LONG).show();

                    setResult(ConfigIncognitActivity.RESULT_OK, null);
                    finish();
                }
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
