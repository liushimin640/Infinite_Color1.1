package com.example.egoistk.infinite_color.Launch;

import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

import com.example.egoistk.infinite_color.Container;
import com.example.egoistk.infinite_color.IActivity;
import com.example.egoistk.infinite_color.IfiniteColor.GameView;
import com.example.egoistk.infinite_color.MainActivity;
import com.example.egoistk.infinite_color.MainView;
import com.example.egoistk.infinite_color.PixBox;

import java.util.Iterator;

/**
 * Created by liushimin on 15/10/11.
 */
public class MainLaunchView extends LaunchView {


	public PixBox pixBox;
	public MoveThread moveThread;
	public BombThread bombThread;
	public Change4ClickThread change4ClickThread;
	public Thread td;
	public  boolean hasLoaded;
	public boolean bombOver;


	public MainLaunchView(final IActivity iActivity) {
		super(iActivity);

		pixBox = new PixBox();

		if(canvas!=null){
			root.setWidth(canvas.getWidth());
			root.setHeight(canvas.getHeight());
		}
	}

	public void OnTouchView(float x,float y){
		if(hasLoaded){
			new Thread(new BombThread()).start();
		}

	}

	public class Change4ClickThread implements Runnable{
		@Override
		public void run() {
			setOnTouchListener(null);
			while(!bombOver) {
				Thread.yield();
			}System.out.println("waitToChangeFalse");
			mainActivity.waitToChange = false;
		}
	}



	public void Launch(){
		System.out.println("新建了画面");
		draw();
		testBox();

		root.circle1 = new Circle((float)(Math.random()*getWidth()),(float)(Math.random()*getHeight()),0);
		root.circle2 = new Circle(root.circle1.getX(),root.circle1.getY(),-20);
		for (ColorfulPix child : pixBox) {
			this.getRoot().addPixChild(child);
		}
		moveThread = new MoveThread();
		td = new Thread(moveThread);
		bombThread = new BombThread();
		change4ClickThread = new Change4ClickThread();
		td.start();
		hasLoaded = true;
		bombOver = false;
	}

	public void pause(){
		System.out.println("销毁了画面");
		pixBox = new PixBox();
		moveThread = new MoveThread();
		td = new Thread(moveThread);
		root = new LaunchContainer();


	}

	public class BombThread implements Runnable {
		public BombThread() {

		}

		@Override
		public void run() {
			for(Iterator<ColorfulPix> it = root.pixChildren.iterator();it.hasNext();){
				ColorfulPix child = it.next();
				child.setOnActive(false);
				child.drawOnBomb();
				try{
					Thread.sleep((long)(Math.random()*5));
				}catch (Exception e) {
					Thread.currentThread().interrupt();
				}
			}
			System.out.println("bombTrue");
		}
	}

	public class MoveThread implements Runnable {

		@Override
		public void run() {
			boolean hasReady = false;
			while(!hasReady){
				long before = SystemClock.currentThreadTimeMillis();
				if(root.pixChildren.size() == 0) {
					hasReady = true;
				}
				root.circleMove(15, hasReady);
				draw();
				long ago = SystemClock.currentThreadTimeMillis();
				/*System.out.println("本次绘制耗时"+(ago-before));*/
				while((int)(ago- before) <=1000/30) {
					ago = SystemClock.currentThreadTimeMillis();
					/**线程等待**/
					Thread.yield();
				}
				/*System.out.println("一帧的时间是："+(SystemClock.currentThreadTimeMillis()-before));*/
			}
			while(hasReady){
				long before = SystemClock.currentThreadTimeMillis();
				if(root.circleMove(100,hasReady)){
					hasReady = false;
					bombOver = true;
				}
				draw();
				long ago = SystemClock.currentThreadTimeMillis();
				/*System.out.println("本次绘制耗时" + (ago - before));*/
				while((int)(ago- before) <=1000/30) {
					ago = SystemClock.currentThreadTimeMillis();
					/**线程等待**/
					Thread.yield();
				}

				/*System.out.println("一帧的时间是："+(SystemClock.currentThreadTimeMillis()-before));*/
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
