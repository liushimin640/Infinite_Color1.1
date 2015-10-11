package com.example.egoistk.infinite_color;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by liushimin on 15/10/11.
 */
public class ColorfulPix extends Container{
	private Paint paint;
	private int color;
	private boolean onActive;
	private boolean onBomb;
	private DrawBombThread dbThread = null;

	public ColorfulPix(float x,float y,float width,float height,int color) {
		paint = new Paint();
		setX(x);
		setY(y);
		setHeight(height);
		setWidth(width);
		setColor(color);
		onActive = true;
		onBomb = false;
	}


	@Override
	public void costomChildren(Canvas canvas) {
		super.costomChildren(canvas);
		if(isOnActive()){
			drawOnActive(canvas);
		}else {
			drawOnBomb(canvas);
		}
	}

	public void drawOnActive(Canvas canvas){
		canvas.drawRect(0,0,getWidth(),getHeight(),paint);
	}

	public void drawOnBomb(Canvas canvas){
		dbThread = new DrawBombThread(canvas);
		new Thread(dbThread).start();
	}


	private class DrawBombThread implements Runnable {
		public Canvas canvas;
		private int a;
		public DrawBombThread(Canvas canvas) {
			this.canvas = canvas;
			a = 50;
		}

		@Override
		public void run() {
			for(int i = 0;i<5;i++){
				try {
					Thread.sleep(200);
				} catch (Exception e) {
					Thread.currentThread().interrupt();
				}

				new Thread(new DrawBombCellThread(canvas)).start();
				a +=50 ;
			}
		}
		private class DrawBombCellThread implements Runnable{
			public Canvas canvas;
			private float theWidth = 0,theHeight = 0;


			public DrawBombCellThread(Canvas canvas) {
				this.canvas = canvas;
			}

			@Override
			public void run() {
				while (isOnBomb()){
					try{
						Thread.sleep(100);
					}catch (Exception e) {
						Thread.currentThread().interrupt();
					}
					paint.setAlpha(a);
					canvas.drawRect(0,0,theWidth,theHeight,paint);
					theWidth+=12.5;theHeight+=12.5;
				}
			}
		}
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
		paint.setColor(color);
	}
	public boolean isOnActive() {
		return onActive;
	}

	public void setOnActive(boolean onActive) {
		this.onActive = onActive;
	}

	public boolean isOnBomb() {
		return onBomb;
	}

	public void setOnBomb(boolean onBomb) {
		this.onBomb = onBomb;
	}
}
