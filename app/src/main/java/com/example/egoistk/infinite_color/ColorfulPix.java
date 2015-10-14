package com.example.egoistk.infinite_color;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.provider.Settings;

import java.util.logging.XMLFormatter;

/**
 * Created by liushimin on 15/10/11.
 */
public class ColorfulPix extends Container{
	private Paint paint;
	private int color;
	public boolean onActive;
	private boolean onBomb;
	public String className ;
	public ColorfulPix me;
	private DrawBombThread dbThread = null;

	private float cos,sin,r;
	public Circle c1,c2,c3,c4,c5;

	public ColorfulPix(float x,float y,int color) {
		super();
		className = "pix";
		paint = new Paint();
		paint.setColor(color);
		paint.setAlpha(255);
		setX(x);
		setY(y);
		setColor(color);
		c1 = new Circle(0,0,20);

		c2 = new Circle(0,0,-40);
		c3 = new Circle(0,0,-20);
		c4 = new Circle(0,0,0);
		c5 = new Circle(0,0,20);

		onActive = true;
		onBomb = false;
		sin = (float)Math.random() * 2 - 1;
		cos = (float)Math.random() * 2 - 1;
		r = (float)Math.sqrt(sin*sin+cos*cos);
		me = this;
	}

	@Override
	public void draw(Canvas canvas){

		canvas.save();
		System.out.println("开始绘制pix");
		canvas.translate(getX(), getY());
		System.out.println("pix画布就位,开始绘制pixCircle");
		costomChildren(canvas);
		System.out.println("pixCircle绘制完毕");

		canvas.restore();
	}

	@Override
	public void costomChildren(Canvas canvas) {
		super.costomChildren(canvas);
		if(onActive) {
			canvas.drawCircle(0, 0, c1.width, paint);
		}
		else{

			paint.setAlpha(50);
			canvas.drawCircle(0, 0, c5.getWidth(), paint);
			paint.setAlpha(100);
			canvas.drawCircle(0, 0, c4.getWidth(), paint);
			paint.setAlpha(150);
			canvas.drawCircle(0, 0, c3.getWidth(), paint);
			paint.setAlpha(200);
			canvas.drawCircle(0, 0, c2.getWidth(), paint);
			paint.setAlpha(250);
			canvas.drawCircle(0, 0, c1.getWidth(), paint);
			paint.setAlpha(255);
		}
		if(onActive){
			if(x < 0 + c1.width || x > parent.width - c1.width){
				rebound(true, false);
			}
			if(y < 0 + c1.width|| y > parent.height - c1.width){
				rebound(false, true);
			}
			move();
			if(parent.circle1.getWidth()*parent.circle1.getWidth() >= ((x-parent.circle1.x)*(x-parent.circle1.x)+(y-parent.circle1.y)*(y-parent.circle1.y))){
				new Thread(new ChangeColorThread(parent.rpaint.getColor())).start();
			}
		}
	}


	public class ChangeColorThread implements Runnable{
		public int color;
		public ChangeColorThread(int color) {
			this.color = color;
		}

		@Override
		public void run() {
			paint.setColor(color);
			paint.setAlpha(255);
		}
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
			while (c5.width <= 100){
				try{
					Thread.sleep(10);
				}catch (Exception e) {
					Thread.currentThread().interrupt();
				}
				c5.width+=5;
				c4.width+=5;
				c3.width+=5;
				c2.width+=5;
			}
			while (c5.width >= 0) {
				try {
					Thread.sleep(10);
				} catch (Exception e) {
					Thread.currentThread().interrupt();
				}
				c5.width-=5;
				c4.width-=5;
				c3.width-=5;
				c2.width-=5;
				c1.width-=5;
			}
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

	public void move(){
		x = (x + 20 * cos / r);
		y = (y + 20 * sin / r);
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
