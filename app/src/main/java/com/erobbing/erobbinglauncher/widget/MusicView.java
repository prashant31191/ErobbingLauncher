package com.erobbing.erobbinglauncher.widget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.erobbing.erobbinglauncher.widget.CircleProgress.CircleProgress;
import com.erobbing.erobbinglauncher.widget.CircleProgress.CenterText;
import com.erobbing.erobbinglauncher.widget.CircleProgress.CenterImage;

import com.erobbing.erobbinglauncher.R;

/**
 * Created by zhangzhaolei on 2017/7/5.
 */

public class MusicView extends FrameLayout {

    private TextView mTitle;
    private TextView mAuthor;
    private CircleProgress mAlbum;

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            init();
            handler.sendMessageDelayed(handler.obtainMessage(), 60 * 1000);
        }

        ;
    };

    public MusicView(Context context) {
        this(context, null);
    }


    public MusicView(Context context, AttributeSet attrs) {
        super(context, attrs);

        View view = LayoutInflater.from(getContext()).inflate(
                R.layout.music_view, this);

        mTitle = (TextView) view.findViewById(R.id.title);
        mAuthor = (TextView) view.findViewById(R.id.author);
        mAlbum = (CircleProgress) view.findViewById(R.id.icon_album);
        init();
    }

    void init() {
        mTitle.setText("The Dawn");
        mAuthor.setText("world of war craft");
        mAlbum.setOnCenterDraw(new CenterImage(getContext(), R.drawable.default_album));
        mAlbum.setProgress(10);
    }

    public void updateMusicInfo(String title, String author, int resId) {
        if (resId != 0) {
            mAlbum.setOnCenterDraw(new CenterImage(getContext(), resId));
        }
        if (title != null) {
            mTitle.setText(title);
        }
        if (author != null) {
            mAuthor.setText(author);
        }
    }

    public void updateMusicProgress(int progress) {
        mAlbum.setProgress(progress);
    }
}

