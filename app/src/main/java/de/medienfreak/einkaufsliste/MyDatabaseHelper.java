package de.medienfreak.einkaufsliste;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "einkauf.db";

	private static final int DATABASE_VERSION = 1;

	static final String ARTIKEL_TABLE_NAME = "artikel";

	static final String ARTIKEL_ID = "artikel_id";

	static final String ARTIKEL_BEZEICHNUNG = "bezeichnung";

	static final String ARTIKEL_STATUS = "status";

	private static final String ARTIKEL_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ ARTIKEL_TABLE_NAME
			+ " ("
			+ ARTIKEL_ID
			+ " int primary key, "
			+ ARTIKEL_BEZEICHNUNG + " text, " + ARTIKEL_STATUS + " int);";


	public MyDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(ARTIKEL_TABLE_CREATE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("DROP TABLE "+ARTIKEL_TABLE_NAME);
		this.onCreate(db);

	}
	
//	private String buildInsertArtikel(int artikelId, String bezeichnung, int status) {
//		
//		return "INSERT INTO "+ARTIKEL_TABLE_NAME+" ("+ARTIKEL_ID+", "+ARTIKEL_BEZEICHNUNG+", "+ARTIKEL_STATUS+") VALUES("+artikelId+",'"+bezeichnung+"',"+status+")";
//	}

}
