package com.erobbing.erobbinglauncher;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.erobbing.erobbinglauncher.widget.MusicView;
import com.erobbing.erobbinglauncher.widget.WeatherView;
import com.erobbing.erobbinglauncher.widget.CircleProgress.CircleProgress;
import com.erobbing.erobbinglauncher.widget.CircleProgress.CenterText;
import com.erobbing.erobbinglauncher.widget.CircleProgress.CenterImage;

public class MainActivity extends Activity {
    private Button buttonNavigation;
    private Button buttonNavigation1;
    private Button buttonNavigation2;
    private Button buttonMusic;
    private Button buttonMusic1;
    private Button buttonMusic2;
    private Button buttonFm;
    private Button buttonFm1;
    private Button buttonFm2;
    private Button buttonDvr;
    private Button buttonDvr1;
    private Button buttonDvr2;
    private Button buttonBtdialer;
    private Button buttonBtdialer1;
    private Button buttonBtdialer2;
    private Button buttonAssistant;
    private Button buttonAssistant1;
    private Button buttonAssistant2;
    private Button buttonMsg;
    private Button buttonMsg1;
    private Button buttonMsg2;
    private Button buttonSettings;
    private Button buttonSettings1;
    private Button buttonSettings2;
    private boolean isButtonClick = true;
    private long startTime;
    private long endTime;
    private float startX;
    private float startY;

