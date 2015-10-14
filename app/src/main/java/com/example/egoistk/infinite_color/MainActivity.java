package com.example.egoistk.infinite_color;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_gallery, btn_about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        setContentView(R.layout.activity_main);

        btn_gallery  = (Button) findViewById(R.id.btn_gallery);
        btn_about    = (Button) findViewById(R.id.btn_about);
        btn_gallery.setOnClickListener(this);
        btn_about.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_gallery:
                startActivity(new Intent(MainActivity.this, GalleryActivity.class));
                break;
            case R.id.btn_about:
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                break;

        }
    }
}
