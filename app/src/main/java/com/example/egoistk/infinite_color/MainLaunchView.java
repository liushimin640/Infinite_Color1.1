package com.example.egoistk.infinite_color;

import android.content.Context;
import android.graphics.Color;
import android.view.SurfaceHolder;

/**
 * Created by liushimin on 15/10/11.
 */
public class MainLaunchView extends LaunchView{

	private PixBox pixBox;

	private MoveThread moveThread;
	private Thread td;

	public MainLaunchView(Context context) {
		super(context);
		pixBox = new PixBox();
		moveThread = new MoveThread();
		td = new Thread(moveThread);
		testBox();
	}

	public void Launch(){
		for(ColorfulPix child : pixBox){
			this.getRoot().addChild(child);
		}
		td.start();
	}

	public class MoveThread implements Runnable {
		@Override
		public void run() {
			while(true){
				try{
					Thread.sleep(1);
				}catch (Exception e) {
					Thread.currentThread().interrupt();
				}
				for(ColorfulPix child : pixBox){
					if(child.isOnActive()){
						child.setX(child.getX()+5);
					}
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
		Launch();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

	}


	public void testBox(){
		pixBox.add(new ColorfulPix((float)Math.random()*500,(float)Math.random()*500,50,50,0xff000000));
		pixBox.add(new ColorfulPix((float)Math.random()*500,(float)Math.random()*500,10,10,0xff000000));
		pixBox.add(new ColorfulPix((float)Math.random()*500,(float)Math.random()*500,10,10,0xff000000));
		pixBox.add(new ColorfulPix((float)Math.random()*500,(float)Math.random()*500,10,10,0xff000000));
		pixBox.add(new ColorfulPix((float)Math.random()*500,(float)Math.random()*500,10,10,0xff000000));
	}
}