    private WeatherView mWeatherView;
    private MusicView mMusicView;
    private CircleProgress mCircleProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_main);
        buttonNavigation = (Button) findViewById(R.id.button_navigation);
        buttonNavigation1 = (Button) findViewById(R.id.button_navigation1);
        buttonNavigation2 = (Button) findViewById(R.id.button_navigation2);
        buttonMusic = (Button) findViewById(R.id.button_music);
        buttonMusic1 = (Button) findViewById(R.id.button_music1);
        buttonMusic2 = (Button) findViewById(R.id.button_music2);
        buttonFm = (Button) findViewById(R.id.button_fm);
        buttonFm1 = (Button) findViewById(R.id.button_fm1);
        buttonFm2 = (Button) findViewById(R.id.button_fm2);
        buttonDvr = (Button) findViewById(R.id.button_dvr);
        buttonDvr1 = (Button) findViewById(R.id.button_dvr1);
        buttonDvr2 = (Button) findViewById(R.id.button_dvr2);
        buttonBtdialer = (Button) findViewById(R.id.button_btdialer);
        buttonBtdialer1 = (Button) findViewById(R.id.button_btdialer1);
        buttonBtdialer2 = (Button) findViewById(R.id.button_btdialer2);
        buttonAssistant = (Button) findViewById(R.id.button_carassistant);
        buttonAssistant1 = (Button) findViewById(R.id.button_carassistant1);
        buttonAssistant2 = (Button) findViewById(R.id.button_carassistant2);
        buttonMsg = (Button) findViewById(R.id.button_msg);
        buttonMsg1 = (Button) findViewById(R.id.button_msg1);
        buttonMsg2 = (Button) findViewById(R.id.button_msg2);
        buttonSettings = (Button) findViewById(R.id.button_settings);
        buttonSettings1 = (Button) findViewById(R.id.button_settings1);
        buttonSettings2 = (Button) findViewById(R.id.button_settings2);

        buttonNavigation.setOnTouchListener(mOnTouchListener);
        buttonNavigation1.setOnTouchListener(mOnTouchListener);
        buttonNavigation2.setOnTouchListener(mOnTouchListener);
        buttonMusic.setOnTouchListener(mOnTouchListener);
        buttonMusic1.setOnTouchListener(mOnTouchListener);
        buttonMusic2.setOnTouchListener(mOnTouchListener);
        buttonFm.setOnTouchListener(mOnTouchListener);
        buttonFm1.setOnTouchListener(mOnTouchListener);
        buttonFm2.setOnTouchListener(mOnTouchListener);
        buttonDvr.setOnTouchListener(mOnTouchListener);
        buttonDvr1.setOnTouchListener(mOnTouchListener);
        buttonDvr2.setOnTouchListener(mOnTouchListener);
        buttonBtdialer.setOnTouchListener(mOnTouchListener);
        buttonBtdialer1.setOnTouchListener(mOnTouchListener);
        buttonBtdialer2.setOnTouchListener(mOnTouchListener);
        buttonAssistant.setOnTouchListener(mOnTouchListener);
        buttonAssistant1.setOnTouchListener(mOnTouchListener);
        buttonAssistant2.setOnTouchListener(mOnTouchListener);
        buttonMsg.setOnTouchListener(mOnTouchListener);
        buttonMsg1.setOnTouchListener(mOnTouchListener);
        buttonMsg2.setOnTouchListener(mOnTouchListener);
        buttonSettings.setOnTouchListener(mOnTouchListener);
        buttonSettings1.setOnTouchListener(mOnTouchListener);
        buttonSettings2.setOnTouchListener(mOnTouchListener);

        mWeatherView = (WeatherView) findViewById(R.id.weatherview);
        mWeatherView.updateWeather(getResources().getDrawable(R.drawable.ic_weather_overcast), "26℃", "阵雨", "青岛市");

        mMusicView = (MusicView) findViewById(R.id.musicview);
        mMusicView.updateMusicInfo("In The End", "Linkin Park", R.drawable.icon_feature_wifi);
        mMusicView.updateMusicProgress(66);

        //mCircleProgress = (CircleProgress) findViewById(R.id.circle_progress);
        //mCircleProgress.setOnCenterDraw(new CenterImage(this,R.drawable.icon_feature_file_browser));
        //mCircleProgress.setProgress(70);
    }

    public void btnClick(View v) {
        if (isButtonClick) {
            Toast.makeText(MainActivity.this, ((Button) v).getId() + "", Toast.LENGTH_SHORT).show();
            switch (((Button) v).getId()) {
                case R.id.button_navigation:
                    Log.e("====", "==============button_navigation");
                    break;
                case R.id.button_navigation1:
                    Log.e("====", "==============button_navigation1");
                    break;
                case R.id.button_navigation2:
                    Log.e("====", "==============button_navigation2");
                    break;
                case R.id.button_music:
                    Log.e("====", "==============button_music");
                    break;
                case R.id.button_music1:
                    Log.e("====", "==============button_music1");
                    break;
                case R.id.button_music2:
                    Log.e("====", "==============button_music2");
                    break;
                case R.id.button_fm:
                    Log.e("====", "==============button_fm");
                    break;
                case R.id.button_fm1:
                    Log.e("====", "==============button_fm1");
                    break;
                case R.id.button_fm2:
                    Log.e("====", "==============button_fm2");
                    break;
                case R.id.button_dvr:
                    Log.e("====", "==============button_dvr");
                    break;
                case R.id.button_dvr1:
                    Log.e("====", "==============button_dvr1");
                    break;
                case R.id.button_dvr2:
                    Log.e("====", "==============button_dvr2");
                    break;
                case R.id.button_btdialer:
                    Log.e("====", "==============button_btdialer");
                    break;
                case R.id.button_btdialer1:
                    Log.e("====", "==============button_btdialer1");
                    break;
                case R.id.button_btdialer2:
                    Log.e("====", "==============button_btdialer2");
                    break;
                case R.id.button_carassistant:
                    Log.e("====", "==============button_carassistant");
                    break;
                case R.id.button_carassistant1:
                    Log.e("====", "==============button_carassistant1");
                    break;
                case R.id.button_carassistant2:
                    Log.e("====", "==============button_carassistant2");
                    break;
                case R.id.button_msg:
                    Log.e("====", "==============button_msg");
                    break;
                case R.id.button_msg1:
                    Log.e("====", "==============button_msg1");
                    break;
                case R.id.button_msg2:
                    Log.e("====", "==============button_msg2");
                    break;
                case R.id.button_settings:
                    Log.e("====", "==============button_settings");
                    break;
                case R.id.button_settings1:
                    Log.e("====", "==============button_settings1");
                    break;
                case R.id.button_settings2:
                    Log.e("====", "==============button_settings2");
                    break;
            }
        }
        Log.e("====", "=============btnClick=" + isButtonClick);
    }

    View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            boolean isClick = false;
            //long startTime = System.currentTimeMillis();
            //long endTime = 0l;
            float x = 0;
            float y = 0;
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Log.e("====", "touch down");
                startTime = System.currentTimeMillis();
                startX = event.getX();
                startY = event.getY();
                return false;  //1 FALSE
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                Log.e("====", "touch move");
                //x = event.getX();
                //y = event.getY();
                isClick = true;
                return false;  //2 FALSE
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                Log.e("====", "touch up");
                endTime = System.currentTimeMillis();
                long time = endTime - startTime;
                //long time = System.currentTimeMillis() - startTime;
                Log.e("====", "=============time=" + time);
                if (Math.abs(event.getX() - startX) > 5 || (Math.abs(event.getY() - startY)) > 5) {
                    isButtonClick = false;
                } else {
                    isButtonClick = true;
                }
                Log.e("====", "==============ACTION_UP=move=" + Math.abs(event.getX() - startX) + "---" + Math.abs(event.getY() - startY));
                return false;  //3 FALSE
            }
            return false;
        }
    };
}
