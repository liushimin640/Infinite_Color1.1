package com.example.egoistk.infinite_color.Menu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

import com.example.egoistk.infinite_color.About.AboutView;
import com.example.egoistk.infinite_color.ColorBox.BoxView;
import com.example.egoistk.infinite_color.Container;
import com.example.egoistk.infinite_color.IActivity;
import com.example.egoistk.infinite_color.IfiniteColor.GameView;
import com.example.egoistk.infinite_color.Launch.MainLaunchView;
import com.example.egoistk.infinite_color.R;

import java.io.InputStream;


/**
 * Created by liushimin on 15/10/21.
 */
public class MenuView extends MainLaunchView {
	public MyButton aboutbtn,boxbtn,testbtn;
	public boolean onToGame,onToAbout,onToBox;
	public float w;
	public MenuView(IActivity iActivity) {
		super(iActivity);
		onToAbout = false;
		onToBox = false;
		onToGame = false;

	}
	public void OnPressView(float x,float y) {

	}
	@Override
	public void OnTouchView(float x,float y){
		boolean toAbout = (x > this.root.getWidth() - 1.5 * w && x < this.root.getWidth()-0.5 * w && y < this.root.getHeight() - 0.5 * w && y > this.getHeight() - 1.5 * w);
		boolean toBox = (x > this.root.getWidth() - 3 * w && x < this.root.getWidth() - 2 * w && y < this.root.getHeight() - 0.5 * w && y > this.getHeight() - 1.5 * w);
		boolean toGame = !(toAbout || toBox);
		new Thread(bombThread).start();
		new Thread(change4ClickThread).start();

		if(hasLoaded&&toGame) {
			onToGame = true;
			mainActivity.changeView(mainActivity.gameView,this);
		}
		if(hasLoaded && toAbout) {
			onToAbout = true;
			root.removeChild(aboutbtn);
			root.addChild(aboutbtn);
			mainActivity.changeView(mainActivity.aboutView,this);
		}
		if(hasLoaded && toBox) {
			onToBox = true;
			root.removeChild(boxbtn);
			root.addChild(boxbtn);
			mainActivity.changeView(mainActivity.boxView,this);
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
		testbtn = new MyButton(this.root.getWidth()-100,this.root.getHeight()-100,R.mipmap.about);
		w = testbtn.bitmap.getWidth();
		aboutbtn = new MyButton(this.root.getWidth()- w * 1.5f,this.root.getHeight() - w * 1.5f,R.mipmap.about);
		boxbtn = new MyButton(this.root.getWidth()-w * 3f,this.getHeight()-w * 1.5f, R.mipmap.gallery);
		root.addChild(aboutbtn);
		root.addChild(boxbtn);
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
		onToAbout = false;
		onToBox = false;
		onToGame = false;
	}

	public class MyButton extends Container {
		public int id;
		public Bitmap bitmap;
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
			if(onToGame && y < root.getHeight()){
				y+=12;
			}
			else if ((onToAbout || onToBox) && x > 0.5f * w) {
				x -= 4 * w;
			}
			if(onToAbout && x <= 0.5f * w) {
				x = 0.5f * w;
				parent.removeChild(boxbtn);

			}
			else if(onToBox && x <= 0.5f * w) {
				x = 0.5f * w;
				parent.removeChild(aboutbtn);
			}
		}
	}

}
