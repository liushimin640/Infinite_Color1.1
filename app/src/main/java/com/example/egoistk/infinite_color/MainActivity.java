package com.example.egoistk.infinite_color;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        ibtn_gallery.setOnClickListener(this);
        ibtn_about.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        MainLaunchView.BombThread bombThread = mainLaunchView.new BombThread(mainLaunchView);
        new Thread(bombThread).start();//小圆圈开始爆炸
        //等待2秒动画
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                }catch (Exception e){
                   Thread.currentThread().interrupt();
                }
            }
        }).start();
        //跳转界面
        switch (v.getId()) {
            case R.id.ibtn_gallery:
                startActivity(new Intent(MainActivity.this, GalleryActivity.class));
                break;
            case R.id.ibtn_about:
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                break;

        }
    }
}
