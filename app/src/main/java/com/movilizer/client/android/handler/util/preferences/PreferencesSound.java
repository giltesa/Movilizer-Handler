package com.movilizer.client.android.handler.util.preferences;


import android.content.Context;
import android.net.Uri;

import com.movilizer.client.android.handler.R;
import com.movilizer.client.android.handler.util.TryParse;

import java.security.InvalidParameterException;
import java.util.Map;



/**
 * Created by Alberto Gil Tesa
 */
@SuppressWarnings({"UnusedDeclaration"})
public class PreferencesSound extends Preferences
{
    private final       String PREFIX     = "SOUND_";
    private final       String SOUND_NUM  = "NUM";
    public final static int    NUM_SOUNDS = 8;
    //Parameters from MEL:
    private final       String PLAY_SOUND = "PLAY";



    /**
     * @param context
     */
    public PreferencesSound( Context context )
    {
        super(context);
    }



    public PreferencesSound( Context context, Map<String, Object> properties ) throws InvalidParameterException
    {
        super(context);


        // PLAY_SOUND (Enum Sound)
        //
        String playSoundStr = TryParse.tryParseString(properties.get(PLAY_SOUND), null);

        if( playSoundStr == null || playSoundStr.isEmpty() )
            throw new InvalidParameterException(context.getResources().getString(R.string.pref_parameter_mandatory).replace("#1", PLAY_SOUND));

        int playSound = Sound.byOrdinal(playSoundStr);

        if( playSound == -1 )
            throw new InvalidParameterException(context.getResources().getString(R.string.pref_parameter_not_valid).replace("#1", PLAY_SOUND).replace("#2", playSoundStr));

        setPlaySound(playSound);
    }


    /// GETTERS ////////////////////////////////////////////////////////////////////////////////////



    public Uri getSound( int index )
    {
        if( index >= 0 && index < NUM_SOUNDS )
        {
            String value = read.getString(PREFIX + SOUND_NUM + index, null);

            if( value != null )
                return Uri.parse(value);
        }

        return null;
    }



    public int getPlaySound()
    {
        return read.getInt(PREFIX + PLAY_SOUND, -1);
    }


    /// SETTERS ////////////////////////////////////////////////////////////////////////////////////



    public void setSound( int index, String uri )
    {
        if( index >= 0 && index < NUM_SOUNDS )
        {
            write.putString(PREFIX + SOUND_NUM + index, uri);
            write.commit();
        }
    }



    public void setSound( int index, Uri uri )
    {
        setSound(index, uri == null ? null : uri.toString());
    }



    public void setPlaySound( int value )
    {
        write.putInt(PREFIX + PLAY_SOUND, value);
        write.commit();
    }



    /**
     *
     */
    public enum Sound
    {
        INFO(0), WARNING(1), ERROR(2), FATAL_ERROR(3), FATALERROR(3),
        ZERO(0), ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7);


        private int numVal;



        Sound( int numVal )
        {
            this.numVal = numVal;
        }



        public int getNumVal()
        {
            return numVal;
        }



        public static int byOrdinal( Object find )
        {
            for( Sound m : Sound.values() )
            {
                if( find instanceof String )
                {
                    if( m.toString().equalsIgnoreCase((String) find) || TryParse.tryParseInteger((String) find, -1) == m.getNumVal() )
                    {
                        return m.getNumVal();
                    }
                }
                else if( find instanceof Integer && m.getNumVal() == (int) find )
                {
                    return m.getNumVal();
                }
            }

            return -1;
        }


    }


}
