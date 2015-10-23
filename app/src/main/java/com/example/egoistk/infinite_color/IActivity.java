package com.example.egoistk.infinite_color;

import android.content.Context;
import android.view.View;

/**
 * Created by liushimin on 15/10/21.
 */
public interface IActivity {
	public Context getContext();
	public void changeView(View view,View self);
}
