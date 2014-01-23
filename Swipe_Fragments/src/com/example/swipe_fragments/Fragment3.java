package com.example.swipe_fragments;

import java.io.IOException;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Fragment3 extends Fragment implements SurfaceHolder.Callback{
	
	Camera camera;
	SurfaceView surfaceView;
	SurfaceHolder surfaceHolder;
	boolean previewing = false;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment3, null);
		
		Button buttonStartCameraPreview = (Button) view.findViewById(R.id.startcamerapreview);
		Button buttonStopCameraPreview = (Button) view.findViewById(R.id.stopcamerapreview);
		
		surfaceView = (SurfaceView) view.findViewById(R.id.surfaceview);
		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
		//Start Preview Button Listener
		buttonStartCameraPreview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!previewing) {
					camera = camera.open();
					if (camera != null) {
						try {
							camera.setPreviewDisplay(surfaceHolder);
							camera.setDisplayOrientation(90);
							camera.startPreview();
							previewing = true;
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		
		//Stop Preview Button Listener
		buttonStopCameraPreview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(camera != null && previewing) {
					camera.stopPreview();
					camera.release();
					camera = null;
					previewing = false;
				}
			}
		});
		
		return view;
	}
	
	@Override
	public void onPause() {
		Toast.makeText(getActivity().getApplicationContext(), "Fragment3 onPause()", Toast.LENGTH_SHORT).show();
		
		//Pause Preview
		if(camera != null && previewing) {
			camera.stopPreview();
			camera.release();
			camera = null;
			previewing = false;
		}
		
		super.onPause();
	}
	
	@Override
	public void onResume() {
		Toast.makeText(getActivity().getApplicationContext(), "Fragment3 onResume()", Toast.LENGTH_SHORT).show();
		
		//Start Preview
		if(!previewing) {
			camera = camera.open();
			if (camera != null) {
				try {
					camera.setPreviewDisplay(surfaceHolder);
					camera.setDisplayOrientation(90);
					camera.startPreview();
					previewing = true;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		super.onResume();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}
	
}