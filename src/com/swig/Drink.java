package com.swig;

import java.io.Serializable;
import java.util.Calendar;

import android.text.format.Time;

public class Drink implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 26908712757342670L;

	public enum DrinkType{beer(1), wine(2), spirit(3), shooter(4), mixed(5);
		
		private int value;
		
		public int getValue()
		{
			return value;
		}

    private DrinkType(int value) {
            this.value = value;
    }
};
	private DrinkType type;
	private double percent;
	private double size;
	private long time;
	
	public Drink(DrinkType drinkType, double pct, double sizeOz, long drinkTime)
	{
		type = drinkType;
		percent = pct;
		size = sizeOz;
		time = drinkTime;
	}
	
	public DrinkType getType()
	{
		return type;
	}
	
	public double getPercent()
	{
		return percent;
	}
	
	public double getSize()
	{
		return size;
	}
	
	public long getTime()
	{
		return time;
	}
	
}
