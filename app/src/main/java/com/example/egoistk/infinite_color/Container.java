package com.example.egoistk.infinite_color;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.egoistk.infinite_color.Launch.Circle;
import com.example.egoistk.infinite_color.Launch.ColorfulPix;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by liushimin on 15/10/21.
 */
public class Container {
	public CopyOnWriteArrayList<Container> children;
	public float x ,y ,width ,height ;
	public Container parent;
	public String className;

	public Container() {
		children = new CopyOnWriteArrayList<Container>();
		className = null;
		parent = null;
	}

	public CopyOnWriteArrayList<Container> getChildren() {
		return children;
	}


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

	}
	public void addChild(Container child){
		children.add(child);
		child.parent = this;
	}
	public void removeChild(Container child){
		children.remove(child);
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
