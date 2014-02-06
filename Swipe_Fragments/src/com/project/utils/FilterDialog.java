package com.project.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.project.utils.Filters;


public class FilterDialog extends DialogFragment {
	
	
	// Interface for passing event back to host
	public interface FilterDialogListener {
		public void onFilterDialogPositiveClick(FilterDialog dialog);
		public void onFilterDialogNegativeClick(FilterDialog dialog);
	}
	
	// Class fields
	private int selectedFilter = 0;
	private FilterDialogListener mListener;
	private boolean changed = false;
	
	// Default ctor
	public FilterDialog() {
		// required empty for DialogFragment
	}
	
	// Called on creation of dialog
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final int oldFilter = selectedFilter;
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		mListener = (FilterDialogListener) getTargetFragment();
		builder.setTitle("Choose a filter")
				.setSingleChoiceItems(Filters.AVAILABLE_FILTERS, selectedFilter, new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						selectedFilter = which;
						}
					})
				.setPositiveButton(android.R.string.ok, new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						if(oldFilter != selectedFilter) {
							changed = true;
						} else {
							changed = false;
						}
						mListener.onFilterDialogPositiveClick(FilterDialog.this);
					}
					
				})
				.setNegativeButton(android.R.string.cancel, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						selectedFilter = oldFilter;
						changed = false;
						mListener.onFilterDialogNegativeClick(FilterDialog.this);
					}
				});
		
		return builder.create();
		
	}
	
	public int getSelectedFilter() {
		return selectedFilter;
	}
	
	public boolean isChanged() {
		return changed;
	}
	
}
