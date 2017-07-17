package com.erobbing.erobbinglauncher.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.erobbing.erobbinglauncher.R;

public class WeatherView extends LinearLayout {

    private static final String Hour_COMPARE = "hour_compare";
    private static final String DAY_OF_WEEK = "day_of_week";
    private static final String LOW = "low";
    private static final String HIGH = "high";
    private static final String CONDITION = "condition";
    private static final String IMAGE = "image";
    private static final String DATE_COMPARE = "date_compare";
    private static final String CITYNAE_SHARE = "cityNameShare";

    private ImageView mIconWeather;
    //private TextView tv_state, tv_position, tv;
    private TextView mTemperature;
    private TextView mWeather;
    private TextView mLocation;

    /**
     * 自动加载天气
     */
    private boolean autoLoad = false;

    public boolean getAutoLoad() {
        return autoLoad;
    }

    public void setAutoLoad(boolean isLoad) {
        this.autoLoad = isLoad;
    }

    /**
     * 城市名称
     */
    private String cityName = "";

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * 设置每几小时更新一次
     */
    private int updateHour;

    public int getUpdateHour() {
        return updateHour;
    }

    public void setUpdateHour(int hour) {
        this.updateHour = hour;
    }

    public WeatherView(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub
    }

    public WeatherView(Context context, AttributeSet attrs) {
        super(context, attrs);

        View view = LayoutInflater.from(getContext()).inflate(
                R.layout.weather_view, this);

        mTemperature = (TextView) view.findViewById(R.id.temperature);
        Typeface typeFace = Typeface.createFromAsset(context.getAssets(), "fonts/Expansiva.ttf");
        mTemperature.setTypeface(typeFace);
        //mTemperature.getPaint().setFakeBoldText(true);//equals to android:textStyle="bold" in xml

        mIconWeather = (ImageView) view.findViewById(R.id.icon_weather);
        mWeather = (TextView) view.findViewById(R.id.weather);
        mLocation = (TextView) view.findViewById(R.id.location);
        init();//zhangzhaolei +
    }


    /**
     * 初始化组件 信息
     */
    void init() {
        mIconWeather.setImageDrawable(getResources().getDrawable(R.drawable.ic_weather_cleartoovercast));
        mWeather.setText("cloudy");
        mLocation.setText("qingdao");
        mTemperature.setText("32℃");
    }

    public void updateWeather(Drawable res, String temperature, String weather, String location) {
        if (res != null) {
            mIconWeather.setImageDrawable(res);
        }
        if (temperature != null) {
            mTemperature.setText(temperature);
        }
        if (weather != null) {
            mWeather.setText(weather);
        }
        if (location != null) {
            mLocation.setText(location);
        }
    }
}
