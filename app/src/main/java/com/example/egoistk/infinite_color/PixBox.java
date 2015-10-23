package com.example.egoistk.infinite_color;

import com.example.egoistk.infinite_color.Launch.ColorfulPix;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by liushimin on 15/10/11.
 */
public class PixBox extends CopyOnWriteArrayList<ColorfulPix> {
	int room;
	public PixBox() {
		room = 250;
	}


}
