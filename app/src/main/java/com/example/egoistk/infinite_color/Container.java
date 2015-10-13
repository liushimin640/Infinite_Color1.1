package com.example.egoistk.infinite_color;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by liushimin on 15/10/11.
 */
public class Container{
	public CopyOnWriteArrayList<ColorfulPix> children;
	public double x ,y ,width ,height ;
	private double rX ;
	private double rY ;
	public String className;
	public Canvas canvas ;
	public Paint rpaint;
	public Circle circle;
	public int bkgColor;

	public Container() {
		children = new CopyOnWriteArrayList<ColorfulPix>();
		rpaint = new Paint();
		rpaint.setColor((int)(Math.random()*0x88888888));
		circle = new Circle(Math.random()*getWidth(),Math.random()*getHeight(),0,0,rpaint.getColor());
		bkgColor = 0xff000000;
		parent = null;
		className = null;
	}



	public CopyOnWriteArrayList<ColorfulPix> getChildren() {
		return children;
	}

	public Container parent;

	public void draw(Canvas canvas){
		this.canvas = canvas;
		canvas.save();
		canvas.translate((float) getX(), (float) getY());
		costomChildren(canvas);

		for(Container child : children){
			child.draw(canvas);
		}
		canvas.restore();
	}
	public void costomChildren(Canvas canvas){
		canvas.drawCircle((float)circle.getX(), (float)circle.getY(), (float)circle.getWidth(), rpaint);
	}
	public void addChild(ColorfulPix child){
		children.add(child);
		child.parent = this;
	}
	public void removeChild(ColorfulPix child){
		children.remove(child);
	}



	public void enlargeView(double dX,double dY){//enlarge this Container and lock every center locations of its children,but don't enlarge them.

		/*x = rX - getWidth()/2.0 - dX/2.0;
		y = rY - getHeight()/2.0 - dY/2.0;*/
		setWidth(getWidth() + dX);
		setHeight(getHeight() + dY);

		if(this.x%1!=0){
				setrX(getrX());
				setrY(getrY());
		}
		else if(parent != null){
			setrX(parent.getWidth() / 2.0);
			setrY(parent.getHeight() / 2.0);
		}
	}


	public void setX(double x) {
		this.x = x;
		this.rX = x + this.getWidth() / 2.0;
	}

	public void setY(double y) {
		this.y = y;
		this.rY = y + this.getHeight() / 2.0;
	}

	public void setWidth(double width) {

		for(Container child : children) {
			child.setrX(width * child.getrX() / this.width);
		}
		this.width = width;

	}

	public void setHeight(double height) {
		for(Container child : children) {
			child.setrY(height * child.getrY() / this.height);
		}
		this.height = height;

	}


	public void setrX(double rX) {
		this.rX = rX;
		this.x = rX - this.getWidth() / 2.0;
	}
	public void setrY(double rY) {
		this.rY = rY;
		this.y = rY - this.getHeight() / 2.0;
	}

	public double getrX() {
		return rX;
	}
	public double getrY() {
		return rY;
	}


	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}
}
