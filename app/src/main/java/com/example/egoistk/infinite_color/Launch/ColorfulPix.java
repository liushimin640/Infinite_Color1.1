package com.example.egoistk.infinite_color.Launch;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by liushimin on 15/10/11.
 */
public class ColorfulPix extends LaunchContainer {
	private Paint paint;
	private int color;
	public boolean onActive;
	private boolean onBomb;
	public String className ;
	public ColorfulPix me;
	private DrawBombThread dbThread = null;
	public LaunchContainer parent;
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
		paint.setAntiAlias(true);
		
		onActive = true;
		onBomb = false;
		sin = (float)Math.random() * 2 - 1;
		cos = (float)Math.random() * 2 - 1;
		r = (float)Math.sqrt(sin*sin+cos*cos);
		me = this;
	}

	public void draw(Canvas canvas){

		canvas.save();
		canvas.translate(this.getX(), this.getY());
		costomChildren(canvas);
		canvas.restore();
	}

	@Override
	public void costomChildren(Canvas canvas) {

		if(onActive){
			if(x < 0 + c1.width || x > parent.width - c1.width){
				rebound(true, false);
			}
			if(y < 0 + c1.width|| y > parent.height - c1.width){
				rebound(false, true);
			}
			this.move();
			if(inCircle(this.parent.circle5)){
				this.paint.setColor(this.parent.rpaint3.getColor());
			}
			else if(inCircle(this.parent.circle3)){
				this.paint.setColor(this.parent.rpaint2.getColor());
			}
			else if(inCircle(this.parent.circle1)){
				this.paint.setColor(this.parent.rpaint1.getColor());

			}
		}
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
	}

	public boolean inCircle(Circle bc){
		int r = (int)bc.width;
		int dx = (int) (bc.x - x);
		int dy =(int )( bc.y - y);
		boolean i = (r*r)>=(dx*dx+dy*dy);
		return (i);
	}


	public void drawOnBomb(){
		new Thread(new DrawBombThread()).start();
	}


	public class DrawBombThread implements Runnable {

		@Override
		public void run() {
			while (c5.width <= 100){
				try{
					Thread.sleep(500/30);
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
					Thread.sleep(500/30);
				} catch (Exception e) {
					Thread.currentThread().interrupt();
				}
				c5.width-=5;
				c4.width-=5;
				c3.width-=5;
				c2.width-=5;
				c1.width-=5;
			}
			parent.removePixChild(me);
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
		this.x = (this.x + 5 * cos / r);
		this.y = (this.y + 5 * sin / r);
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
