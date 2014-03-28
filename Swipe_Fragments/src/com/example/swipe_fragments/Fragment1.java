

package com.example.swipe_fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;





public class Fragment1 extends Fragment {
	
	private static final int REAUTH_ACTIVIY_CODE = 100;
	private static final String TAG = "Fragment1";
	private UiLifecycleHelper uiHelper;
	private ProfilePictureView profilePictureView;
	
	private ListView listView;
	private List<BaseListElement> listElements;
	
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment1, null);
		
		//Find user's profile picture custom view
		profilePictureView = (ProfilePictureView) view.findViewById(R.id.selection_profile_pic);
		profilePictureView.setCropped(true);
		
		
		
		LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
		authButton.setFragment(this);
		
		listView = (ListView)view.findViewById(R.id.selection_list);
		listElements = new ArrayList<BaseListElement>();
		listElements.add(new PeopleListElement(0));
		listView.setAdapter(new ActionListAdapter(getActivity(), R.id.selection_list, listElements));
		
		Session session = Session.getActiveSession();
				
		return view;
	}
	
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
		if (state.isOpened()) {
			Log.i(TAG, "Logged in...");
		} else if (state.isClosed()) {
			Log.i(TAG, "Logged out...");
		}
		
		if (session != null && session.isOpened()) {
			makeMeRequest(session);
		}
	}
	
	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};
	
	private void makeMeRequest(final Session session) {
		//Make an API call to get user data and define a
		//new callback to handle the response.
		Request request = Request.newMeRequest(session, new Request.GraphUserCallback() {
			@Override
			public void onCompleted(GraphUser user, Response response) {
				//If the response is successful
				if (session == Session.getActiveSession()) {
					if (user != null) {
						//Set the id for the ProfilePictureView
						//view that in turn displays the profile picture.
						profilePictureView.setProfileId(user.getId());
						
					}
				}
				if (response.getError() != null) {
					//Handle errors.
				}
			}
		});
		request.executeAsync();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(getActivity(), callback);
		uiHelper.onCreate(savedInstanceState);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		uiHelper.onResume();
		
		// For scenarios where the main activity is launched and user
	    // session is not null, the session state change notification
	    // may not be triggered. Trigger it if it's open/closed.
	    Session session = Session.getActiveSession();
	    if (session != null &&
	           (session.isOpened() || session.isClosed()) ) {
	        onSessionStateChange(session, session.getState(), null);
	    }

	    uiHelper.onResume();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    if (requestCode == REAUTH_ACTIVIY_CODE) {
	      uiHelper.onActivityResult(requestCode, resultCode, data);
	    } else if (resultCode == Activity.RESULT_OK) {
	    	//The following line shouldn't be here according to FB. For some reason REAUTH and activityCode are not matching, will checkout later...
	    	uiHelper.onActivityResult(requestCode, resultCode, data); 
	    } 
	    
	    
	}
	
	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}
	
	private void startPickerActivity(Uri data, int requestCode) {
	     Intent intent = new Intent();
	     intent.setData(data);
	     intent.setClass(getActivity(), PickerActivity.class);
	     startActivityForResult(intent, requestCode);
	 }
	
	private class ActionListAdapter extends ArrayAdapter<BaseListElement> {
	    private List<BaseListElement> listElements;

	    public ActionListAdapter(Context context, int resourceId, 
	                             List<BaseListElement> listElements) {
	        super(context, resourceId, listElements);
	        this.listElements = listElements;
	        // Set up as an observer for list item changes to
	        // refresh the view.
	        for (int i = 0; i < listElements.size(); i++) {
	            listElements.get(i).setAdapter(this);
	        }
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View view = convertView;
	        if (view == null) {
	            LayoutInflater inflater =
	                    (LayoutInflater) getActivity()
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            view = inflater.inflate(R.layout.listitem, null);
	        }

	        BaseListElement listElement = listElements.get(position);
	        if (listElement != null) {
	            view.setOnClickListener(listElement.getOnClickListener());
	            ImageView icon = (ImageView) view.findViewById(R.id.icon);
	            TextView text1 = (TextView) view.findViewById(R.id.text1);
	            TextView text2 = (TextView) view.findViewById(R.id.text2);
	            if (icon != null) {
	                icon.setImageDrawable(listElement.getIcon());
	            }
	            if (text1 != null) {
	                text1.setText(listElement.getText1());
	            }
	            if (text2 != null) {
	                text2.setText(listElement.getText2());
	            }
	        }
	        return view;
	    }

	}
	
	private class PeopleListElement extends BaseListElement {

	    public PeopleListElement(int requestCode) {
	        super(getActivity().getResources().getDrawable(R.drawable.action_people),
	              "Send To:",
	              "Select Friends",
	              requestCode);
	    }

	    @Override
	    protected View.OnClickListener getOnClickListener() {
	        return new View.OnClickListener() {
	            @Override
	            public void onClick(View view) {
	            	startPickerActivity(PickerActivity.FRIEND_PICKER, getRequestCode());
	            }
	        };
	    }
	}
	
}
