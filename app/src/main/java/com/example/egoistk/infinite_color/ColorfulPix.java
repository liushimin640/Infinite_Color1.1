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

	private float cos,sin,r;


	public ColorfulPix(float x,float y,float width,float height,int color) {
		paint = new Paint();
		setX(x);
		setY(y);
		setHeight(height);
		setWidth(width);
		setColor(color);
		onActive = true;
		onBomb = false;
		sin = (float)Math.random() * 2 - 1;
		cos = (float)Math.random() * 2 - 1;
		r = (float)Math.sqrt(sin*sin+cos*cos);
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
		canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
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
					canvas.drawRect(0, 0, theWidth, theHeight, paint);
					theWidth+=12.5;theHeight+=12.5;
				}
			}
		}
	}

	public void move(){//k = (float)Math.random()   0-1
		setX(getX() + 5 * cos / r);
		setY(getY() + 5 * sin / r);
	}

	public void rebound(boolean shouldChgCos,boolean shouldChgSin){
		if(shouldChgCos)cos = - cos;
		if(shouldChgSin)sin = - sin;
	}

	//                                                        //GETSET

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

	public float getCos() {
		return cos;
	}

	public void setCos(float cos) {
		this.cos = cos;
	}

	public float getSin() {
		return sin;
	}

	public void setSin(float sin) {
		this.sin = sin;
	}

	public float getR() {
		return r;
	}

	public void setR(float r) {
		this.r = r;
	}
}
