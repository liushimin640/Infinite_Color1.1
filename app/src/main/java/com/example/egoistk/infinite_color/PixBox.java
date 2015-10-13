package com.example.egoistk.infinite_color;

import android.util.ArraySet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
