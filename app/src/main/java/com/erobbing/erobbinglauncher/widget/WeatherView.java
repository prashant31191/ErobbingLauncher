package com.erobbing.erobbinglauncher.widget;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
//import org.apache.commons.codec.binary.Base64;
import android.util.Base64;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.erobbing.erobbinglauncher.R;
//import com.yaomei.adapter.weatherAdapter;
//import com.yaomei.model.WeatherMdoel;
//import com.yaomei.util.strHelpeUtil;

public class WeatherView extends LinearLayout {

    private static final String Hour_COMPARE = "hour_compare";
    private static final String DAY_OF_WEEK = "day_of_week";
    private static final String LOW = "low";
    private static final String HIGH = "high";
    private static final String CONDITION = "condition";
    private static final String IMAGE = "image";
    private static final String DATE_COMPARE = "date_compare";
    private static final String CITYNAE_SHARE = "cityNameShare";

    private ImageView iv_weather;
    private TextView tv_state, tv_position, tv;

    //WeatherMdoel model;
    //private List<WeatherMdoel> weatherList = null;
    //GridView gv;
    Timer timer;
    /*Handler handler = new Handler() {
        public void handleMessage(Message msg) {
			if (msg.arg1 == 1) {
				if (weatherList.size() > 0) {
					gv
							.setAdapter(new weatherAdapter(getContext(),
									weatherList));
					init();
				} else {
					Toast.makeText(getContext(), "查询不到数据", Toast.LENGTH_SHORT).show();
				}
				// msg.recycle();
			}
		};
	};*/

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
        /*int resouceID = -1;
        TypedArray tyedArray = context.obtainStyledAttributes(attrs,
                R.styleable.WeatherView);
        int N = tyedArray.getIndexCount();
        for (int i = 0; i < N; i++) {
            int attr = tyedArray.getIndex(i);
            switch (attr) {
                case R.styleable.WeatherView_AutoLoad:
                    setAutoLoad(tyedArray.getBoolean(
                            R.styleable.WeatherView_AutoLoad, false));
                    break;

                case R.styleable.WeatherView_CityName:
                    resouceID = tyedArray.getResourceId(
                            R.styleable.WeatherView_CityName, 0);
                    setCityName(resouceID > 0 ? tyedArray.getResources().getText(
                            resouceID).toString() : tyedArray
                            .getString(R.styleable.WeatherView_CityName));
                    break;
                case R.styleable.WeatherView_UpdateHour:
                    setUpdateHour(tyedArray.getInteger(
                            R.styleable.WeatherView_UpdateHour, 3));
                    break;
            }
        }*/

        View view = LayoutInflater.from(getContext()).inflate(
                R.layout.weather_view, this);

        tv = (TextView) view.findViewById(R.id.tv_temperature);

