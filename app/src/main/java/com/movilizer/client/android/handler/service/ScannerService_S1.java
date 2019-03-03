package com.movilizer.client.android.handler.service;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.honeywell.aidc.AidcManager;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.ScannerUnavailableException;
import com.honeywell.aidc.TriggerStateChangeEvent;
import com.honeywell.aidc.UnsupportedPropertyException;
import com.movilizer.client.android.handler.R;
import com.movilizer.client.android.handler.models.SpinnerRow;
import com.movilizer.client.android.handler.util.BarcodeType;
import com.movilizer.client.android.handler.util.GS1;
import com.movilizer.client.android.handler.util.Movilizer;
import com.movilizer.client.android.handler.util.NotificationBar;
import com.movilizer.client.android.handler.util.TryParse;
import com.movilizer.client.android.handler.util.Vibrator;
import com.movilizer.client.android.handler.util.preferences.PreferencesScanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



/**
 * Created by Alberto Gil Tesa on 06/03/2018.
 * <p>
 * Use the library / SDK: Honeywell_MobilitySDK_Android_v1.00.00.0054
 * <p>
 * It is compatible with these devices:
 * <ul>
 * <li>Honeywell Dolphin CT50</li>
 * <li>Honeywell Dolphin CT60</li>
 * <li>Honeywell Dolphin 75e</li>
 * <li>Honeywell CN51</li>
 * <li>Honeywell CK75</li>
 * <li>Honeywell CN75</li>
 * <li>Honeywell CN75e</li>
 * <li>Honeywell CN80 (The SDK does not indicate that it is compatible but works correctly)</li>
 * <li>Honeywell Scanpal EDA50</li>
 * <li>Honeywell Scanpal EDA50K</li>
 * <li>Honeywell Scanpal EDA70</li>
 * </ul>
 */
public class ScannerService_S1 extends Service implements BarcodeReader.BarcodeListener, BarcodeReader.TriggerListener
{
    public final static String                        SDK                 = "S1";
    private             AidcManager                   manager             = null;
    private             BarcodeReader                 barcodeReader       = null;
    private             BarcodeReader.BarcodeListener barcodeListener     = null;
    private             BarcodeReader.TriggerListener triggerListener     = null;
    //
    private             Movilizer                     movilizer;
    private             Context                       context;
    private             PreferencesScanner            pref;
    private             Vibrator                      vibrator;



    /**
     * A constructor of Class
     */
    public ScannerService_S1()
    {
        context = this;
        barcodeListener = this;
        triggerListener = this;
    }



