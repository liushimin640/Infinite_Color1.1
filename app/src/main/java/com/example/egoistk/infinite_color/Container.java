package com.example.egoistk.infinite_color;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by liushimin on 15/10/11.
 */
public class Container{
	private List<Container> children;
	private float x = 0,y = 0,width = 0,height = 0;

	public Container() {
		children = new ArrayList<Container>();
	}


	public void draw(Canvas canvas){
		canvas.save();
		canvas.translate(getX(),getY());
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
