package com.project.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

public class Filters {
	
	public static final String[] AVAILABLE_FILTERS = {"None", "Greyscale", "Sepia"};
	
	public static Bitmap filterImage(Bitmap image, int filter) {
		if(filter == 1) {
			return grayscaleFilter(image);
		} else if (filter == 2) {
			return sepiaFilter(image);
		} else
			return image;
	}

	private static Bitmap sepiaFilter(Bitmap image) {
		// TODO Auto-generated method stub
		return image;
	}

	private static Bitmap grayscaleFilter(Bitmap image) {
		Bitmap bm = image.copy(image.getConfig(), true);
		Canvas c = new Canvas(bm);
		Paint paint = new Paint();
		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(0);
		ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
		paint.setColorFilter(f);
		c.drawBitmap(image, 0, 0, paint);
		return bm;
	}


}