    /**
     * Called by the system when the service is first created. Do not call this method directly.
     */
    @Override
    public void onCreate()
    {
        movilizer = new Movilizer(context, new Handler());
        pref = new PreferencesScanner(context);
        vibrator = new Vibrator(context);


        //Create notification bar for create service status:
        NotificationBar.create(context, getString(R.string.notifbar_scanning_service_active), null, new Intent(context, TimerService.class));


        // Create the AidcManager providing a Context and a CreatedCallback implementation.
        AidcManager.create(this, new AidcManager.CreatedCallback()
        {
            @Override
            public void onCreated( AidcManager aidcManager )
            {
                try
                {
                    manager = aidcManager;
                    barcodeReader = manager.createBarcodeReader();

                    // Register bar code event listener
                    barcodeReader.addBarcodeListener(barcodeListener);

                    // Set the trigger mode to client control
                    barcodeReader.setProperty(BarcodeReader.PROPERTY_TRIGGER_CONTROL_MODE, BarcodeReader.TRIGGER_CONTROL_MODE_AUTO_CONTROL);

                    // Register trigger state change listener
                    barcodeReader.addTriggerListener(triggerListener);

                    Map<String, Object> properties = new HashMap<>();

                    // Set Symbologies On/Off
                    properties.put(BarcodeReader.PROPERTY_AZTEC_ENABLED, false);
                    properties.put(BarcodeReader.PROPERTY_CHINA_POST_ENABLED, false);
                    properties.put(BarcodeReader.PROPERTY_CODABAR_ENABLED, false);
                    properties.put(BarcodeReader.PROPERTY_CODABLOCK_A_ENABLED, false);
                    properties.put(BarcodeReader.PROPERTY_CODABLOCK_F_ENABLED, false);
                    properties.put(BarcodeReader.PROPERTY_CODE_11_ENABLED, false);
                    properties.put(BarcodeReader.PROPERTY_CODE_128_ENABLED, false);
                    properties.put(BarcodeReader.PROPERTY_CODE_39_ENABLED, false);
                    properties.put(BarcodeReader.PROPERTY_CODE_93_ENABLED, false);
                    properties.put(BarcodeReader.PROPERTY_COMPOSITE_ENABLED, false);
                    properties.put(BarcodeReader.PROPERTY_DATAMATRIX_ENABLED, false);
                    properties.put(BarcodeReader.PROPERTY_EAN_13_ENABLED, false);
                    properties.put(BarcodeReader.PROPERTY_EAN_8_ENABLED, false);
                    properties.put(BarcodeReader.PROPERTY_GS1_128_ENABLED, false);
                    properties.put(BarcodeReader.PROPERTY_IATA_25_ENABLED, false);
                    properties.put(BarcodeReader.PROPERTY_INTERLEAVED_25_ENABLED, false);
                    properties.put(BarcodeReader.PROPERTY_KOREAN_POST_ENABLED, false);
                    properties.put(BarcodeReader.PROPERTY_MATRIX_25_ENABLED, false);
                    properties.put(BarcodeReader.PROPERTY_MAXICODE_ENABLED, false);
                    properties.put(BarcodeReader.PROPERTY_MSI_ENABLED, false);
                    properties.put(BarcodeReader.PROPERTY_PDF_417_ENABLED, false);
                    properties.put(BarcodeReader.PROPERTY_QR_CODE_ENABLED, false);
                    properties.put(BarcodeReader.PROPERTY_STANDARD_25_ENABLED, false);
                    properties.put(BarcodeReader.PROPERTY_TELEPEN_ENABLED, false);
                    properties.put(BarcodeReader.PROPERTY_TLC_39_ENABLED, false);
                    properties.put(BarcodeReader.PROPERTY_TRIOPTIC_ENABLED, false);
                    properties.put(BarcodeReader.PROPERTY_UPC_A_ENABLE, false);
                    properties.put(BarcodeReader.PROPERTY_UPC_E_ENABLED, false);


                    Map<String, BarcodeType> barcodeTypesList = pref.getBarcodeTypes();
                    BarcodeType              bType;
                    int                      value;


                    //AZTEC
                    bType = barcodeTypesList.get("AZTEC");
                    if( barcodeTypesList.size() == 0 || bType != null )
                    {
                        properties.put(BarcodeReader.PROPERTY_AZTEC_ENABLED, true);

                        if( bType != null )
                        {
                            value = TryParse.tryParseInteger(bType.getMinimumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_AZTEC_MINIMUM_LENGTH, value);

                            value = TryParse.tryParseInteger(bType.getMaximumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_AZTEC_MAXIMUM_LENGTH, TryParse.tryParseInteger(bType.getMaximumLength(), 0));
                        }
                    }

                    //CHINA_POST
                    bType = barcodeTypesList.get("CHINA_POST");
                    if( barcodeTypesList.size() == 0 || bType != null )
                    {
                        properties.put(BarcodeReader.PROPERTY_CHINA_POST_ENABLED, true);

                        if( bType != null )
                        {
                            value = TryParse.tryParseInteger(bType.getMinimumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_CHINA_POST_MINIMUM_LENGTH, value);

                            value = TryParse.tryParseInteger(bType.getMaximumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_CHINA_POST_MAXIMUM_LENGTH, TryParse.tryParseInteger(bType.getMaximumLength(), 0));
                        }
                    }

                    //CODABAR
                    bType = barcodeTypesList.get("CODABAR");
                    if( barcodeTypesList.size() == 0 || bType != null )
                    {
                        properties.put(BarcodeReader.PROPERTY_CODABAR_ENABLED, true);

                        if( bType != null )
                        {
                            value = TryParse.tryParseInteger(bType.getMinimumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_CODABAR_MINIMUM_LENGTH, value);

                            value = TryParse.tryParseInteger(bType.getMaximumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_CODABAR_MAXIMUM_LENGTH, TryParse.tryParseInteger(bType.getMaximumLength(), 0));
                        }
                    }

                    //CODABLOCK_A
                    bType = barcodeTypesList.get("CODABLOCK_A");
                    if( barcodeTypesList.size() == 0 || bType != null )
                    {
                        properties.put(BarcodeReader.PROPERTY_CODABLOCK_A_ENABLED, true);

                        if( bType != null )
                        {
                            value = TryParse.tryParseInteger(bType.getMinimumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_CODABLOCK_A_MINIMUM_LENGTH, value);

                            value = TryParse.tryParseInteger(bType.getMaximumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_CODABLOCK_A_MAXIMUM_LENGTH, TryParse.tryParseInteger(bType.getMaximumLength(), 0));
                        }
                    }

                    //CODABLOCK_F
                    bType = barcodeTypesList.get("CODABLOCK_F");
                    if( barcodeTypesList.size() == 0 || bType != null )
                    {
                        properties.put(BarcodeReader.PROPERTY_CODABLOCK_F_ENABLED, true);

                        if( bType != null )
                        {
                            value = TryParse.tryParseInteger(bType.getMinimumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_CODABLOCK_F_MINIMUM_LENGTH, value);

                            value = TryParse.tryParseInteger(bType.getMaximumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_CODABLOCK_F_MAXIMUM_LENGTH, TryParse.tryParseInteger(bType.getMaximumLength(), 0));
                        }
                    }

                    //CODE_11
                    bType = barcodeTypesList.get("CODE_11");
                    if( barcodeTypesList.size() == 0 || bType != null )
                    {
                        properties.put(BarcodeReader.PROPERTY_CODE_11_ENABLED, true);

                        if( bType != null )
                        {
                            value = TryParse.tryParseInteger(bType.getMinimumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_CODE_11_MINIMUM_LENGTH, value);

                            value = TryParse.tryParseInteger(bType.getMaximumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_CODE_11_MAXIMUM_LENGTH, TryParse.tryParseInteger(bType.getMaximumLength(), 0));
                        }
                    }

                    //CODE_128
                    bType = barcodeTypesList.get("CODE_128");
                    if( barcodeTypesList.size() == 0 || bType != null )
                    {
                        properties.put(BarcodeReader.PROPERTY_CODE_128_ENABLED, true);

                        if( bType != null )
                        {
                            value = TryParse.tryParseInteger(bType.getMinimumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_CODE_128_MINIMUM_LENGTH, value);

                            value = TryParse.tryParseInteger(bType.getMaximumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_CODE_128_MAXIMUM_LENGTH, TryParse.tryParseInteger(bType.getMaximumLength(), 0));
                        }
                    }

                    //CODE_39
                    bType = barcodeTypesList.get("CODE_39");
                    if( barcodeTypesList.size() == 0 || bType != null )
                    {
                        properties.put(BarcodeReader.PROPERTY_CODE_39_ENABLED, true);

                        if( bType != null )
                        {
                            value = TryParse.tryParseInteger(bType.getMinimumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_CODE_39_MINIMUM_LENGTH, value);

                            value = TryParse.tryParseInteger(bType.getMaximumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_CODE_39_MAXIMUM_LENGTH, TryParse.tryParseInteger(bType.getMaximumLength(), 0));
                        }
                    }

                    //CODE_93
                    bType = barcodeTypesList.get("CODE_93");
                    if( barcodeTypesList.size() == 0 || bType != null )
                    {
                        properties.put(BarcodeReader.PROPERTY_CODE_93_ENABLED, true);

                        if( bType != null )
                        {
                            value = TryParse.tryParseInteger(bType.getMinimumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_CODE_93_MINIMUM_LENGTH, value);

                            value = TryParse.tryParseInteger(bType.getMaximumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_CODE_93_MAXIMUM_LENGTH, TryParse.tryParseInteger(bType.getMaximumLength(), 0));
                        }
                    }

                    //COMPOSITE
                    bType = barcodeTypesList.get("COMPOSITE");
                    if( barcodeTypesList.size() == 0 || bType != null )
                    {
                        properties.put(BarcodeReader.PROPERTY_COMPOSITE_ENABLED, true);

                        if( bType != null )
                        {
                            value = TryParse.tryParseInteger(bType.getMinimumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_COMPOSITE_MINIMUM_LENGTH, value);

                            value = TryParse.tryParseInteger(bType.getMaximumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_COMPOSITE_MAXIMUM_LENGTH, TryParse.tryParseInteger(bType.getMaximumLength(), 0));
                        }
                    }

                    //DATAMATRIX
                    bType = barcodeTypesList.get("DATAMATRIX");
                    if( barcodeTypesList.size() == 0 || bType != null )
                    {
                        properties.put(BarcodeReader.PROPERTY_DATAMATRIX_ENABLED, true);

                        if( bType != null )
                        {
                            value = TryParse.tryParseInteger(bType.getMinimumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_DATAMATRIX_MINIMUM_LENGTH, value);

                            value = TryParse.tryParseInteger(bType.getMaximumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_DATAMATRIX_MAXIMUM_LENGTH, TryParse.tryParseInteger(bType.getMaximumLength(), 0));
                        }
                    }

                    //EAN_13
                    bType = barcodeTypesList.get("EAN_13");
                    if( barcodeTypesList.size() == 0 || bType != null )
                    {
                        properties.put(BarcodeReader.PROPERTY_EAN_13_ENABLED, true);
                    }

                    //EAN_8
                    bType = barcodeTypesList.get("EAN_8");
                    if( barcodeTypesList.size() == 0 || bType != null )
                    {
                        properties.put(BarcodeReader.PROPERTY_EAN_8_ENABLED, true);
                    }

                    //GS1_128
                    bType = barcodeTypesList.get("GS1_128");
                    if( barcodeTypesList.size() == 0 || bType != null )
                    {
                        properties.put(BarcodeReader.PROPERTY_GS1_128_ENABLED, true);

                        if( bType != null )
                        {
                            value = TryParse.tryParseInteger(bType.getMinimumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_GS1_128_MINIMUM_LENGTH, value);

                            value = TryParse.tryParseInteger(bType.getMaximumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_GS1_128_MAXIMUM_LENGTH, TryParse.tryParseInteger(bType.getMaximumLength(), 0));
                        }
                    }

                    //IATA_25
                    bType = barcodeTypesList.get("IATA_25");
                    if( barcodeTypesList.size() == 0 || bType != null )
                    {
                        properties.put(BarcodeReader.PROPERTY_IATA_25_ENABLED, true);

                        if( bType != null )
                        {
                            value = TryParse.tryParseInteger(bType.getMinimumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_IATA_25_MINIMUM_LENGTH, value);

                            value = TryParse.tryParseInteger(bType.getMaximumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_IATA_25_MAXIMUM_LENGTH, TryParse.tryParseInteger(bType.getMaximumLength(), 0));
                        }
                    }

                    //INTERLEAVED_25
                    bType = barcodeTypesList.get("INTERLEAVED_25");
                    if( barcodeTypesList.size() == 0 || bType != null )
                    {
                        properties.put(BarcodeReader.PROPERTY_INTERLEAVED_25_ENABLED, true);

                        if( bType != null )
                        {
                            value = TryParse.tryParseInteger(bType.getMinimumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_INTERLEAVED_25_MINIMUM_LENGTH, value);

                            value = TryParse.tryParseInteger(bType.getMaximumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_INTERLEAVED_25_MAXIMUM_LENGTH, TryParse.tryParseInteger(bType.getMaximumLength(), 0));
                        }
                    }

                    //KOREAN_POST
                    bType = barcodeTypesList.get("KOREAN_POST");
                    if( barcodeTypesList.size() == 0 || bType != null )
                    {
                        properties.put(BarcodeReader.PROPERTY_KOREAN_POST_ENABLED, true);

                        if( bType != null )
                        {
                            value = TryParse.tryParseInteger(bType.getMinimumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_KOREAN_POST_MINIMUM_LENGTH, value);

                            value = TryParse.tryParseInteger(bType.getMaximumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_KOREAN_POST_MAXIMUM_LENGTH, TryParse.tryParseInteger(bType.getMaximumLength(), 0));
                        }
                    }

                    //MATRIX_25
                    bType = barcodeTypesList.get("MATRIX_25");
                    if( barcodeTypesList.size() == 0 || bType != null )
                    {
                        properties.put(BarcodeReader.PROPERTY_MATRIX_25_ENABLED, true);

                        if( bType != null )
                        {
                            value = TryParse.tryParseInteger(bType.getMinimumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_MATRIX_25_MINIMUM_LENGTH, value);

                            value = TryParse.tryParseInteger(bType.getMaximumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_MATRIX_25_MAXIMUM_LENGTH, TryParse.tryParseInteger(bType.getMaximumLength(), 0));
                        }
                    }

                    //MAXICODE
                    bType = barcodeTypesList.get("MAXICODE");
                    if( barcodeTypesList.size() == 0 || bType != null )
                    {
                        properties.put(BarcodeReader.PROPERTY_MAXICODE_ENABLED, true);

                        if( bType != null )
                        {
                            value = TryParse.tryParseInteger(bType.getMinimumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_MAXICODE_MINIMUM_LENGTH, value);

                            value = TryParse.tryParseInteger(bType.getMaximumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_MAXICODE_MAXIMUM_LENGTH, TryParse.tryParseInteger(bType.getMaximumLength(), 0));
                        }
                    }

                    //MSI
                    bType = barcodeTypesList.get("MSI");
                    if( barcodeTypesList.size() == 0 || bType != null )
                    {
                        properties.put(BarcodeReader.PROPERTY_MSI_ENABLED, true);

                        if( bType != null )
                        {
                            value = TryParse.tryParseInteger(bType.getMinimumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_MSI_MINIMUM_LENGTH, value);

                            value = TryParse.tryParseInteger(bType.getMaximumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_MSI_MAXIMUM_LENGTH, TryParse.tryParseInteger(bType.getMaximumLength(), 0));
                        }
                    }

                    //PDF_417
                    bType = barcodeTypesList.get("PDF_417");
                    if( barcodeTypesList.size() == 0 || bType != null )
                    {
                        properties.put(BarcodeReader.PROPERTY_PDF_417_ENABLED, true);

                        if( bType != null )
                        {
                            value = TryParse.tryParseInteger(bType.getMinimumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_PDF_417_MINIMUM_LENGTH, value);

                            value = TryParse.tryParseInteger(bType.getMaximumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_PDF_417_MAXIMUM_LENGTH, TryParse.tryParseInteger(bType.getMaximumLength(), 0));
                        }
                    }

                    //QR_CODE
                    bType = barcodeTypesList.get("QR_CODE");
                    if( barcodeTypesList.size() == 0 || bType != null )
                    {
                        properties.put(BarcodeReader.PROPERTY_QR_CODE_ENABLED, true);

                        if( bType != null )
                        {
                            value = TryParse.tryParseInteger(bType.getMinimumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_QR_CODE_MINIMUM_LENGTH, value);

                            value = TryParse.tryParseInteger(bType.getMaximumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_QR_CODE_MAXIMUM_LENGTH, TryParse.tryParseInteger(bType.getMaximumLength(), 0));
                        }
                    }

                    //STANDARD_25
                    bType = barcodeTypesList.get("STANDARD_25");
                    if( barcodeTypesList.size() == 0 || bType != null )
                    {
                        properties.put(BarcodeReader.PROPERTY_STANDARD_25_ENABLED, true);

                        if( bType != null )
                        {
                            value = TryParse.tryParseInteger(bType.getMinimumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_STANDARD_25_MINIMUM_LENGTH, value);

                            value = TryParse.tryParseInteger(bType.getMaximumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_STANDARD_25_MAXIMUM_LENGTH, TryParse.tryParseInteger(bType.getMaximumLength(), 0));
                        }
                    }

                    //TELEPEN
                    bType = barcodeTypesList.get("TELEPEN");
                    if( barcodeTypesList.size() == 0 || bType != null )
                    {
                        properties.put(BarcodeReader.PROPERTY_TELEPEN_ENABLED, true);

                        if( bType != null )
                        {
                            value = TryParse.tryParseInteger(bType.getMinimumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_TELEPEN_MINIMUM_LENGTH, value);

                            value = TryParse.tryParseInteger(bType.getMaximumLength(), -1);
                            if( value > -1 )
                                properties.put(BarcodeReader.PROPERTY_TELEPEN_MAXIMUM_LENGTH, TryParse.tryParseInteger(bType.getMaximumLength(), 0));
                        }
                    }

                    //TLC_39
                    bType = barcodeTypesList.get("TLC_39");
                    if( barcodeTypesList.size() == 0 || bType != null )
                    {
                        properties.put(BarcodeReader.PROPERTY_TLC_39_ENABLED, true);
                    }

                    //TRIOPTIC
                    bType = barcodeTypesList.get("TRIOPTIC");
                    if( barcodeTypesList.size() == 0 || bType != null )
                    {
                        properties.put(BarcodeReader.PROPERTY_TRIOPTIC_ENABLED, true);
                    }

                    //UPC_A
                    bType = barcodeTypesList.get("UPC_A");
                    if( barcodeTypesList.size() == 0 || bType != null )
                    {
                        properties.put(BarcodeReader.PROPERTY_UPC_A_ENABLE, true);
                    }

                    //UPC_E
                    bType = barcodeTypesList.get("UPC_E");
                    if( barcodeTypesList.size() == 0 || bType != null )
                    {
                        properties.put(BarcodeReader.PROPERTY_UPC_E_ENABLED, true);
                    }


                    // Turn on center decoding
                    properties.put(BarcodeReader.PROPERTY_CENTER_DECODE, true);

                    // Enable bad read response
                    properties.put(BarcodeReader.PROPERTY_NOTIFICATION_GOOD_READ_ENABLED, pref.getDriverSounds());
                    properties.put(BarcodeReader.PROPERTY_NOTIFICATION_BAD_READ_ENABLED, pref.getDriverSounds());

                    // Apply the settings
                    barcodeReader.setProperties(properties);

                    // Start barcode reader:
                    barcodeReader.claim();
                }
                catch( UnsupportedPropertyException e )
                {
                    e.printStackTrace();
                    Log.e(PreferencesScanner.LOG_TAG, "Failed to apply properties");
                }
                catch( ScannerUnavailableException e )
                {
                    e.printStackTrace();
                    Log.e(PreferencesScanner.LOG_TAG, "Scanner unavailable");
                }
                catch( Exception e )
                {
                    e.printStackTrace();
                    Log.e(PreferencesScanner.LOG_TAG, e.toString());
                }
            }
        });
    }



