package com.example.egoistk.infinite_color;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.provider.Settings;

import java.util.logging.XMLFormatter;

/**
 * Created by liushimin on 15/10/11.
 */
public class ColorfulPix extends Container{
	private Paint paint;
	private int color;
	private boolean onActive;
	private boolean onBomb;
	public String className ;
	public ColorfulPix me;
	private DrawBombThread dbThread = null;

	private float cos,sin,r;


	public ColorfulPix(double x,double y,double width,double height,int color) {
		super();
		className = "pix";
		paint = new Paint();
		this.height = height;
		this.width = width;
		setX(x);
		setY(y);
		setColor(color);
		onActive = true;
		onBomb = false;
		sin = (float)Math.random() * 2 - 1;
		cos = (float)Math.random() * 2 - 1;
		r = (float)Math.sqrt(sin*sin+cos*cos);
		me = this;
	}


	@Override
	public void costomChildren(Canvas canvas) {
		super.costomChildren(canvas);
		canvas.drawRect(0, 0, (float)getWidth(), (float)getHeight(), paint);
	}

	public void drawOnActive(){
		canvas.drawRect(0, 0, (float)getWidth(), (float)getHeight(), paint);
	}

	public void drawOnBomb(int a){
		new Thread(new DrawBombThread(a)).start();
	}


	public class DrawBombThread implements Runnable {
			int a;
		public DrawBombThread(int a) {
			this.a = a;
		}

		@Override
		public void run() {
			ColorfulPix childPix = new ColorfulPix(0, 0, 50, 50, getColor());
			while (getWidth()<130 - a / 2){
				try{
					Thread.sleep(10);
				}catch (Exception e) {
					Thread.currentThread().interrupt();
				}
				synchronized (childPix){


					if(getWidth() == 50){
						addChild(childPix);
						childPix.setOnBomb(true);
					}
					enlargeView(10, 10);
					if(getWidth() == 70){
						childPix.drawOnBomb(a + 40);
					}
					paint.setAlpha(a);
				}
			}

			try{
				Thread.sleep(1000);
			}catch (Exception e) {
				Thread.currentThread().interrupt();
			}
			while (getWidth() >=0) {
				try {
					Thread.sleep(20);
				} catch (Exception e) {
					Thread.currentThread().interrupt();
				}
				synchronized (me) {
					if (me != null)
						enlargeView(-10, -10);
					if (getWidth() == 0) {
					}
				}
			}
			if(parent!=null)
			parent.removeChild(me);
		}

	}

	public class RemoveThread implements Runnable{
		public RemoveThread() {
		}

		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName()+"done!");
		}
	}

	public void move(){//k = (float)Math.random()   0-1
		setX(getX() + 10 * cos / r);
		setY(getY() + 10 * sin / r);
	}

	public void rebound(boolean shouldChgCos,boolean shouldChgSin){
		if(shouldChgCos)cos = - cos;
		if(shouldChgSin)sin = - sin;
	}


	public void removeThis() {
		new Thread(new RemoveThread()).start();
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
		this.onBomb = !onActive;
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

	public Paint getPaint() {
		return paint;
	}

	public void setR(float r) {
		this.r = r;
	}
}
