package com.example.egoistk.infinite_color;

import android.content.Context;
import android.content.pm.LauncherApps;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by liushimin on 15/10/21.
 */
public class MainView extends SurfaceView implements SurfaceHolder.Callback {
	public IActivity iActivity;
	public MainActivity mainActivity;
	public Canvas canvas;
	public SurfaceHolder surfaceHolder;
	public Container root;
	public MainView (IActivity iActivity)
	{
		super(iActivity.getContext());
		this. iActivity = iActivity;
		this.mainActivity = (MainActivity)iActivity;
		getHolder().addCallback(this);
		setBackgroundColor(Color.WHITE);
		setZOrderOnTop(true);
		getHolder().setFormat(PixelFormat.TRANSPARENT);

	}



	public void draw(){
		Canvas canvas = surfaceHolder.lockCanvas();
		if(canvas != null){
			root.width = canvas.getWidth();
			root.height = canvas.getHeight();
			canvas.drawColor(Color.WHITE);
			root.draw(canvas);
			surfaceHolder.unlockCanvasAndPost(canvas);

		}

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