    /**
     * Called by the system to notify a Service that it is no longer used and is being removed.
     * The service should clean up any resources it holds (threads, registered receivers, etc) at
     * this point. Upon return, there will be no more calls in to this Service object and it is
     * effectively dead. Do not call this method directly.
     * <p>
     * Unregister listeners from the barcode reader
     */
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if( barcodeReader != null )
        {
            barcodeReader.release();
            barcodeReader.removeBarcodeListener(barcodeListener);
            barcodeReader.removeTriggerListener(triggerListener);
            barcodeReader = null;
        }
        NotificationBar.cancel();
    }



    /**
     * Return the communication channel to the service. May return null if clients can not bind to
     * the service. The returned IBinder is usually for a complex interface that has been described
     * using aidl.
     * <p>
     * Note that unlike other application components, calls on to the IBinder interface returned here
     * may not happen on the main thread of the process. More information about the main thread can
     * be found in Processes and Threads.
     *
     * @param intent The Intent that was used to bind to this service, as given to Context.bindService.
     *               Note that any extras that were included with the Intent at that point will not be seen here
     *
     * @return Return an IBinder through which clients can call on to the service. This value may be null.
     */
    @Nullable
    @Override
    public IBinder onBind( Intent intent )
    {
        return null;
    }



    /**
     * SDK: Called when a bar code label is successfully scanned.
     *
     * @param event Contains information about the scanned bar code
     */
    @Override
    public void onBarcodeEvent( final BarcodeReadEvent event )
    {
        String aimID = event.getAimId();

        Map<String, Object> values = new HashMap<>();
        values.put("VALUE", event.getBarcodeData());
        values.put("TYPE", getBarcodeType(event.getCodeId()));
        values.put("CHARACTER", event.getCharset().displayName());
        values.put("AIM_ID", aimID);
        values.put("TIMESTAMP", event.getTimestamp());

        if( aimID.equals("]d2") || aimID.equals("]C1") )
        {
            try
            {
                values.put("VALUES", GS1.split((String) values.get("VALUE")));
            }
            catch( Exception ignored )
            {
            }
        }

        movilizer.doSendMessage(values);
        vibrator.success();
    }



    /**
     * SDK: Called when a bar code label is not successfully scanned.
     *
     * @param barcodeFailureEvent Contains information about the failure
     */
    @Override
    public void onFailureEvent( BarcodeFailureEvent barcodeFailureEvent )
    {
        vibrator.error();
    }



    /**
     * SDK: This event is dispatched by the BarcodeReader when a state change occurs in the scan trigger.
     *
     * @param triggerStateChangeEvent Contains information about the trigger state change
     */
    @Override
    public void onTriggerEvent( TriggerStateChangeEvent triggerStateChangeEvent )
    {
    }



    /**
     * Returns all compatible devices for this SDK
     *
     * @return ArrayList<> The trigger state.
     */
    public static ArrayList<SpinnerRow> getSupportedDevices()
    {
        ArrayList<SpinnerRow> devices = new ArrayList<>();
        devices.add(new SpinnerRow("Honeywell Dolphin CT50", (SDK + "_CT50"), SDK));
        devices.add(new SpinnerRow("Honeywell Dolphin CT60", (SDK + "_CT60"), SDK));
        devices.add(new SpinnerRow("Honeywell Dolphin 75e", (SDK + "_75E"), SDK));
        devices.add(new SpinnerRow("Honeywell CN51", (SDK + "_CN51"), SDK));
        devices.add(new SpinnerRow("Honeywell CK75", (SDK + "_CK75"), SDK));
        devices.add(new SpinnerRow("Honeywell CN75", (SDK + "_CN75"), SDK));
        devices.add(new SpinnerRow("Honeywell CN75e", (SDK + "_CN75E"), SDK));
        devices.add(new SpinnerRow("Honeywell CN80", (SDK + "_CN80"), SDK)); //(The SDK does not indicate that it is compatible but works correctly)
        devices.add(new SpinnerRow("Honeywell Scanpal EDA50", (SDK + "_EDA50"), SDK));
        devices.add(new SpinnerRow("Honeywell Scanpal EDA50K", (SDK + "_EDA50K"), SDK));
        devices.add(new SpinnerRow("Honeywell Scanpal EDA70", (SDK + "_EDA70"), SDK));
        return devices;
    }



    /**
     * Returns the readable name of the received barcode type.
     *
     * @return String with the type of code read.
     */
    private String getBarcodeType( String codeID )
    {
        switch( codeID )
        {
            case ",":
                return "INFOMAIL";
            case "-":
                return "MICROQR_ALT";
            case ".":
                return "DOTCODE";
            case "1":
                return "CODE1";
            case ";":
                return "MERGED_COUPON";
            case "<":
                return "CODE39_BASE32;CODE32;ITALIAN PHARMACODE;PARAF;LABELCODE_V";
            case "=":
                return "TRIOPTIC";
            case ">":
                return "LABELCODE_IV";
            case "?":
                return "KOREA_POST";
            case "A":
                return "AUS_POST";
            case "B":
                return "BRITISH_POST";
            case "C":
                return "CANADIAN_POST";
            case "D":
                return "EAN8";
            case "E":
                return "UPCE";
            case "G":
                return "BC412";
            case "H":
                return "HAN_XIN_CODE";
            case "I":
                return "GS1_128";
            case "J":
                return "JAPAN_POST";
            case "K":
                return "KIX_CODE";
            case "L":
                return "PLANET_CODE";
            case "M":
                return "USPS_4_STATE;INTELLIGENT_MAIL";
            case "N":
                return "UPU_4_STATE;ID_TAGS";
            case "O":
                return "OCR";
            case "P":
                return "POSTNET";
            case "Q":
                return "HK25;CHINA_POST";
            case "R":
                return "MICROPDF";
            case "S":
                return "SECURE_CODE";
            case "T":
                return "TLC39";
            case "U":
                return "ULTRACODE";
            case "V":
                return "CODABLOCK_A";
            case "W":
                return "POSICODE";
            case "X":
                return "GRID_MATRIX";
            case "Y":
                return "NEC25";
            case "Z":
                return "MESA";
            case "[":
                return "SWEEDISH_POST";
            case "]":
                return "BRAZIL_POST";
            case "`":
                return "EAN13_ISBN";
            case "a":
                return "CODABAR";
            case "b":
                return "CODE39";
            case "c":
                return "UPCA";
            case "d":
                return "EAN13";
            case "e":
                return "I25";
            case "f":
                return "S25 (2BAR and 3BAR)";
            case "g":
                return "MSI";
            case "h":
                return "CODE11";
            case "i":
                return "CODE93";
            case "j":
                return "CODE128";
            case "k":
                return "UNUSED";
            case "l":
                return "CODE49";
            case "m":
                return "M25";
            case "n":
                return "PLESSEY";
            case "o":
                return "CODE16K";
            case "p":
                return "CHANNELCODE";
            case "q":
                return "CODABLOCK_F";
            case "r":
                return "PDF417";
            case "s":
                return "QRCODE";
            case "t":
                return "TELEPEN";
            case "u":
                return "CODEZ";
            case "v":
                return "VERICODE";
            case "w":
                return "DATAMATRIX";
            case "x":
                return "MAXICODE";
            case "y":
                return "COMPOSITE;GS1_DATABAR;RSS";
            case "z":
                return "AZTEC_CODE";
            case "{":
                return "GS1_DATABAR_LIM";
            case "|":
                return "RM_MAILMARK";
            case "}":
                return "GS1_DATABAR_EXP";
            default:
                return codeID;
        }
    }

}
