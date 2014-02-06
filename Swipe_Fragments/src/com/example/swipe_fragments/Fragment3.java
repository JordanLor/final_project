package com.example.swipe_fragments;

import java.io.IOException;

import com.project.utils.FilterDialog;
import com.project.utils.Filters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class Fragment3 extends Fragment implements SurfaceHolder.Callback, FilterDialog.FilterDialogListener {

	public static final String TAG = "Fragment3";

	private final int REQUEST_CODE = 3;

	private int cameraId;
	private Camera camera;
	private SurfaceHolder surfaceHolder;
	private SurfaceView surfaceView;
	private ImageView photoView;
	private ImageView shutterButton;
	private ImageView filterButton;
	private FilterDialog filterDialog;
	private Bitmap bitmapPicture;
	private Bitmap filteredBitmap;


	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) { 

		View view = inflater.inflate(R.layout.fragment3, null);

		surfaceView = (SurfaceView) view.findViewById(R.id.surfaceview);
		surfaceView.getHolder().addCallback(this);

		photoView = (ImageView) view.findViewById(R.id.photoView);
		shutterButton = (ImageView) view.findViewById(R.id.shutterButton);
		filterButton = (ImageView) view.findViewById(R.id.filterButton);
		filterDialog = new FilterDialog();



		// Needed to pass messages back to this fragment
		filterDialog.setTargetFragment(this, REQUEST_CODE);

		shutterButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity().getApplicationContext(), "SNAP!", Toast.LENGTH_SHORT).show();	
				camera.takePicture(mShutterCallback, mPictureCallback_RAW, mPictureCallback_JPG);
			}

			ShutterCallback mShutterCallback = new ShutterCallback() {
				@Override
				public void onShutter() {
					//TODO Auto-generated method stub
				}
			};

			PictureCallback mPictureCallback_RAW = new PictureCallback() {
				@Override
				public void onPictureTaken(byte[] arg0, Camera arg1) {
					//TODO Auto-generated method stub
				}
			};

			PictureCallback mPictureCallback_JPG = new PictureCallback() {
				@Override
				public void onPictureTaken(byte[] arg0, Camera arg1) {

					
					// Reduce image size to closest possible to 720x 1280 while maintaining
					// image ratio (solves out of memory issues on real android devices,
					// may not be noticed on emulated devices)
					bitmapPicture = com.project.utils.ImageTools.decodeResizedBitmap(arg0);
					
					// Fix image rotation
					Matrix matrix = new Matrix();
					matrix.postRotate(90);
					bitmapPicture = Bitmap.createBitmap(bitmapPicture, 0, 
							0, bitmapPicture.getWidth(), bitmapPicture.getHeight(), matrix, true);
					// Show filter button
					filterButton.setVisibility(0);
				}
			};

		});

		filterButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {		
				filterDialog.show(getActivity().getSupportFragmentManager(), "filters");
			} });


		return view;
	}



	@Override
	public void onResume() {
		super.onResume();

		try {
			camera = Camera.open(cameraId);
		} catch (Exception exception) {
			Log.e(TAG, "Can't open camera with id " + cameraId, exception);
			return;
		}
	}


	@Override
	public void onPause() {
		super.onPause();

		stopCameraPreview();
		camera.release();
	}


	private synchronized void startCameraPreview() {
		camera.setDisplayOrientation(90);
		setupCamera();

		try {
			camera.setPreviewDisplay(surfaceHolder);
			camera.startPreview();
		} catch (IOException exception) {
			Log.e(TAG, "Can't start camera preview due to IOException", exception);
		}
	}


	private synchronized void stopCameraPreview() {
		try {
			camera.stopPreview();
		} catch (Exception exception) {
			Log.e(TAG, "Exception during stopping camera preview", exception);
		}
	}


	public void setupCamera() {
		Camera.Parameters parameters = camera.getParameters();

		camera.setParameters(parameters);
	}


	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		this.surfaceHolder = holder;

		startCameraPreview();
	}


	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// The interface forces us to have this method but we don't need it
		// up to now.
	}


	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// We don't need to handle this case as the fragment takes care of
		// releasing the camera when needed.
	}

	// Handle ok dialog clicks
	@Override
	public void onFilterDialogPositiveClick(FilterDialog dialog) {
		if(dialog.isChanged()) {
			if(dialog.getSelectedFilter() == 0) {
				photoView.setImageBitmap(bitmapPicture);
				// Recycle unused filteredImage
				if(filteredBitmap != null) {
					filteredBitmap.recycle();
				}
			} else {
				filteredBitmap = Filters.filterImage(bitmapPicture, dialog.getSelectedFilter());
				photoView.setImageBitmap(filteredBitmap);
			}
		}	
	}

	// Handle cancel dialog clicks
	@Override
	public void onFilterDialogNegativeClick(FilterDialog dialog) {
		// Stub - as of yet unused, may not be needed unless a "filter canceled"
		// message is desired
	}
}