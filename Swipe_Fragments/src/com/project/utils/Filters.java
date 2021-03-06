package com.project.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

public class Filters {
	
	public static final String[] AVAILABLE_FILTERS = {"None", "Greyscale", "Sepia"};
	
	// Access method for filtering
	public static Bitmap filterImage(Bitmap image, int filter) {
		if(filter == 1) {
			return grayscaleFilter(image);
		} else if (filter == 2) {
			return sepiaFilter(image);
		} else
			return image;
	}

	// Returns a sepia filtered bitmap
	private static Bitmap sepiaFilter(Bitmap image) {
		// Values for a sepia tone
		float[] sepiaValues = { 0.393f, 0.769f, 0.189f, 0f, 0f,
								0.349f, 0.686f, 0.168f, 0f, 0f,
								0.272f, 0.534f, 0.131f, 0f, 0f,
								0f, 0f, 0f, 1f, 0f };
		
		Bitmap filteredImage = image.copy(image.getConfig(), true);
		Canvas canvas = new Canvas(filteredImage);
		Paint paint = new Paint();
		ColorMatrix sepiaMatrix = new ColorMatrix();
		sepiaMatrix.set(sepiaValues);
		ColorMatrixColorFilter sepiaFilter = new ColorMatrixColorFilter(sepiaMatrix);
		paint.setColorFilter(sepiaFilter);
		canvas.drawBitmap(image, 0, 0, paint);
		return filteredImage;
	}

	// Returns a grayscale image
	private static Bitmap grayscaleFilter(Bitmap image) {
		Bitmap filteredImage = image.copy(image.getConfig(), true);
		Canvas canvas = new Canvas(filteredImage);
		Paint paint = new Paint();
		ColorMatrix grayscaleMatrix = new ColorMatrix();
		// Grayscale returned by setting saturation to 0
		grayscaleMatrix.setSaturation(0);
		ColorMatrixColorFilter grayscaleFilter = new ColorMatrixColorFilter(grayscaleMatrix);
		paint.setColorFilter(grayscaleFilter);
		canvas.drawBitmap(image, 0, 0, paint);
		return filteredImage;
	}


	
	
}
