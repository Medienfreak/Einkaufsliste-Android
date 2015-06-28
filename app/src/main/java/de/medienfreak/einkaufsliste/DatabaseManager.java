package de.medienfreak.einkaufsliste;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager {

	private SQLiteDatabase database;

//	private MyDatabaseHelper dbHelper;

	private String[] allColums = { MyDatabaseHelper.ARTIKEL_ID,
			MyDatabaseHelper.ARTIKEL_BEZEICHNUNG,
			MyDatabaseHelper.ARTIKEL_STATUS };

	public void open(Context context) {
		database = new MyDatabaseHelper(context).getWritableDatabase();
	}

	public void close() {
		database.close();
	}

	public String buildInsertArtikel(int artikelId, String bezeichnung,
			int status) {

		return "INSERT INTO " + MyDatabaseHelper.ARTIKEL_TABLE_NAME + " ("
				+ MyDatabaseHelper.ARTIKEL_ID + ", "
				+ MyDatabaseHelper.ARTIKEL_BEZEICHNUNG + ", "
				+ MyDatabaseHelper.ARTIKEL_STATUS + ") VALUES(" + artikelId
				+ ",'" + bezeichnung + "'," + status + ")";
	}

	public void insertArtikel(int artikelId, String bezeichnung, int status) {
		database.execSQL(buildInsertArtikel(artikelId, bezeichnung, status));
	}

	public List<String> getArtikel() {

		List<String> liste = new ArrayList<String>();

		Cursor cursor = database.query(MyDatabaseHelper.ARTIKEL_TABLE_NAME,
				allColums, null, null, null, null, null);
		cursor.moveToFirst();

		if (cursor.getCount() > 0) {
			while (!cursor.isAfterLast()) {

				// int id = cursor.getInt(0);
				String text = cursor.getString(1);
				liste.add(text);
				// int value = cursor.getInt(2);
				cursor.moveToNext();
			}
		}

		return liste;
	}

	public boolean deleteArtikel(String bezeichnung) {
		
		database.execSQL("DELETE FROM "+MyDatabaseHelper.ARTIKEL_TABLE_NAME+" WHERE "+MyDatabaseHelper.ARTIKEL_BEZEICHNUNG+"='"+bezeichnung+"';"  );
//		database.delete(MyDatabaseHelper.ARTIKEL_TABLE_NAME, MyDatabaseHelper.ARTIKEL_BEZEICHNUNG, new String[]{bezeichnung});
		return true;
	}
	
}
