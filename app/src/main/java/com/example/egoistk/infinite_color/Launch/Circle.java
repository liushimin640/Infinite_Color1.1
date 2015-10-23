package com.example.egoistk.infinite_color.Launch;

import android.graphics.Paint;

/**
 * Created by liushimin on 15/10/13.
 */
public class Circle {
	float x, y, width;


	public Circle(float x, float y, float width) {
		this.x = x;
		this.y = y;
		this.width = width;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}
}