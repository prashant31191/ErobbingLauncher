package com.erobbing.erobbinglauncher;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.erobbing.erobbinglauncher.FloatButton.FloatViewService;
import com.erobbing.erobbinglauncher.widget.MusicView;
import com.erobbing.erobbinglauncher.widget.WeatherView;
import com.erobbing.erobbinglauncher.widget.CircleProgress.CircleProgress;
import com.erobbing.erobbinglauncher.widget.CircleProgress.CenterText;
import com.erobbing.erobbinglauncher.widget.CircleProgress.CenterImage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
import com.android.mltcode.ferace.weatherforecast.IWeatherInterface;

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

    private static final String WEATHER_AIDL_PACKNAME = "com.android.mltcode.ferace.weatherforecast";
    private static final String WEATHER_ACTION_SERVICES = "com.android.hoinnet.weather.forecast";
    private static final String WEATHER_UP_SUCCES = "android.mltcode.ferace.weather_up_succes";
    private static final String WEATHER_LIST = "weather_list";
    private static final String TAG = "ErobbingLauncher";
    private ServiceConnection mServiceConnection;
    private IWeatherInterface mIWeatherInterface;
    private boolean isConnected = false;
    private IntentFilter mFilter;
    private LinearLayout mWeatherLayout;

    private static final int MSG_WEATHER_UPDATE = 0;
    Handler mWeatherUpdateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_WEATHER_UPDATE:
                    getWeatherList();
                    break;
            }
        }
    };

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
        mWeatherView.updateWeather(getResources().getDrawable(R.drawable.ic_weather_overcast), "26℃", "多云", "青岛市");

        mMusicView = (MusicView) findViewById(R.id.musicview);
        mMusicView.updateMusicInfo("In The End", "Linkin Park", R.drawable.default_album);
        mMusicView.updateMusicProgress(66);

        mWeatherLayout = (LinearLayout) findViewById(R.id.weather_layout);
        mWeatherLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "====mWeatherLayout.onClick");
                mWeatherUpdateHandler.sendEmptyMessageDelayed(MSG_WEATHER_UPDATE, 100);
            }
        });

        //mCircleProgress = (CircleProgress) findViewById(R.id.circle_progress);
        //mCircleProgress.setOnCenterDraw(new CenterImage(this,R.drawable.icon_feature_file_browser));
        //mCircleProgress.setProgress(70);
        bindWeatherService();
        mFilter = new IntentFilter();
        mFilter.addAction(WEATHER_UP_SUCCES);
        registerReceiver(mWeatherReceiver, mFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //registerReceiver(mWeatherReceiver, mFilter);
        Log.d(TAG, "====onResume");
        mWeatherUpdateHandler.sendEmptyMessageDelayed(MSG_WEATHER_UPDATE, 100);
        startService(new Intent(MainActivity.this, FloatViewService.class));
    }

    @Override
    protected void onPause() {
        super.onPause();
        //unregisterReceiver(mWeatherReceiver);
        stopService(new Intent(MainActivity.this, FloatViewService.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWeatherReceiver != null) {
            unregisterReceiver(mWeatherReceiver);
        }
        if (mServiceConnection != null) {
            unbindService(mServiceConnection);
        }
    }

    public void btnClick(View v) {
        if (isButtonClick) {
            //Toast.makeText(MainActivity.this, ((Button) v).getId() + "", Toast.LENGTH_SHORT).show();
            switch (((Button) v).getId()) {
                case R.id.button_navigation:
                case R.id.button_navigation1:
                case R.id.button_navigation2:
                    startActivity(new Intent().setClassName("com.autonavi.amapautolite", "com.autonavi.auto.remote.fill.UsbFillActivity"));
                    break;
                case R.id.button_music:
                case R.id.button_music1:
                case R.id.button_music2:
                    startActivity(new Intent().setClassName("cn.kuwo.kwmusiccar", "cn.kuwo.kwmusiccar.WelcomeActivity"));
                    break;
                case R.id.button_fm:
                case R.id.button_fm1:
                case R.id.button_fm2:
                    startActivity(new Intent().setClassName("com.caf.fmradio", "com.caf.fmradio.MainActivity"));
                    break;
                case R.id.button_dvr:
                case R.id.button_dvr1:
                case R.id.button_dvr2:
                    startActivity(new Intent().setClassName("com.luobin.dvr", "com.luobin.dvr.ui.MainActivity"));
                    //Toast.makeText(this, getResources().getString(R.string.toast_todo), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.button_btdialer:
                case R.id.button_btdialer1:
                case R.id.button_btdialer2:
                    startActivity(new Intent().setClassName("com.hmct.bluetoothdialer", "com.hmct.bluetoothdialer.MainActivity"));
                    break;
                case R.id.button_carassistant:
                case R.id.button_carassistant1:
                case R.id.button_carassistant2:
                    Toast.makeText(this, getResources().getString(R.string.toast_todo), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, FloatViewService.class);
                    startService(intent);
                    break;
                case R.id.button_msg:
                case R.id.button_msg1:
                case R.id.button_msg2:
                    Toast.makeText(this, getResources().getString(R.string.toast_todo), Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(MainActivity.this, FloatViewService.class);
                    stopService(intent1);
                    break;
                case R.id.button_settings:
                case R.id.button_settings1:
                case R.id.button_settings2:
                    startActivity(new Intent().setClassName("com.android.settings", "com.android.settings.Settings"));
                    break;
            }
        }
        Log.d(TAG, "====btnClick=" + isButtonClick);
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
                Log.d(TAG, "====onTouch-touch down");
                startTime = System.currentTimeMillis();
                startX = event.getX();
                startY = event.getY();
                return false;  //1 FALSE
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                Log.d(TAG, "====onTouch-touch move");
                //x = event.getX();
                //y = event.getY();
                isClick = true;
                return false;  //2 FALSE
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                Log.d(TAG, "====onTouch-touch up");
                endTime = System.currentTimeMillis();
                long time = endTime - startTime;
                //long time = System.currentTimeMillis() - startTime;
                if (Math.abs(event.getX() - startX) > 5 || (Math.abs(event.getY() - startY)) > 5) {
                    isButtonClick = false;
                } else {
                    isButtonClick = true;
                }
                Log.d(TAG, "====ACTION_UP=move=" + Math.abs(event.getX() - startX) + "-" + Math.abs(event.getY() - startY));
                return false;  //3 FALSE
            }
            return false;
        }
    };

    private void bindWeatherService() {
        Intent intent = new Intent();
        intent.setAction(WEATHER_ACTION_SERVICES);
        intent.setPackage(WEATHER_AIDL_PACKNAME);
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mIWeatherInterface = IWeatherInterface.Stub.asInterface(service);
                isConnected = true;
                Log.d(TAG, "====bindWeatherService-connected=" + isConnected);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                isConnected = false;
                Log.d(TAG, "====bindWeatherService-disconnected=" + isConnected);
            }
        };
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private BroadcastReceiver mWeatherReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG, "====mWeatherReceiver-action:" + action);
            if (WEATHER_UP_SUCCES.equals(action)) {
                /*String string = intent.getStringExtra(WEATHER_LIST);
                Log.e("====", "广播接收到天气信息：" + string);
                JSONArray jsonArray = JSON.parseArray(string);  //转换成 JSonArray
                Log.e("====", "天气数量：" + jsonArray.size());
                mWeatherView.updateWeather(getResources().getDrawable(iconId), currTemp + "℃", weather, city);*/
                mWeatherUpdateHandler.sendEmptyMessageDelayed(MSG_WEATHER_UPDATE, 100);
            }
        }
    };

    /**
     * @desc get weather info from hoin server
     * @author zhangzhaolei@erobbing.com
     */
    private void getWeatherList() {
        try {
            String string = mIWeatherInterface.getWeatherList();
            Log.d(TAG, "主动查询的天气：" + string);
            JSONArray jsonArray = JSON.parseArray(string);  //转换成 JSonArray
            //for (int i = 0; i < jsonArray.size(); i++) {
            String city = jsonArray.getJSONObject(0).getString("cityName");
            String currTemp = jsonArray.getJSONObject(0).getString("currTemp");
            String weather = jsonArray.getJSONObject(0).getString("weather");
            String icon = jsonArray.getJSONObject(0).getString("icon");
            int iconId = getIconWeather(icon);
            //}
            Log.d(TAG, "主动查询到的天气数量：" + jsonArray.size());
            //mWeatherView.updateWeather(getResources().getDrawable(R.drawable.ic_weather_overcast), "26℃", "多云", "青岛市");
            mWeatherView.updateWeather(getResources().getDrawable(iconId), currTemp + "℃", weather, city);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param icon
     * @return id
     * @desc 获取到的天气图标子串转换成本地图片的id
     * @author zhangzhaolei@erobbing.com
     */
    private int getIconWeather(String icon) {
        switch (icon) {
            case "Cleartoovercast.png":
                return R.drawable.ic_weather_cleartoovercast;
            case "Cloudy.png":
                return R.drawable.ic_weather_cloudy;
            case "Cloudytoclear.png":
                return R.drawable.ic_weather_cloudytoclear;
            case "Downfall.png":
                return R.drawable.ic_weather_downfall;
            case "Drizzle.png":
                return R.drawable.ic_weather_drizzle;
            case "Duststorm.png":
                return R.drawable.ic_weather_duststorm;
            case "Fair.png":
                return R.drawable.ic_weather_fair;
            case "Flurries.png":
                return R.drawable.ic_weather_flurries;
            case "Foggy.png":
                return R.drawable.ic_weather_foggy;
            case "HeavySnow.png":
                return R.drawable.ic_weather_heavysnow;
            case "MidRain.png":
                return R.drawable.ic_weather_midrain;
            case "MidSnow.png":
                return R.drawable.ic_weather_midsnow;
            case "Overcast.png":
                return R.drawable.ic_weather_overcast;
            case "RainShower.png":
                return R.drawable.ic_weather_rainshower;
            case "RainStome.png":
                return R.drawable.ic_weather_rainstome;
            case "RainStomeToBig.png":
                return R.drawable.ic_weather_rainstometobig;
            case "Sandstorm.png":
                return R.drawable.ic_weather_sandstorm;
            case "Sleet.png":
                return R.drawable.ic_weather_sleet;
            case "ThunderyShower.png":
                return R.drawable.ic_weather_thunderyshower;
        }
        return R.drawable.ic_weather_fair;
    }
}
