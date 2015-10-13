package com.example.egoistk.infinite_color;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
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



	public MainLaunchView(Context context) {
		super(context);
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
		synchronized (pixBox) {
			for (ColorfulPix child : pixBox) {
				this.getRoot().addChild(child);
			}
		}
		td.start();
		hasLoaded = true;
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
						Thread.sleep((long)(Math.random()*50));
					}catch (Exception e) {
						Thread.currentThread().interrupt();
					}
				}

			}

		}

	}

	public class MoveThread implements Runnable {
		@Override
		public void run() {
			boolean hasReady = false;
			while(!hasReady){
				try{
					Thread.sleep(1);
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
						if(!child.isOnBomb()){

						}
					}
					if(i == pixBox.size())
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
					getRoot().rpaint.setColor((int)(Math.random()*0x88888888));
					getRoot().circle = new Circle(Math.random()*getRoot().getWidth(),Math.random()*getRoot().getHeight(),0,0,getRoot().rpaint.getColor());
				}

				draw();
			}



			while(hasReady){
				try{
					Thread.sleep(1);
				}catch (Exception e) {
					Thread.currentThread().interrupt();
				}
				draw();
			}
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		this.Launch();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		this.root = new Container();
	}


	public void testBox(){
		for(int i = 0;i<3;i++){
			pixBox.add(new ColorfulPix(Math.random()*(getRoot().getWidth() - 50),Math.random()*(getRoot().getHeight() - 50),50,50,0xff000000));
			pixBox.add(new ColorfulPix(Math.random()*(getRoot().getWidth() - 50),Math.random()*(getRoot().getHeight() - 50),50,50,0xffff0000));
			pixBox.add(new ColorfulPix(Math.random()*(getRoot().getWidth() - 50),Math.random()*(getRoot().getHeight() - 50),50,50,0xff00ff00));
			pixBox.add(new ColorfulPix(Math.random()*(getRoot().getWidth() - 50),Math.random()*(getRoot().getHeight() - 50),50,50,0xff0000ff));
			pixBox.add(new ColorfulPix(Math.random()*(getRoot().getWidth() - 50),Math.random()*(getRoot().getHeight() - 50),50,50,0xff000000));
		}
	}
	public PixBox getPixBox() {
		return pixBox;
	}
}
