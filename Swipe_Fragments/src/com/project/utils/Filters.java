package com.project.utils;

import android.graphics.Bitmap;

public class Filters {
	
	public static final String[] AVAILABLE_FILTERS = {"None", "Greyscale", "Sepia"};
	
	public Bitmap filterImage(Bitmap image, int filter) {
		if(filter == 1) {
			return grayscaleFilter(image);
		} else if (filter == 2) {
			return sepiaFilter(image);
		} else
			return image;
	}

	private Bitmap sepiaFilter(Bitmap image) {
		// TODO Auto-generated method stub
		return image;
	}

	private Bitmap grayscaleFilter(Bitmap image) {
		// TODO Auto-generated method stub
		return image;
	}
	

}
