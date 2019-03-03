package com.movilizer.client.android.handler.activity;


import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.movilizer.client.android.handler.R;
import com.movilizer.client.android.handler.adapters.CardArrowAdapter;
import com.movilizer.client.android.handler.models.CardArrow;
import com.movilizer.client.android.handler.util.preferences.PreferencesSound;

import java.util.ArrayList;



public class ConfigSoundActivity extends AppCompatActivity implements View.OnClickListener
{
    private PreferencesSound pref;
    private ListView         lvSounds;
    private CardArrowAdapter adapter;



    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_sound);


        pref = new PreferencesSound(this);

        ArrayList<CardArrow> model = new ArrayList<>();

        for( int i = 0; i < PreferencesSound.NUM_SOUNDS; i++ )
        {
            Uri    uri      = pref.getSound(i);
            String title    = null;
            String subtitle = (uri == null ? "" : RingtoneManager.getRingtone(this, uri).getTitle(this));

            switch( i )
            {
                case 0:
                    title = getString(R.string.conf_sound_info); break;
                case 1:
                    title = getString(R.string.conf_sound_warning); break;
                case 2:
                    title = getString(R.string.conf_sound_error); break;
                case 3:
                    title = getString(R.string.conf_sound_fatal_error); break;
                default:
                    title = getString(R.string.conf_sound_other); break;
            }

            model.add(new CardArrow(model.size(), (i + 1) + ": " + title, subtitle, this));
        }

        adapter = new CardArrowAdapter(this, model);
        lvSounds = findViewById(R.id.sound_list);
        lvSounds.setAdapter(adapter);
    }



    /**
     * @param view
     */
    @Override
    public void onClick( View view )
    {
        //final Uri currentTone = RingtoneManager.getActualDefaultRingtoneUri(ConfigSoundActivity.this, RingtoneManager.TYPE_ALARM);

        int index = (int) view.getTag();
        Uri uri   = pref.getSound(index);

        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, getString(R.string.conf_sound_select_sound));
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, uri);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);

        startActivityForResult(intent, (int) view.getTag());
    }



    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data )
    {
        if( resultCode == RESULT_OK && (requestCode >= 0 && requestCode < PreferencesSound.NUM_SOUNDS) )
        {
            int index = requestCode;
            Uri uri   = (Uri) data.getExtras().get(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);

            pref.setSound(index, uri);

            CardArrow ca = (CardArrow) adapter.getItem(index);
            ca.setSubtitle((uri == null ? "" : RingtoneManager.getRingtone(this, uri).getTitle(this)));
            adapter.notifyDataSetChanged();
        }

        super.onActivityResult(requestCode, resultCode, data);
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
