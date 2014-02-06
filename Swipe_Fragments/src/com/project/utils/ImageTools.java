package com.project.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;



public class ImageTools {

	private final static int MIN_HEIGHT = 720, MIN_WIDTH = 1280;

	public static int calculateInSampleSize(BitmapFactory.Options options) {
		int inSampleSize = 1;

		Log.v("ImageTools", "options.outHeight is " + options.outHeight);
		Log.v("ImageTools", "options.outWidth is " + options.outWidth);

		if (options.outHeight > MIN_HEIGHT || options.outWidth > MIN_WIDTH) {

			while ((options.outHeight / 2 / inSampleSize) > MIN_HEIGHT
					&& (options.outWidth / 2 / inSampleSize) > MIN_WIDTH) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}


	public static Bitmap decodeResizedBitmap(byte[] bytes) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		
		options.inJustDecodeBounds = true;
		
		BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
		
		options.inSampleSize = calculateInSampleSize(options);
		Log.v("ImageTools", "inSampleSize is " + options.inSampleSize);
		options.inMutable = true;
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
		
	}


}
