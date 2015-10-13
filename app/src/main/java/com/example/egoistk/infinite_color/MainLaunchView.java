package com.example.egoistk.infinite_color;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.FrameLayout;

import java.util.Iterator;

/**
 * Created by liushimin on 15/10/11.
 */
public class MainLaunchView extends LaunchView{

	private PixBox pixBox;

	private MoveThread moveThread;
	private Thread td;
	boolean hasLoaded;



	public MainLaunchView(Context context,AttributeSet attrs) {
		super(context,attrs);
		pixBox = new PixBox();
		moveThread = new MoveThread();
		td = new Thread(moveThread);
		setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				OnTouchView(event.getX(),event.getY());
				hasLoaded = false;
				return false;
			}
		});
	}

	public void OnTouchView(float x,float y){
		if(hasLoaded){
			new Thread(new BombThread()).start();
		}
	}



	public void Launch(){

			draw();
			testBox();
			root.circle = new Circle(Math.random()*getWidth(),Math.random()*getHeight(),0,0,root.rpaint.getColor());
			synchronized (pixBox) {
				for (ColorfulPix child : pixBox) {
					this.getRoot().addChild(child);
				}
			}
			td.start();
			hasLoaded = true;

	}

	public void pause(){
		pixBox = new PixBox();
		moveThread = new MoveThread();
		td = new Thread(moveThread);
		int color = root.bkgColor;
		root = new Container();
		root.bkgColor = color;
		setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				OnTouchView(event.getX(),event.getY());
				hasLoaded = false;
				return false;
			}
		});


	}

	public class BombThread implements Runnable {
		public BombThread() {
		}

		@Override
		public void run() {
			for(Iterator<ColorfulPix> it = getRoot().children.iterator();it.hasNext();){
				ColorfulPix child = it.next();
				synchronized (child){

					child.setOnActive(false);
					child.drawOnBomb(40);
					try{
						Thread.sleep((long)(Math.random()*5));
					}catch (Exception e) {
						Thread.currentThread().interrupt();
					}
				}

			}

		}

	}

	public class MoveThread implements Runnable {
		public boolean isOnPause = false;
		@Override
		public void run() {
			boolean hasReady = false;
			while(!hasReady){
				while(isOnPause){
					try{
						Thread.sleep(1000);
					}catch (Exception e){
						Thread.currentThread().interrupt();
					}

				}
				try{
					Thread.sleep(50/3);
				}catch (Exception e) {
					Thread.currentThread().interrupt();
				}
				int i = 0;
				for(ColorfulPix child : getRoot().children){
					if(child.isOnActive()){
						if(child.getX() < 0 || child.getX() > getRoot().getWidth() - child.getWidth()){
							child.rebound(true,false);
						}
						if(child.getY() < 0 || child.getY() > getRoot().getHeight() - child.getHeight()){
							child.rebound(false,true);
						}
						child.move();
					}else {
						i++;
						System.out.println("开始了第"+i+"次爆炸");
					}
					if(i >= getRoot().children.size())
						hasReady = true;
				}
				if(getRoot().circle.getWidth()<2000){
					getRoot().circle.width+=30;
					getRoot().circle.height+=30;
					/*getRoot().circle.x;
					getRoot().circle.y-=3;*/
				}
				else{
					getRoot().bkgColor = getRoot().rpaint.getColor();
					getRoot().rpaint.setColor((int)(Math.random()*0x00888888)+0xff888888);
					getRoot().circle = new Circle(Math.random()*getRoot().getWidth(),Math.random()*getRoot().getHeight(),0,0,getRoot().rpaint.getColor());
				}

				draw();
				System.out.println("绘制1帧");
			}



			while(hasReady){
				try{
					Thread.sleep(50/3);
				}catch (Exception e) {
					Thread.currentThread().interrupt();
				}
				if(getRoot().circle.getWidth()<2000){
					getRoot().circle.width+=60;
					getRoot().circle.height+=60;
					/*getRoot().circle.x;
					getRoot().circle.y-=3;*/
				}
				else{
					getRoot().bkgColor = getRoot().rpaint.getColor();
					if(getRoot().children.size() == 0)
						hasReady = false;
				}
				draw();
			}
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		this.Launch();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		pause();
	}


	public void testBox(){
		for(int i = 0;i<30;i++){
			pixBox.add(new ColorfulPix(Math.random()*(getRoot().getWidth() - 50),Math.random()*(getRoot().getHeight() - 50),50,50,0xff880000));
			pixBox.add(new ColorfulPix(Math.random()*(getRoot().getWidth() - 50),Math.random()*(getRoot().getHeight() - 50),50,50,0xffff0000));
			pixBox.add(new ColorfulPix(Math.random()*(getRoot().getWidth() - 50),Math.random()*(getRoot().getHeight() - 50),50,50,0xff00ff00));
			pixBox.add(new ColorfulPix(Math.random()*(getRoot().getWidth() - 50),Math.random()*(getRoot().getHeight() - 50),50,50,0xff0000ff));
			pixBox.add(new ColorfulPix(Math.random()*(getRoot().getWidth() - 50),Math.random()*(getRoot().getHeight() - 50),50,50,0xff000088));
		}
	}
	public PixBox getPixBox() {
		return pixBox;
	}
}