        //gv = (GridView) view.findViewById(R.id.grid);
        iv_weather = (ImageView) view.findViewById(R.id.iv_weather);
        tv_state = (TextView) view.findViewById(R.id.tv_state);
        tv_position = (TextView) view.findViewById(R.id.tv_position);
        //timer = new Timer();
        init();//zhangzhaolei +
        //if (getAutoLoad()) {
        //startLoadWeather();
        //}
        //tyedArray.recycle();
    }

    /**
     * 开始加载
     */
    /*public void startLoadWeather() {
        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                SharedPreferences share = getContext().getSharedPreferences(
                        "weather", Activity.MODE_PRIVATE);
                long time = System.currentTimeMillis();
                final Calendar mCalendar = Calendar.getInstance();
                mCalendar.setTimeInMillis(time);
                String tempDate = mCalendar.get(Calendar.YEAR) + "-"
                        + mCalendar.get(Calendar.MONTH) + "-"
                        + mCalendar.get(Calendar.DAY_OF_MONTH);
                if (share.contains(DATE_COMPARE)) {
                    if (share.getString(CITYNAE_SHARE, "").equals(cityName)) {
                        int time_cop = mCalendar.get(Calendar.HOUR)
                                - share.getInt(Hour_COMPARE, 0);
                        String date = share.getString(DATE_COMPARE, "");

                        if (time_cop >= getUpdateHour()
                                || !date.equals(tempDate)) {
                            //saveWeatherList(mCalendar.get(Calendar.HOUR),tempDate);
                        } else if (time_cop < getUpdateHour()) {
                            weatherList = new ArrayList<WeatherMdoel>();
                            for (int i = 0; i < 4; i++) {
                                WeatherMdoel model = new WeatherMdoel();
                                model.setWeek(share.getString(DAY_OF_WEEK + i,
                                        ""));
                                model.setLowTemp(share.getString(LOW + i, ""));
                                model
                                        .setHighTemp(share.getString(HIGH + i,
                                                ""));
                                model.setConditions(share.getString(CONDITION
                                        + i, ""));
                                String image = share.getString(IMAGE + i, "");
                                //byte[] base64Bytes = Base64.decodeBase64(image.getBytes());
                                //ByteArrayInputStream bais = new ByteArrayInputStream(
                                //        base64Bytes);
                                model.setImageUrl("");
                                //model.setImageDrawable(Drawable
                                //        .createFromStream(bais,
                                //                "weather_image"));
                                model.setImageDrawable(getResources().getDrawable(R.drawable.icon));

                                weatherList.add(model);
                            }
                        }
                    } else {
                        //saveWeatherList(mCalendar.get(Calendar.HOUR), tempDate);
                    }

                } else {
                    //saveWeatherList(mCalendar.get(Calendar.HOUR), tempDate);
                }
                // 把必要的操作放在于线程中执行，不阻塞UI
                if (handler.hasMessages(1)) {
                    handler.obtainMessage().recycle();
                } else {
                    Message msg = handler.obtainMessage();
                    msg.arg1 = 1;
                    msg.sendToTarget();
                }
            }
        }, 0, getUpdateHour() * 3600 * 1000);
    }*/

    /**
     * 第一次或者另外重新加载
     */
    /*void saveWeatherList(int hour, String day) {
        weatherList = new ArrayList<WeatherMdoel>();
        weatherList = strHelpeUtil.searchWeather(Html.fromHtml(
                getContext().getResources()
                        .getString(R.string.googleWeatherApi)).toString(),
                getCityName());

        SharedPreferences.Editor shareEditor = getContext()
                .getSharedPreferences("weather", Activity.MODE_PRIVATE).edit();
        shareEditor.clear();
        int i = 0;
        for (WeatherMdoel model : weatherList) {

            shareEditor.putString(DAY_OF_WEEK + i, model.getWeek());
            shareEditor.putString(LOW + i, model.getLowTemp());
            shareEditor.putString(HIGH + i, model.getHighTemp());
            shareEditor.putString(CONDITION + i, model.getConditions());

              //将图片存入

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ((BitmapDrawable) strHelpeUtil.loadImage(model.getImageUrl()))
                    .getBitmap().compress(CompressFormat.JPEG, 50, baos);

            String ImageBase64 = new String(Base64.encodeBase64(baos
                    .toByteArray()));
            shareEditor.putString(IMAGE + i, ImageBase64);
            i++;
        }
        shareEditor.putString(DATE_COMPARE, day);
        shareEditor.putInt(Hour_COMPARE, hour);
        shareEditor.putString(CITYNAE_SHARE, cityName);
        shareEditor.commit();
    }*/

    /**
     * 初始化组件 信息
     */
    void init() {
        //model = weatherList.get(0);
        /*iv_weather.setImageDrawable(model.getImageUrl() == "" ? model
                .getImageDrawable() : strHelpeUtil.loadImage(model
                .getImageUrl()));
        tv_state.setText(model.getConditions());
        tv_position.setText(getCityName());
        tv.setText(getContext().getResources().getString(R.string.temp_format,
                model.getLowTemp(), model.getHighTemp()));*/
        iv_weather.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
        tv_state.setText("cloudy");
        tv_position.setText("qingdao");
        tv.setText("32℃");
    }

    public void updateWeather(Drawable res, String temperature, String weather, String location) {
        if (res != null) {
            iv_weather.setImageDrawable(res);
        }
        if (temperature != null) {
            tv.setText(temperature);
        }
        if (weather != null) {
            tv_state.setText(weather);
        }
        if (location != null) {
            tv_position.setText(location);
        }
    }

    /**
     * 释放对象
     */
    /*public void releaseTimer() {
        timer.cancel();
        weatherList = null;
    }*/

}
