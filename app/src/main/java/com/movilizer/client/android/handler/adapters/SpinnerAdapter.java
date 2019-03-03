package com.movilizer.client.android.handler.adapters;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.movilizer.client.android.handler.R;
import com.movilizer.client.android.handler.models.SpinnerRow;

import java.util.ArrayList;
import java.util.ListIterator;



/**
 * Created by Alberto Gil Tesa on 05/03/2018.
 * <p>
 * http://android-er.blogspot.com.es/2014/04/spinner-with-different-displat-text-and.html
 * https://stackoverflow.com/questions/9970675/example-of-custom-setdropdownviewresource-spinner-item
 */
public class SpinnerAdapter extends ArrayAdapter<SpinnerRow>
{

    private Context               context;
    private ArrayList<SpinnerRow> spinnerRows;



    public SpinnerAdapter( Context context, ArrayList<SpinnerRow> spinnerRows )
    {
        super(context, android.R.layout.simple_spinner_item, spinnerRows);
        this.context = context;
        this.spinnerRows = spinnerRows;
    }



    public int getCount()
    {
        return spinnerRows.size();
    }



    public SpinnerRow getItem( int position )
    {
        return spinnerRows.get(position);
    }



    public long getItemId( int position )
    {
        return position;
    }



    public int getPositionFromValue( String value )
    {
        ListIterator it = spinnerRows.listIterator();
        while( it.hasNext() )
        {
            SpinnerRow item = (SpinnerRow) it.next();

            if( item.getValue().equals(value) )
                return getPosition(item);
        }

        return -1;
    }



    @Override
    public View getView( int position, View convertView, ViewGroup parent )
    {
        TextView label = new TextView(context);
        label.setText(spinnerRows.get(position).getText());

        return initView(position, convertView);
    }



    @Override
    public View getDropDownView( int position, View convertView, ViewGroup parent )
    {
        TextView label = new TextView(context);
        label.setText(spinnerRows.get(position).getText());

        return initView(position, convertView);
    }



    private View initView( int position, View convertView )
    {
        if( convertView == null )
            convertView = View.inflate(getContext(), R.layout.view_spinner, null);

        ((TextView)convertView.findViewById(R.id.text)).setText(getItem(position).getText());
        ((TextView)convertView.findViewById(R.id.value)).setText(getItem(position).getValue());
        ((TextView)convertView.findViewById(R.id.value_aux)).setText(getItem(position).getValueAux());

        return convertView;
    }
}
