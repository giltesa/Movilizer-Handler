package com.movilizer.client.android.handler.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.movilizer.client.android.handler.R;
import com.movilizer.client.android.handler.models.CardArrow;

import java.util.ArrayList;



public class CardArrowAdapter extends BaseAdapter
{
    private Context              context;
    private ArrayList<CardArrow> arrayList;



    /**
     * @param context
     * @param arrayList
     */
    public CardArrowAdapter( Context context, ArrayList<CardArrow> arrayList )
    {
        this.context = context;
        this.arrayList = arrayList;
    }



    @Override
    public View getView( int position, View convertView, ViewGroup parent )
    {
        if( convertView == null )
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.view_card_arrow, null);
        }

        LinearLayout layout   = convertView.findViewById(R.id.layout);
        TextView     title    = convertView.findViewById(R.id.title);
        TextView     subtitle = convertView.findViewById(R.id.subtitle);

        CardArrow item = arrayList.get(position);

        layout.setOnClickListener(item.getListener());
        layout.setTag(item.getIndex());
        title.setText(item.getTitle());
        subtitle.setText(item.getSubtitle());

        return convertView;
    }



    @Override
    public int getCount()
    {
        return arrayList.size();
    }



    @Override
    public long getItemId( int position )
    {
        return position;
    }



    @Override
    public Object getItem( int position )
    {
        return arrayList.get(position);
    }


}
