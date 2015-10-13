package com.example.egoistk.infinite_color;

/**
 * Created by liushimin on 15/10/13.
 */
public class Circle {
	double x, y, width, height;
	int color;

	public Circle(double x, double y, double width, double heihgt, int color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = heihgt;
		this.color = color;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
}
