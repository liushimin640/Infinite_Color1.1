package com.example.egoistk.infinite_color.About;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

import com.example.egoistk.infinite_color.ColorBox.BoxView;
import com.example.egoistk.infinite_color.Container;
import com.example.egoistk.infinite_color.IActivity;
import com.example.egoistk.infinite_color.IfiniteColor.GameView;
import com.example.egoistk.infinite_color.Launch.MainLaunchView;
import com.example.egoistk.infinite_color.MainView;
import com.example.egoistk.infinite_color.Menu.MenuView;
import com.example.egoistk.infinite_color.R;

import java.io.InputStream;

/**
 * Created by liushimin on 15/10/21.
 */
public class AboutView extends MainLaunchView {
	public MyButton aboutbtn,boxbtn,testbtn,backbtn;
	public boolean onToBack;
	public float w;
	public AboutView(IActivity iActivity) {
		super(iActivity);
		onToBack = false;
	}

	public void OnPressView(float x,float y) {

	}
	@Override
	public void OnTouchView(float x,float y){
		boolean toBack = (x > this.root.getWidth() - 1.5 * w && x < this.root.getWidth()-0.5 * w && y < this.root.getHeight() - 0.5 * w && y > this.getHeight() - 1.5 * w);
		new Thread(bombThread).start();
		new Thread(change4ClickThread).start();
		if(hasLoaded && toBack) {
			onToBack = true;
			mainActivity.changeView(mainActivity.menuView,this);
		}
	}
	@Override
	public void Launch(){
		setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {

					//手按下的时候
					case MotionEvent.ACTION_DOWN:
						root.onPress = true;
						OnPressView(event.getX(),event.getY());
						OnTouchView(event.getX(), event.getY());
						break;
					case MotionEvent.ACTION_UP:
						if(root.onPress == true)
							root.onPress = false;
						break;
					default:
						break;
				}
				hasLoaded = false;
				return true;
			}
		});
		super.Launch();
		testbtn = new MyButton(this.root.getWidth()-100,this.root.getHeight()-100, R.mipmap.about);
		w = testbtn.bitmap.getWidth();
		aboutbtn = new MyButton(w * 0.5f,this.root.getHeight() - w * 1.5f,R.mipmap.about);
		boxbtn = new MyButton(w * 0.5f,this.root.getHeight() - w * 1.5f,R.mipmap.gallery);
		backbtn = new MyButton(this.root.getWidth()- w * 1.5f,this.root.getHeight(),R.mipmap.back);
		root.addChild(aboutbtn);
		root.addChild(backbtn);
		new Thread(new BackViewThread()).start();
	}

	public class BackViewThread implements Runnable {
		@Override
		public void run() {
			while(!onToBack && backbtn.y > root.getHeight() - w * 1.5f){
				backbtn.y -= 12;
				try {
					Thread.sleep(500/30);
				} catch (Exception e) {
					Thread.currentThread().interrupt();
				}
			}
			backbtn.y = root.getHeight() - w * 1.5f;
			while (!onToBack) {
				Thread.yield();
			}
			while(onToBack && backbtn.y < root.getHeight()) {
				backbtn.y += 12;
				try {
					Thread.sleep(500/30);
				} catch (Exception e) {
					Thread.currentThread().interrupt();
				}
			}
			backbtn.y = root.getHeight() + 15;
			root.removeChild(backbtn);
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
		pause();
		onToBack = false;
	}

	public class MyButton extends Container {
		public int id;
		public Bitmap bitmap;
		public float d;
		public MyButton(float x,float y,int id) {
			this.x = x;
			this.y = y;
			this.id = id;
			InputStream inputStream = getResources().openRawResource(id);
			bitmap  = BitmapFactory.decodeStream(inputStream);
		}
		@Override
		public void draw(Canvas canvas){
			canvas.save();
			canvas.translate(this.x, this.y);
			costomChildren(canvas);
			canvas.restore();
		}

		@Override
		public void costomChildren(Canvas canvas){

			Paint mPaint = new Paint();
			mPaint.setAntiAlias(true);
			mPaint.setAlpha(100);
			mPaint.setColor(Color.WHITE);
			canvas.save();
			Matrix matrix = new Matrix();
			canvas.drawBitmap(bitmap, matrix, mPaint);
			canvas.restore();
			if(onToBack&&backbtn.y == root.getHeight()+15){
				x += 4 * w;
				if(boxbtn.parent == null) {
					root.addChild(boxbtn);
				}
			}
			if(aboutbtn.x > root.getWidth() - 1.5f * w) {
				aboutbtn.x = root.getWidth() - 1.5f * w;
			}
			if(boxbtn.x > root.getWidth() - 3f * w) {
				boxbtn.x = root.getWidth() - 3f * w;
			}
		}
	}
	public class MessageContainer extends Container {

	}

}
