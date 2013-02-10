package com.swig;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;

public class DrinkRepository extends Activity{
	
	DataBaseHelper db;
	
	public DrinkRepository(DataBaseHelper dataBase){
		db = dataBase;

		try {
			db.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<Drink> getAllByType(Drink.DrinkType type){
		SQLiteDatabase sqlDB = db.getReadableDatabase();
		String queryString = "SELECT * FROM Consumed";
		String[] whereArgs = new String[0];// {
//			    "value1",
//			    "value2"
//			};
		return convertToArrayList(sqlDB.rawQuery(queryString, whereArgs));
	}
	
	public void consumeDrink(Drink d)
	{
		SQLiteDatabase sqlDB = db.getWritableDatabase();
		String queryString = "INSERT INTO Consumed VALUES ( ?, ?, ?, ?)";
		String type = String.valueOf(d.getType().getValue());
		String percent = String.valueOf(d.getPercent());
		String size = String.valueOf(d.getSize());
		Time now = new Time();
		now.setToNow();
		String date = String.valueOf(now.toMillis(false));
		String[] args = new String[] {type, percent, size, date};
		sqlDB.execSQL(queryString, args);
		

	}
	
	public ArrayList<Drink> retrieveConsumedDrinks()
	{
		SQLiteDatabase sqlDB = db.getReadableDatabase();
		String queryString = "SELECT * FROM Consumed";
		String[] whereArgs = new String[0];
		return convertToArrayList(sqlDB.rawQuery(queryString, null));
	}
	
	public void clearConsumedDrinks()
	{
		SQLiteDatabase sqlDB = db.getWritableDatabase();
		String queryString = "DELETE FROM Consumed";
		sqlDB.execSQL(queryString);
			
	}
	
	private Drink convertToDrink(Cursor c)
	{
		return new Drink(Drink.DrinkType.values()[c.getInt(0)], c.getDouble(1), c.getDouble(2), c.getLong(3));
	}
	
	
	private ArrayList<Drink> convertToArrayList(Cursor c)
	{
		ArrayList<Drink> drinks = new ArrayList<Drink>();
		if (c != null) {

		    while(c.moveToNext()) {
		        drinks.add(convertToDrink(c));
		    }
		    c.close();
		}
		return drinks;
	}

}
