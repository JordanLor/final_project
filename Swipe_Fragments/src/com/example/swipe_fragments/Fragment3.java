package com.example.swipe_fragments;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;



public class Fragment3 extends Fragment implements SurfaceHolder.Callback {
	
    public static final String TAG = "Fragment3";

    private int cameraId;
    private Camera camera;
    private SurfaceHolder surfaceHolder;
    private SurfaceView surfaceView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
    
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) { 
    	
    	View view = inflater.inflate(R.layout.fragment3, null);
    	
    	surfaceView = (SurfaceView) view.findViewById(R.id.surfaceview);
        surfaceView.getHolder().addCallback(this);

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
}