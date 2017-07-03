package com.erobbing.erobbinglauncher.widget;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.erobbing.erobbinglauncher.R;
//import com.yaomei.util.strHelpeUtil;

public class DateView extends FrameLayout {

    private TextView tv_date_time, tv_week, tv_date;

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

        tv_date_time = (TextView) view.findViewById(R.id.tv_date_time);
        //tv_week = (TextView) view.findViewById(R.id.tv_week);
        tv_date = (TextView) view.findViewById(R.id.tv_date);
        init();
        final Calendar calendar = Calendar.getInstance();
        second = calendar.get(Calendar.SECOND);
        handler.sendMessageDelayed(handler.obtainMessage(),
                (60 - second) * 1000);
    }

    void init() {
        //java.text.DateFormat df = new java.text.SimpleDateFormat("HH:mm");
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        tv_date_time.setText(df.format(new Date()));
        //tv_week.setText(strHelpeUtil.getWeekOfDate(new Date()));
        //strHelpeUtil str = new strHelpeUtil(getContext());
        //tv_date.setText(str.toString());
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String t=format.format(new Date());
        tv_date.setText(t.toString());
    }

}
