package com.example.swipe_fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class MainActivity extends FragmentActivity {

	private PageAdapter mPageAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewpager_layout);
		
		List<Fragment> fragments = new ArrayList<Fragment>();
		fragments.add(Fragment.instantiate(this, Fragment1.class.getName()));
		fragments.add(Fragment.instantiate(this, Fragment2.class.getName()));
		fragments.add(Fragment.instantiate(this, Fragment3.class.getName()));
		fragments.add(Fragment.instantiate(this, Fragment4.class.getName()));
		fragments.add(Fragment.instantiate(this, Fragment5.class.getName()));
		
		mPageAdapter = new PageAdapter(getSupportFragmentManager(), fragments);
		ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
		pager.setAdapter(mPageAdapter);
	} 
}
