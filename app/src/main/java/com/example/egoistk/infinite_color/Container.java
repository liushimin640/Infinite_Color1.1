package com.example.egoistk.infinite_color;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by liushimin on 15/10/11.
 */
public class Container{
	public CopyOnWriteArrayList<ColorfulPix> children;
	public float x ,y ,width ,height ;

	public String className;
	public Paint rpaint;
	public Paint wpaint;
	public Circle circle1,circle2;



	public Container() {
		children = new CopyOnWriteArrayList<ColorfulPix>();
		rpaint = new Paint();
		wpaint = new Paint();
		wpaint.setColor(Color.WHITE);
		rpaint.setColor((int)(Math.random()*0x88888888));
		circle1 = new Circle((float)(Math.random()*getWidth()),(float)(Math.random()*getHeight()),0);
		circle2 = new Circle(circle1.getX(),circle1.getY(),-20);
		parent = null;
		className = null;
	}



	public CopyOnWriteArrayList<ColorfulPix> getChildren() {
		return children;
	}

	public Container parent;

	public void draw(Canvas canvas){
		canvas.save();
		System.out.println("开始绘制root");
		canvas.translate(getX(), getY());
		System.out.println("root画布就位,开始绘制rootCircle");
		costomChildren(canvas);
		System.out.println("rootCircle绘制完毕，开始绘制child");
		for(Container child : children){
			System.out.println("启动了一个root。child的绘制");
			child.draw(canvas);
			System.out.println("完成了一个root.child");
		}
		System.out.println("画布开始restore");
		canvas.restore();
		System.out.println("画布restore完毕");
	}
	public void costomChildren(Canvas canvas){
		canvas.drawCircle(circle1.getX(), circle1.getY(), circle1.getWidth(), rpaint);
		canvas.drawCircle(circle2.getX(), circle2.getY(), circle2.getWidth(), wpaint);
	}
	public void addChild(ColorfulPix child){
		children.add(child);
		child.parent = this;
	}
	public void removeChild(ColorfulPix child){
		children.remove(child);
	}



	public void enlargeView(float dX,float dY){//enlarge this Container and lock every center locations of its children,but don't enlarge them.

		/*x = rX - getWidth()/2.0 - dX/2.0;
		y = rY - getHeight()/2.0 - dY/2.0;*/
		width += dX;
		height += dY;
	}

	public boolean circleMove(int r){

		if(circle1.getWidth()>circle2.getWidth()-(float)(Math.random()*200)){
			circle1.width+=r;
			circle2.width+=(r+circle1.width/1500);
			rpaint.setAlpha(rpaint.getAlpha()-7);
		}
		else{
			circle1 = new Circle((float)(Math.random()*width),(float)(Math.random()*height),0);
			rpaint.setColor((int)(circle1.x/width*circle1.y/height*0x00888888)+0xff666666);
			circle2 = new Circle(circle1.getX(),circle1.getY(),-20);


		}
		return(circle1.getWidth()<circle2.getWidth());
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setWidth(float width) {

		this.width = width;

	}

	public void setHeight(float height) {
		this.height = height;

	}





	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}
}
