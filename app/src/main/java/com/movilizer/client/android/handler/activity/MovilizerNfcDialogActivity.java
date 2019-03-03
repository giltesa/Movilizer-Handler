package com.movilizer.client.android.handler.activity;


import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.widget.Toast;

import com.movilizer.client.android.handler.R;
import com.movilizer.client.android.handler.util.Movilizer;
import com.movilizer.client.android.handler.util.Vibrator;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;



/**
 * Created by Alberto Gil Tesa on 2019/01/20
 */
public class MovilizerNfcDialogActivity extends MovilizerDialogActivity
{
    private Vibrator  vibrator;
    private Movilizer movilizer;

    private NfcAdapter    nfcAdapter;
    private PendingIntent pendingIntent;
    private IntentFilter  writeTagFilters[];



    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);

        vibrator = new Vibrator(this);
        movilizer = new Movilizer(this, new Handler());


        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if( nfcAdapter == null )
        {
            Toast.makeText(this, getString(R.string.movilizer_dialog_nfc_not_available), Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        writeTagFilters = new IntentFilter[]{tagDetected};
    }



    /**
     * @param intent
     */
    @SuppressLint("NewApi")
    protected void onNewIntent( Intent intent )
    {
        if( NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction()) )
        {
            Tag    myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String id    = new BigInteger(1, myTag.getId()).toString();

            vibrator.success();

            Map<String, Object> values = new HashMap<>();
            values.put("ID", id);
            movilizer.doSendMessage(values);

            finish();
        }
    }



    /**
     *
     */
    public void onPause()
    {
        super.onPause();
        if( nfcAdapter != null )
        {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }



    /**
     *
     */
    public void onResume()
    {
        super.onResume();

        if( nfcAdapter != null )
        {
            if( !nfcAdapter.isEnabled() )
            {
                Toast.makeText(this, getString(R.string.movilizer_dialog_nfc_disabled), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));

                finish();
                return;
            }

            nfcAdapter.enableForegroundDispatch(this, pendingIntent, writeTagFilters, null);
        }
    }
}
