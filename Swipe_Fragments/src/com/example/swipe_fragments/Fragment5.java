package com.example.swipe_fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class Fragment5 extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return (LinearLayout) inflater.inflate(R.layout.fragment5, container, false);
	}
	
}