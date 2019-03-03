package com.movilizer.client.android.handler.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.movilizer.client.android.handler.R;



/**
 * Created by Alberto Gil Tesa on 2019/01/20
 */
public class MovilizerDialogActivity extends AppCompatActivity
{
    protected ImageView ivIcon;
    protected TextView  tvTitle;
    protected TextView  tvMessage;
    protected Button    bButton;



    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        setTitle(null);


        // Get properties from intent for customizing dialog:
        final Intent  intent     = getIntent();
        final Integer iconInt    = intent.getIntExtra("ICON", R.drawable.ic_dialog_info);
        final String  titleStr   = intent.getStringExtra("TITLE");
        final String  messageStr = intent.getStringExtra("MESSAGE");
        final String  btnTextStr = intent.getStringExtra("BUTTON_TEXT");
        final int     timeout    = intent.getIntExtra("TIMEOUT", 0);


        // Create de custom dialog:
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(getLayoutInflater().inflate(R.layout.activity_movilizer_dialog, null));

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);

        alertDialog.show();

        alertDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);


        // Get references of different elements of dialog:
        ivIcon = alertDialog.findViewById(R.id.icon);
        tvTitle = alertDialog.findViewById(R.id.title);
        tvMessage = alertDialog.findViewById(R.id.message);
        bButton = alertDialog.findViewById(R.id.layout);


        // Customizing elements of dialog:
        ivIcon.setImageResource(iconInt);
        tvTitle.setText(titleStr);
        tvMessage.setText(messageStr);
        bButton.setText(btnTextStr);


        // Add listener button:
        bButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick( View arg0 )
            {
                finish();
            }
        });


        // Add auto close dialog (activity):
        if( timeout > 0 )
        {
            new CountDownTimer(timeout * 1000, 1000)
            {
                public void onTick( long millisUntilFinished )
                {
                    bButton.setText(btnTextStr + " (" + (millisUntilFinished / 1000) + ")");
                }



                public void onFinish()
                {
                    finish();
                }
            }.start();
        }
    }



    /**
     * This method makes it easy for you to launch a MovilizerDialogActivity.
     */
    public static final void showError( Context context, int title, Object message, int btnText, int timeout )
    {
        show(context, Type.ERROR, title, message, btnText, timeout);
    }



    public static final void showWarning( Context context, int title, Object message, int btnText, int timeout )
    {
        show(context, Type.WARNING, title, message, btnText, timeout);
    }



    public static final void showInfo( Context context, int title, Object message, int btnText, int timeout )
    {
        show(context, Type.INFO, title, message, btnText, timeout);
    }



    public static final void showNFC( Context context, int title, Object message, int btnText, int timeout )
    {
        show(context, Type.NFC, title, message, btnText, timeout);
    }



    private static final void show( Context context, Type type, int title, Object message, int btnText, int timeout )
    {
        Class<?> cls = MovilizerDialogActivity.class;
        int      icon;
        String   msg = "";

        if( message instanceof String )
        {
            msg = (String) message;
        }
        else if( message instanceof Integer )
        {
            msg = context.getResources().getString((int) message);
        }

        switch( type )
        {
            case NFC:
                icon = R.drawable.ic_dialog_nfc;
                cls = MovilizerNfcDialogActivity.class;
                break;
            case ERROR:
                icon = R.drawable.ic_dialog_error;
                break;
            case WARNING:
                icon = R.drawable.ic_dialog_warning;
                break;
            case INFO:
            default:
                icon = R.drawable.ic_dialog_info;
                break;
        }

        Intent intent = new Intent(context, cls);
        intent.putExtra("ICON", icon);
        intent.putExtra("TITLE", context.getResources().getString(title));
        intent.putExtra("MESSAGE", msg);
        intent.putExtra("BUTTON_TEXT", context.getResources().getString(btnText));
        intent.putExtra("TIMEOUT", timeout);
        context.startActivity(intent);
    }



    private enum Type
    {
        NFC, ERROR, WARNING, INFO
    }
}
