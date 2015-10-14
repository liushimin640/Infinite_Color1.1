package com.example.egoistk.infinite_color;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.provider.Settings;
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
		if(canvas!=null){
			root.setWidth(canvas.getWidth());
			root.setHeight(canvas.getHeight());
		}
	}

	public void OnTouchView(float x,float y){
		if(hasLoaded){
			new Thread(new BombThread(this)).start();
		}
	}



	public void Launch(){
		draw();
		testBox();
		root.circle1 = new Circle((float)(Math.random()*getWidth()),(float)(Math.random()*getHeight()),0);
		root.circle2 = new Circle(root.circle1.getX(),root.circle1.getY(),-20);
		for (ColorfulPix child : pixBox) {
			this.getRoot().addChild(child);
		}
		td.start();
		hasLoaded = true;
	}

	public void pause(){
		pixBox = new PixBox();
		moveThread = new MoveThread();
		td = new Thread(moveThread);
		root = new Container();
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
		public MainLaunchView mainLaunchView;
		public BombThread(MainLaunchView mainLaunchView) {
			this.mainLaunchView = mainLaunchView;
		}

		@Override
		public void run() {
			for(Iterator<ColorfulPix> it = mainLaunchView.getRoot().children.iterator();it.hasNext();){
				ColorfulPix child = it.next();
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

	public class MoveThread implements Runnable {

		@Override
		public void run() {
			int flag = 1;
			boolean hasReady = false;
			while(!hasReady){
				long before = System.currentTimeMillis();
				System.out.println("开始加载");

				if(root.children.size() == 0) {
					hasReady = true;
				}

				root.circleMove(15,hasReady);

				System.out.println("加载完成");
				draw();
				System.out.println("绘制1帧");
				try{
					Thread.sleep(1000/30-(System.currentTimeMillis()-before));
				}catch (Exception e) {
					Thread.currentThread().interrupt();
				}
			}



			while(hasReady){
				long before = System.currentTimeMillis();
				if(root.circleMove(100,hasReady)){
					hasReady = false;
				}
				draw();
				try{
					Thread.sleep(1000/30-(System.currentTimeMillis()-before));
				}catch (Exception e) {
					Thread.currentThread().interrupt();
				}
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
		for(int i = 0;i<1;i++){
			pixBox.add(new ColorfulPix((float)(Math.random()*(getRoot().getWidth() - 100)+50),(float)(Math.random()*(getRoot().getHeight() - 100)+50),0xffffffff));
			pixBox.add(new ColorfulPix((float)(Math.random()*(getRoot().getWidth() - 100)+50),(float)(Math.random()*(getRoot().getHeight() - 100)+50),0xffffffff));
			pixBox.add(new ColorfulPix((float)(Math.random()*(getRoot().getWidth() - 100)+50),(float)(Math.random()*(getRoot().getHeight() - 100)+50),0xffffffff));
			pixBox.add(new ColorfulPix((float)(Math.random()*(getRoot().getWidth() - 100)+50),(float)(Math.random()*(getRoot().getHeight() - 100)+50),0xffffffff));
			pixBox.add(new ColorfulPix((float)(Math.random()*(getRoot().getWidth() - 100)+50),(float)(Math.random()*(getRoot().getHeight() - 100)+50),0xffffffff));
		}
	}
	public PixBox getPixBox() {
		return pixBox;
	}
}
