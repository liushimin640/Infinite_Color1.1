package com.example.egoistk.infinite_color;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private FrameLayout root;
    private MainLaunchView mainLaunchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = new FrameLayout(this);
        mainLaunchView = new MainLaunchView(this);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        setContentView(root);
        root.addView(mainLaunchView, FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);


        mainLaunchView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

    }
}
