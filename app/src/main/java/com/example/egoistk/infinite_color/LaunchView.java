package com.example.egoistk.infinite_color;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

/**
 * Created by liushimin on 15/10/11.
 */
public class LaunchView extends SurfaceView implements Callback{
	private Container root;
	public LaunchView(Context context) {
		super(context);
		root = new Container();
		getHolder().addCallback(this);
	}

	public void draw(){
		Canvas canvas = getHolder().lockCanvas();
		if(canvas != null){
			canvas.drawColor(Color.WHITE);
			root.draw(canvas);
			getHolder().unlockCanvasAndPost(canvas);
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
		draw();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

	}
}
