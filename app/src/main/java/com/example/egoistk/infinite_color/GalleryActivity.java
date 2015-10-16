package com.example.egoistk.infinite_color;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class GalleryActivity extends AppCompatActivity implements View.OnClickListener {
    private MainLaunchView mainLaunchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        setContentView(R.layout.activity_gallery);
        mainLaunchView = (MainLaunchView) findViewById(R.id.mainLaunch);
        mainLaunchView.setOnTouchListener(null);

    }

    @Override
    public void onClick(View v) {
        MainLaunchView.BombThread bombThread = mainLaunchView.new BombThread(mainLaunchView);
        new Thread(bombThread).start();//小圆圈开始爆炸
        //启动跳转动画
        new Thread(new Change4ClickThread(v)).start();
        //跳转界面
    }

    private class Change4ClickThread implements Runnable {
        public View view;

        public Change4ClickThread(View v) {
            this.view = v;
        }

        @Override
        public void run() {
            mainLaunchView.setOnTouchListener(null);


            while (!mainLaunchView.bombOver) {

                /**线程等待**/
                Thread.yield();
            }
            switch (view.getId()) {
                //待跳转
            }

        }
    }




    @Override
    protected void onPause() {
        MainLaunchView.BombThread bombThread = mainLaunchView.new BombThread(mainLaunchView);
        new Thread(bombThread).start();//小圆圈开始爆炸
        while(!mainLaunchView.bombOver) {
            /**线程等待**/
            Thread.yield();
        }

        super.onPause();
    }
}
