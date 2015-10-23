package com.example.egoistk.infinite_color.IfiniteColor;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.SystemClock;
import android.view.SurfaceHolder;

import com.example.egoistk.infinite_color.IActivity;
import com.example.egoistk.infinite_color.MainView;

/**
 * Created by liushimin on 15/10/17.
 */
public class GameView extends MainView implements SurfaceHolder.Callback {
	public Canvas canvas;
	public SurfaceHolder surfaceHolder;
	public GameContainer root;
	public GameView(IActivity iActivity) {
		super(iActivity);
		root = new GameContainer();
		getHolder().addCallback(this);
		surfaceHolder = getHolder();
	}
	public void draw() {
		Canvas canvas = surfaceHolder.lockCanvas();
		if(canvas != null){
			root.width = canvas.getWidth();
			root.height = canvas.getHeight();
			canvas.drawColor(Color.WHITE);
			root.draw(canvas);
			surfaceHolder.unlockCanvasAndPost(canvas);

		}

	}
	public void startGame() {

	}

	public void pauseGame() {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		startGame();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		long ago = SystemClock.currentThreadTimeMillis();
				/*System.out.println("本次绘制耗时" + (ago - before));*/
		while((int)(SystemClock.currentThreadTimeMillis() - ago) <=1000/30) {
			/**线程等待**/
			Thread.yield();
		}

	}
}


