package com.movilizer.client.android.handler.models;


import android.view.View;



public class CardArrow
{
    private int                  index;
    private String               title;
    private String               subtitle;
    private View.OnClickListener listener;



    /**
     *
     */
    public CardArrow()
    {
        this(0, null, null, null);
    }



    /**
     * @param index
     * @param title
     * @param subtitle
     * @param listener
     */
    public CardArrow( int index, String title, String subtitle, View.OnClickListener listener )
    {
        this.index = index;
        this.title = title;
        this.subtitle = subtitle;
        this.listener = listener;
    }



    public int getIndex()
    {
        return index;
    }



    public String getTitle()
    {
        return title;
    }



    public String getSubtitle()
    {
        return subtitle;
    }



    public View.OnClickListener getListener()
    {
        return listener;
    }



    public void setTitle( String title )
    {
        this.title = title;
    }



    public void setSubtitle( String subtitle )
    {
        this.subtitle = subtitle;
    }


}

