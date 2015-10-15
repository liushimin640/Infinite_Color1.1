package com.example.egoistk.infinite_color;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton ibtn_gallery, ibtn_about;
    private MainLaunchView mainLaunchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        setContentView(R.layout.activity_main);

        mainLaunchView = (MainLaunchView)findViewById(R.id.mainLaunch);
        ibtn_gallery  = (ImageButton) findViewById(R.id.ibtn_gallery);
        ibtn_about    = (ImageButton) findViewById(R.id.ibtn_about);
        mainLaunchView.setOnClickListener(this);
        ibtn_gallery.setOnClickListener(this);
        ibtn_about.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        MainLaunchView.BombThread bombThread = mainLaunchView.new BombThread(mainLaunchView);
        new Thread(bombThread).start();//小圆圈开始爆炸
        //启动跳转动画
        new Thread(new Change4ClickThread(v)).start();
        //跳转界面
    }

    private class Change4ClickThread implements Runnable{
        public View view;
        public Change4ClickThread(View v) {
            this.view = v;
        }

        @Override
        public void run() {
            mainLaunchView.setOnTouchListener(null);
            ibtn_gallery.setOnClickListener(null);
            ibtn_about.setOnClickListener(null);
            //这里把onClickListener置空，防止连续点击；

            while(!mainLaunchView.bombOver) {

                /**线程等待**/
                Thread.yield();
            }
            switch (view.getId()) {
                case R.id.ibtn_gallery:
                    startActivity(new Intent(MainActivity.this, GalleryActivity.class));
                    break;
                case R.id.ibtn_about:
                    startActivity(new Intent(MainActivity.this, AboutActivity.class));
                    break;

            }
            ibtn_gallery.setOnClickListener(getMe());
            ibtn_about.setOnClickListener(getMe());
            mainLaunchView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    mainLaunchView.OnTouchView(event.getX(), event.getY());
                    new Thread(mainLaunchView.new Change4ClickThread()).start();
                    mainLaunchView.hasLoaded = false;
                    return false;
                }
            });
        }
    }
    public MainActivity getMe(){
        return this;
    }
}
