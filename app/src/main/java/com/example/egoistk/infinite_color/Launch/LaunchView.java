package com.example.egoistk.infinite_color.Launch;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;

import com.example.egoistk.infinite_color.IActivity;
import com.example.egoistk.infinite_color.MainView;

/**
 * Created by liushimin on 15/10/11.
 */
public class LaunchView extends MainView implements Callback {
	public LaunchContainer root;
	public Canvas canvas;
	public SurfaceHolder surfaceHolder;
	public LaunchView(IActivity iActivity) {
		super(iActivity);
		root = new LaunchContainer();
		getHolder().addCallback(this);
		surfaceHolder = getHolder();
		draw();
	}


	@Override
	public void draw(){
		Canvas canvas = surfaceHolder.lockCanvas();
		if(canvas != null){
			this.root.width = canvas.getWidth();
			this.root.height = canvas.getHeight();
			canvas.drawColor(Color.WHITE);
			this.root.draw(canvas);
			surfaceHolder.unlockCanvasAndPost(canvas);
		}
		System.out.println("一帧画面");
	}

	public LaunchContainer getRoot() {
		return this.root;
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
