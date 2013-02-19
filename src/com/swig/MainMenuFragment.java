package com.swig;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MainMenuFragment extends Fragment {
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				
		View inf = inflater.inflate(R.layout.fragment_main_menu, container, false);
		
		TextView logo = (TextView) inf.findViewById(R.id.textView1);
		Button profiles = (Button) inf.findViewById(R.id.button1);
		Button statistics = (Button) inf.findViewById(R.id.button2);
		Button help = (Button) inf.findViewById(R.id.button3);
		Button about = (Button) inf.findViewById(R.id.button4);
		
		logo.setText("Swig");
		profiles.setText("Manage Profiles");
		statistics.setText("Statistics");
		help.setText("Help");
		about.setText("About");
		
		profiles.setOnClickListener(new profilesListener());
		statistics.setOnClickListener(new statisticsListener());
		help.setOnClickListener(new helpListener());
		about.setOnClickListener(new aboutListener());
		
		return inf;
		
	}
	
	private class profilesListener implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			Dialog x = new Dialog(getActivity());
			x.setTitle("Profiles");
			x.show();
		}
		
	}
	
	private class statisticsListener implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			Dialog x = new Dialog(getActivity());
			x.setTitle("Statistics");
			x.show();
		}
		
	}
	
	private class helpListener implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			Dialog x = new Dialog(getActivity());
			x.setTitle("Help");
			x.show();
		}
		
	}
	
	private class aboutListener implements OnClickListener
	{
		@Override
		public void onClick(View v) {
			Dialog x = new Dialog(getActivity());
			x.setTitle("About");
			x.show();
		}
	}
	
	String reverse(String s)
		{
			if (s.length() <= 1)
				return s;
			else return reverse(s.substring(1)) + s.charAt(0);
		}

}
