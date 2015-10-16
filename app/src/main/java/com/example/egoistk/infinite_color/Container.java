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
	public Paint rpaint1;
	public Paint rpaint2;
	public Paint rpaint3;
	public Paint wpaint;
	public Circle circle1,circle2;
	public Circle circle3,circle4;
	public Circle circle5,circle6;



	public Container() {
		children = new CopyOnWriteArrayList<ColorfulPix>();
		rpaint1 = new Paint();
		rpaint2 = new Paint();
		rpaint3 = new Paint();
		wpaint = new Paint();
		rpaint1.setAntiAlias(true);
		rpaint2.setAntiAlias(true);
		rpaint3.setAntiAlias(true);
		wpaint.setAntiAlias(true);
		wpaint.setColor(Color.WHITE);
		rpaint1.setColor((int)(Math.random()*0x88888888));
		rpaint2.setColor((int)(Math.random()*0x88888888));
		rpaint3.setColor((int)(Math.random()*0x88888888));
		circle1 = new Circle((float)(Math.random()*getWidth()),(float)(Math.random()*getHeight()),0);
		circle2 = new Circle(circle1.getX(),circle1.getY(),-20);
		circle3 = new Circle((float)(Math.random()*getWidth()),(float)(Math.random()*getHeight()),0);
		circle4 = new Circle(circle3.getX(),circle3.getY(),-20);
		circle5 = new Circle((float)(Math.random()*getWidth()),(float)(Math.random()*getHeight()),0);
		circle6 = new Circle(circle5.getX(),circle5.getY(),-20);
		parent = null;
		className = null;
	}

	public CopyOnWriteArrayList<ColorfulPix> getChildren() {
		return children;
	}

	public Container parent;

	public void draw(Canvas canvas){
		canvas.save();
		canvas.translate(getX(), getY());
		costomChildren(canvas);
		for(Container child : children){
			child.draw(canvas);
		}
		canvas.restore();
	}
	public void costomChildren(Canvas canvas){
		canvas.drawCircle(circle1.getX(), circle1.getY(), circle1.getWidth(), rpaint1);
		canvas.drawCircle(circle2.getX(), circle2.getY(), circle2.getWidth(), wpaint);
		canvas.drawCircle(circle3.getX(), circle3.getY(), circle3.getWidth(), rpaint2);
		canvas.drawCircle(circle4.getX(), circle4.getY(), circle4.getWidth(), wpaint);
		canvas.drawCircle(circle5.getX(), circle5.getY(), circle5.getWidth(), rpaint3);
		canvas.drawCircle(circle6.getX(), circle6.getY(), circle6.getWidth(), wpaint);
	}
	public void addChild(ColorfulPix child){
		children.add(child);
		child.parent = this;
	}
	public void removeChild(ColorfulPix child){
		children.remove(child);
	}

	public boolean circleMove(int r,boolean hasReady){

		if(circle1.getWidth()>circle2.getWidth()){
			circle1.width+=r;
			circle2.width+=(r+circle1.width/1000);
			rpaint1.setAlpha(rpaint1.getAlpha()-2);
		}
		else if(!hasReady){
			circle1 = new Circle((float)(Math.random()*width),(float)(Math.random()*height),(float)((-20)*Math.random()));
			rpaint1.setColor((int) (circle1.x / width * circle1.y / height * 0x00888888) + 0xff444444);
			rpaint1.setAlpha(255);
			circle2 = new Circle(circle1.getX(),circle1.getY(),circle1.getWidth()-(float)(10+10*Math.random()));
		}
		if(circle3.getWidth()>circle4.getWidth()){
			circle3.width+=r;
			circle4.width+=(r+circle3.width/1000);
			rpaint2.setAlpha(rpaint2.getAlpha()-2);
		}
		else if(!hasReady){
			circle3 = new Circle((float)(Math.random()*width),(float)(Math.random()*height),(float)((-20)*Math.random()));
			rpaint2.setColor((int) (circle3.x / width * circle3.y / height * 0x00888888) + 0xff444444);
			rpaint2.setAlpha(255);
			circle4 = new Circle(circle3.getX(),circle3.getY(),circle3.width-(float)(10+10*Math.random()));
		}if(circle5.getWidth()>circle6.getWidth()){
			circle5.width+=r;
			circle6.width+=(r+circle5.width/1000);
			rpaint3.setAlpha(rpaint3.getAlpha()-2);
		}
		else if(!hasReady){
			circle5 = new Circle((float)(Math.random()*width),(float)(Math.random()*height),(float)((-20)*Math.random()));
			rpaint3.setColor((int) (circle5.x / width * circle5.y / height * 0x00888888) + 0xff444444);
			rpaint3.setAlpha(255);
			circle6 = new Circle(circle5.getX(),circle5.getY(),circle5.width-(float)(10+10*Math.random()));
		}
		return(circle3.getWidth()<circle4.getWidth()&&circle1.getWidth()<circle2.getWidth()&&circle5.getWidth()<circle6.getWidth());
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
