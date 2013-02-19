package com.swig;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

	@SuppressLint("DefaultLocale")
	public class TrackerFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";
		private Intent intent;
		
		TextView name;
		static TextView currentBAC;
		static TextView targetBAC;
		static TextView drinkLog;

		static DrinkRepository repo;
		public TrackerFragment() {}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				
	        intent = new Intent(getActivity(), BroadcastService.class);
			
			repo = new DrinkRepository(new DataBaseHelper(getActivity()));
			name = new TextView(getActivity());
			name.setId(1000);
			currentBAC = new TextView(getActivity());
			targetBAC = new TextView(getActivity());
			drinkLog = new TextView(getActivity());
			
			RelativeLayout rl = new RelativeLayout(getActivity());
			LinearLayout top = new LinearLayout(getActivity());
			top.setOrientation(LinearLayout.VERTICAL);
			top.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
			ScrollView sv = new ScrollView(getActivity());

			addName(top);
			addCurrentBAC(top);
			drinkLog.setText(String.format(Locale.US,"Drink Log (%s):%n", getDate()));
			top.addView(drinkLog);
					    
			sv.addView(top);
			sv.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
			rl.addView(sv);
			addAddDrinkButton(rl);
			updateUI(intent);
			
			return rl;
			
		}
				
		private void addName(ViewGroup v)
		{
			name.setText("Andrew");
			name.setTextSize(20);
			name.setGravity(Gravity.CENTER);
			
			v.addView(name);
		}
		
		private void addCurrentBAC(ViewGroup v)
		{
			
			double bac = 0;
			double target = 0.15;
			currentBAC.setText(String.format(Locale.US,"%.3f", bac));
			
			TextView spacer = new TextView(getActivity());
			spacer.setText(" / ");
			
			targetBAC.setText(String.format(Locale.US,"%.3f", target));
			
			LinearLayout ll = new LinearLayout(getActivity());
			ll.setOrientation(LinearLayout.HORIZONTAL);
			
			ll.addView(currentBAC);
			ll.addView(spacer);
			ll.addView(targetBAC);
						
			v.addView(ll);
		}
		
		private void addAddDrinkButton(ViewGroup v)
		{
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		    
			Button addDrink = new Button(getActivity());
			lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
			addDrink.setText("Add Drink");
		    addDrink.setLayoutParams(lp);
		    
		    addDrink.setOnClickListener(new AddDrinkListener());
		    
		    lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		    Button clearDrinks = new Button(getActivity());
			lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			lp.addRule(RelativeLayout.ALIGN_RIGHT);
			clearDrinks.setText("Clear Drinks");
			clearDrinks.setLayoutParams(lp);
		    
			clearDrinks.setOnClickListener(new ClearDrinksListener());
			
			v.addView(addDrink);
			v.addView(clearDrinks);
		}
		
		private class AddDrinkListener implements OnClickListener{

			@Override
			public void onClick(View v) {

				DrinkChooser chooser = new DrinkChooser(null, null, null);
				chooser.show(getActivity().getSupportFragmentManager(), "AlertDialogFragment");		
			}
		}
		
		private class ClearDrinksListener implements OnClickListener{

			@Override
			public void onClick(View v) {
				CharSequence text = "Clearing Drinks..";
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(getActivity(), text, duration);
				
				repo.clearConsumedDrinks();
												
				toast.show();
				
				updateUI(intent);
				
			}
		}
		
		private static String createDrinkLog()
		{
			String s = String.format(Locale.US,"Drink Log (%s):%n", getDate());
			for(Drink d : repo.retrieveConsumedDrinks())
			{
				s = String.format(Locale.US, "%s%n%s - %s", s, "Yuingling ", convertFromMillis(d.getTime()));
			}
			
			return s;
		}
			
		private static String convertFromMillis(long l)
		{
			SimpleDateFormat format = new SimpleDateFormat("hh:mm a", Locale.US);
			format.setTimeZone(TimeZone.getTimeZone("GMT-500"));
			String formatted = format.format(l);
			return formatted;
		}
		
		private static String getDate()
		{
			Time now = new Time();
			now.setToNow();
			int monthNum = now.month;
			String month = "Jan";
			switch(monthNum)
			{
			case 1: month = "Feb";
			case 2: month = "Mar";
			case 3: month = "Apr";
			case 4: month = "May";
			case 5: month = "Jun";
			case 6: month = "Jul";
			case 7: month = "Aug";
			case 8: month = "Sep";
			case 9: month = "Oct";
			case 10: month = "Nov";
			case 11: month = "Dec";
			}
			return String.format(Locale.US, "%s %d, %d", month, now.monthDay, now.year);
		}
		
	    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
	        @Override
	        public void onReceive(Context context, Intent intent) {
	        	updateUI(intent);       
	        }
	    };
	        
		@Override
		public void onResume() {
			super.onResume();		
			getActivity().startService(intent);
			getActivity().registerReceiver(broadcastReceiver, new IntentFilter(BroadcastService.BROADCAST_ACTION));
		}
	 
		@Override
		public void onPause() {
			super.onPause();
			getActivity().unregisterReceiver(broadcastReceiver);
			getActivity().stopService(intent); 		
		}
		
	    public static void updateUI(Intent intent) {

	    	double bac = calculateBAC(repo.retrieveConsumedDrinks());
	    	String s = String.format(Locale.ENGLISH,"%.3f", bac);
	    	currentBAC.setText(s);
	    	
	    	double target = 0.15;
			if (bac < target - 0.015)
				currentBAC.setTextColor(Color.parseColor("#000000"));
			else if (bac > target + 0.015)
				currentBAC.setTextColor(Color.parseColor("#FF6600"));
			else if (bac >= 0.25)
				currentBAC.setTextColor(Color.parseColor("#FF0000"));
			else
				currentBAC.setTextColor(Color.parseColor("#00FF00"));
			
			drinkLog.setText(createDrinkLog());

	    }
	    
	    private static double calculateBAC(ArrayList<Drink> drinks)
	    {
	    	boolean isMale = true;
	    	double bac = 0;
	    	double weightInPounds = 160;
	    	double distributionFactor = isMale? 0.73 : 0.66;
	    	for(Drink d : drinks)
	    	{
	    		double amt = ((d.getSize()*d.getPercent() * 5.14)/(weightInPounds * distributionFactor));
	    		
	    		bac += amt;
	    	}
	    	
	    	if (drinks.size() > 0)
	    	bac -= (0.015 * findDifferenceInHours(drinks.get(0).getTime()));
	    	
	    	return (bac > 0)? bac : 0;
	    }
	    
	    private static double findDifferenceInHours(long time)
	    {
	    	Time now = new Time();
	    	now.setToNow();
	    	return (double)(now.toMillis(false) - time)/3600000.0;
	    }
	    
	}
