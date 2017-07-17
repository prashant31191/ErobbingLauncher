package com.erobbing.erobbinglauncher.widget;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.erobbing.erobbinglauncher.R;

public class DateView extends FrameLayout {

    private TextView mTime;
    private TextView mDate;

    int second;

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            init();
            handler.sendMessageDelayed(handler.obtainMessage(), 60 * 1000);
        }

        ;
    };

    public DateView(Context context) {
        this(context, null);
    }


    public DateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //this.setBackgroundDrawable(getContext().getResources().getDrawable(
        //	R.drawable.date_background));

        View view = LayoutInflater.from(getContext()).inflate(
                R.layout.date_view, this);

        mTime = (TextView) view.findViewById(R.id.time);
        Typeface typeFace = Typeface.createFromAsset(context.getAssets(), "fonts/Expansiva.ttf");
        mTime.setTypeface(typeFace);
        //mTime.getPaint().setFakeBoldText(true);//equals to android:textStyle="bold" in xml
        //tv_week = (TextView) view.findViewById(R.id.tv_week);
        mDate = (TextView) view.findViewById(R.id.date);
        init();
        final Calendar calendar = Calendar.getInstance();
        second = calendar.get(Calendar.SECOND);
        handler.sendMessageDelayed(handler.obtainMessage(),
                (60 - second) * 1000);
    }

    void init() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        mTime.setText(timeFormat.format(new Date()).toString());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        mDate.setText(dateFormat.format(new Date()).toString());
    }
}
