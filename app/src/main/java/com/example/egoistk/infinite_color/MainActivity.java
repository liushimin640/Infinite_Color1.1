package com.example.egoistk.infinite_color;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.internal.view.menu.MenuView;
import android.view.Menu;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.egoistk.infinite_color.About.AboutView;
import com.example.egoistk.infinite_color.ColorBox.BoxView;
import com.example.egoistk.infinite_color.IfiniteColor.GameView;
import com.example.egoistk.infinite_color.Launch.MainLaunchView;

public class MainActivity extends Activity implements IActivity{

    public boolean waitToChange;
    public FrameLayout frameLayout;
    public GameView gameView;
    public AboutView aboutView;
    public BoxView boxView;
    public com.example.egoistk.infinite_color.Menu.MenuView menuView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        this.getWindow().setFormat(PixelFormat.TRANSLUCENT);
        frameLayout = new FrameLayout(this);
        frameLayout.setBackgroundColor(0x00000000);
        frameLayout.addView(new SurfaceView(this),0,0);
        setContentView(frameLayout);
        waitToChange = false;
        menuView = new com.example.egoistk.infinite_color.Menu.MenuView(this);
        gameView = new GameView(this);
        aboutView = new AboutView(this);
        boxView = new BoxView(this);
        changeView(menuView,null);
        waitToChange = true;
    }

    @Override
    public Context getContext() {
        return this.getApplicationContext();
    }


    @Override
    public void changeView(View view,View self) {
        while (waitToChange) {
            Thread.yield();
        }
        frameLayout.addView(view,0,0);
        frameLayout.removeView(self);
        frameLayout.addView(view); //注意1
        waitToChange = true;
    }
    public IActivity getThis(){return this;}
}
