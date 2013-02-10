package com.swig;
 
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 
 
public class Database extends SQLiteOpenHelper {
 private static final String MYDATABASE = "names";
 private static final int VERSION = 1;
 
 public Database(Context connection) {
  super(connection, MYDATABASE, null, VERSION);
 }
 
 @Override
 public void onCreate(SQLiteDatabase db) {
  db.execSQL("CREATE TABLE mynames(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT);");
 }
 
 @Override
 public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
  db.execSQL("DROP TABLE IF EXIST mynames");
  onCreate(db);
 }
}