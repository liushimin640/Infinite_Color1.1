package com.example.egoistk.infinite_color;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

/**
 * Created by liushimin on 15/10/11.
 */
public class LaunchView extends SurfaceView implements Callback {
	public Container root;
	public Canvas canvas;
	public SurfaceHolder surfaceHolder;
	public LaunchView(Context context,AttributeSet attrs) {
		super(context, attrs);
		root = new Container();
		getHolder().addCallback(this);
		surfaceHolder = getHolder();

	}



	public void draw(){
		Canvas canvas = surfaceHolder.lockCanvas();
		System.out.println("锁定画布");
		if(canvas != null){
			root.width = canvas.getWidth();
			root.height = canvas.getHeight();
			System.out.println("开始绘制");
			canvas.drawColor(Color.WHITE);
			System.out.println("背景绘制完成");
			root.draw(canvas);
			System.out.println("绘制结束");
			surfaceHolder.unlockCanvasAndPost(canvas);
			System.out.println("解锁画布");

		}

	}

	public Container getRoot() {
		return root;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		draw(canvas);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

	}
}
