package com.example.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {

	//String create_include_sql = "create table include (PID integer not null, Verfication varchar(6) not null, constraint pk primary key (PID, Verfication), foreign key (PID) references personinfo (PID), foreign key (Verfication) references organization (Verfication));";
	//String create_organization_sql = "create table organization (Verfication varchar(6) primary key not null, OName varchar(20) not null, OInfo varchar(100));";
	//String create_personinfo_sql = "create table personinfo (PID integer primary key autoincrement, PName varchar(20) , Telphone varchar(11), Sex varchar(2));";

	public MyDBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub		
		//db.execSQL(create_personinfo_sql);
		//db.execSQL(create_organization_sql);
		//db.execSQL(create_include_sql);
		db.execSQL("create trigger insert_include_personinfo before insert on include for each row begin select raise(rollback, 'Foreign Key Violation') where (select PID from personinfo where PID = new.PID) is null;end;");
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
//		db.execSQL("drop table if exists personinfo");
//		db.execSQL("drop table if exists organization");
//		db.execSQL("drop table if exists include");
	}

}
