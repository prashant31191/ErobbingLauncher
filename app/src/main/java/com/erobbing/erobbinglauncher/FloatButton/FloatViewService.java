package com.erobbing.erobbinglauncher.FloatButton;

/**
 * Created by zhangzhaolei on 2017/9/15.
 */

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.erobbing.erobbinglauncher.ApplicationGrid;
import com.erobbing.erobbinglauncher.R;

public class FloatViewService extends Service {

    private static final String TAG = "FloatViewService";
    private LinearLayout mFloatLayout;
    private WindowManager.LayoutParams wmParams;
    private WindowManager mWindowManager;
    private ImageButton mFloatView;

    @Override
    public void onCreate() {
        super.onCreate();
        createFloatView();
    }

    @SuppressWarnings("static-access")
    @SuppressLint("InflateParams")
    private void createFloatView() {
        wmParams = new WindowManager.LayoutParams();
        mWindowManager = (WindowManager) getApplication().getSystemService(getApplication().WINDOW_SERVICE);
        //wmParams.type = LayoutParams.TYPE_PHONE;
        wmParams.type = LayoutParams.TYPE_TOAST;
        wmParams.format = PixelFormat.RGBA_8888;
        wmParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        wmParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        wmParams.x = 0;
        wmParams.y = 0;

        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;//getResources().getDimensionPixelSize(R.dimen.float_button_width);//WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;//getResources().getDimensionPixelSize(R.dimen.float_button_height);//WindowManager.LayoutParams.WRAP_CONTENT;

        LayoutInflater inflater = LayoutInflater.from(getApplication());
        mFloatLayout = (LinearLayout) inflater.inflate(R.layout.float_button, null);
        mWindowManager.addView(mFloatLayout, wmParams);
        mFloatView = (ImageButton) mFloatLayout.findViewById(R.id.float_button);

        mFloatLayout.measure(View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
                .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        mFloatView.setOnTouchListener(new OnTouchListener() {
            boolean isClick;

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //mFloatView.setBackgroundResource(R.drawable.default_album);
                        isClick = false;
                        break;
                    /*case MotionEvent.ACTION_MOVE:
                        isClick = true;
                        // getRawX是触摸位置相对于屏幕的坐标，getX是相对于按钮的坐标
                        wmParams.x = (int) event.getRawX()
                                - mFloatView.getMeasuredWidth() / 2;
                        // 减25为状态栏的高度
                        wmParams.y = (int) event.getRawY()
                                - mFloatView.getMeasuredHeight() / 2 - 75;
                        // 刷新
                        mWindowManager.updateViewLayout(mFloatLayout, wmParams);
                        return true;*/
                    case MotionEvent.ACTION_UP:
                        //mFloatView.setBackgroundResource(R.drawable.ic_weather_cleartoovercast);
                        return isClick;// 此处返回false则属于移动事件，返回true则释放事件，可以出发点击否。

                    default:
                        break;
                }
                return false;
            }
        });

        mFloatView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(FloatViewService.this, "一百块都不给我！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FloatViewService.this, ApplicationGrid.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mFloatLayout != null) {
            mWindowManager.removeView(mFloatLayout);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

