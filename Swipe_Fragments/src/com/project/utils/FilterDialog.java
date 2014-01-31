package com.project.utils;



import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.project.utils.Filters;


public class FilterDialog extends DialogFragment {
	
	private int selectedFilter = 0;
	
	public FilterDialog() {
		// required empty for DialogFragment
	}
	
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final int oldFilter = selectedFilter;
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Choose a filter")
				.setSingleChoiceItems(Filters.AVAILABLE_FILTERS, selectedFilter, new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						selectedFilter = which;
						}
					})
				.setPositiveButton(android.R.string.ok, new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						
					}
					
				})
				.setNegativeButton(android.R.string.cancel, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						selectedFilter = oldFilter;
						
					}
				});
		
		
		
		return builder.create();
		
	}
	
}
