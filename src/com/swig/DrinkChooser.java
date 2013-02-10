package com.swig;

import com.swig.Drink.DrinkType;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.Time;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkChooser extends DialogFragment {
	DrinkRepository repo;
	private String type;
	private String brand;
	private String name;
		
	public DrinkChooser(String t, String b, String n)
	{
		type = t;
		brand = b;
		name = n;
	}
	
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    	repo = new DrinkRepository(new DataBaseHelper(getActivity()));
    	
    	if (savedInstanceState == null) savedInstanceState = new Bundle();
    	    	
        // Use the Builder class for convenient dialog construction
		 AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		 builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int id) {}});
		 
		 
    	if (type == null)
    	{
    		builder = chooseType(builder);
    	}
    	else if (brand == null)
    	{
    		builder = chooseBrand(builder);
    	}
    	else if (name == null)
    	{
    		builder = chooseName(builder);
    	}
    	else
    	{
    		builder = chooseSize(builder);
    	}
       
        // Create the AlertDialog object and return it
        return builder.create();
    }
      
    private AlertDialog.Builder chooseType(final AlertDialog.Builder builder)
    {
    	return builder.setMessage("What are you drinking?")
	               .setPositiveButton("Next", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {

	                	   DrinkChooser chooser = new DrinkChooser("Beer", brand, name);
	                	   chooser.show(getFragmentManager(), "BrandChooser");
	                	   chooseBrand(builder).create();
	                   }
	               });
    }
    
    private AlertDialog.Builder chooseBrand(final AlertDialog.Builder builder)
    {
    	return 	builder.setMessage("What brand?")
    			.setPositiveButton("Next", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int id) {
	
             	   DrinkChooser chooser = new DrinkChooser("Beer", "Yuingling", name);
             	   chooser.show(getFragmentManager(), "NameChooser");
	            }
	        }).setNeutralButton("Back", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
	             	   DrinkChooser chooser = new DrinkChooser(null, null, null);
	             	   chooser.show(getFragmentManager(), "NameChooser");
					
				}
			});
    }
    
    private AlertDialog.Builder chooseName(final AlertDialog.Builder builder)
    {
    	return 	builder.setMessage("What is the drink name?")
    			.setPositiveButton("Next", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

          	   DrinkChooser chooser = new DrinkChooser("Beer", "Yuingling", "Lager");
          	   chooser.show(getFragmentManager(), "NameChooser");
          	   }
        }).setNeutralButton("Back", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
          	   DrinkChooser chooser = new DrinkChooser("Beer", null, null);
          	   chooser.show(getFragmentManager(), "NameChooser");
				
			}
		});
    }
    
    private AlertDialog.Builder chooseSize(final AlertDialog.Builder builder)
    {
    	return 	builder.setMessage("What size?")
    			.setPositiveButton("Next", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

        		CharSequence text = "Adding Yuingling!";
        		int duration = Toast.LENGTH_SHORT;
        		Toast toast = Toast.makeText(getActivity(), text, duration);
        		toast.show();
            
        		Time now = new Time();
        		now.setToNow();
        		repo.consumeDrink(new Drink(Drink.DrinkType.beer, 0.044, 12, now.toMillis(true)));
            	
				Tracker.updateUI(new Intent(getActivity(), BroadcastService.class));
            }
        }).setNeutralButton("Back", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
          	   DrinkChooser chooser = new DrinkChooser("Beer", "Yuingling", null);
          	   chooser.show(getFragmentManager(), "NameChooser");
				
			}
		});
    }
}
