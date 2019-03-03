package com.movilizer.client.android.handler.models;

import android.support.annotation.NonNull;



/**
 * Created by Alberto Gil Tesa on 05/03/2018.
 * <p>
 * http://android-er.blogspot.com.es/2014/04/spinner-with-different-displat-text-and.html
 */
public class SpinnerRow implements Comparable
{
    private String text;
    private String value;
    private String valueAux;


    public SpinnerRow( String text, String value )
    {
        this(text, value, null);
    }


    public SpinnerRow( String text, String value, String valueAux )
    {
        this.text = text;
        this.value = value;
        this.valueAux = valueAux;
    }


    public void setText( String text )
    {
        this.text = text;
    }


    public String getText()
    {
        return this.text;
    }


    public void setValue( String value )
    {
        this.value = value;
    }


    public void setValueAux( String value )
    {
        this.valueAux = value;
    }


    public String getValue()
    {
        return this.value;
    }


    public String getValueAux()
    {
        return this.valueAux;
    }


    @Override
    public int compareTo( @NonNull Object object )
    {
        SpinnerRow row = (SpinnerRow)object;

        int last = this.text.compareTo(row.text);
        return last == 0 ? this.text.compareTo(row.text) : last;
    }
}