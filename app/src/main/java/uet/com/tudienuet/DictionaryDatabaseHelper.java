package uet.com.tudienuet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ListView;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

public class DictionaryDatabaseHelper extends SQLiteAssetHelper {

	final static String DICTIONARY_DATABASE="quiz";
	final static String TABLE = "dictionary";
	final static String ITEM_ID_COLUMN="_id";
	final static String WORD_COLUMN="word";
	final static String DEFINITION_COLUMN="meaning";
	final static int DATA_VERSION = 2;


	public DictionaryDatabaseHelper(Context context) {
		super(context, DICTIONARY_DATABASE, null, DATA_VERSION);
	}


	public ArrayList<WordDefinition> getSearchWords(String word){
		ArrayList<WordDefinition> arrayList=new ArrayList<WordDefinition>();
		SQLiteDatabase database=this.getReadableDatabase();

		String selectAllQueryString="SELECT * FROM "+TABLE+ " WHERE "+WORD_COLUMN+" like '"+word+ "%'";
		Cursor cursor=database.rawQuery(selectAllQueryString, null);

		if (cursor.moveToFirst()) {
			do {
				WordDefinition wordDefinition=new WordDefinition(cursor.getString(cursor.getColumnIndex(WORD_COLUMN)), cursor.getString(cursor.getColumnIndex(DEFINITION_COLUMN)));
				arrayList.add(wordDefinition);
			} while (cursor.moveToNext());
		}
		return arrayList;
	}

	public ArrayList<WordDefinition> getAllWords() {
		ArrayList<WordDefinition> arrayList=new ArrayList<WordDefinition>();
		SQLiteDatabase database=this.getReadableDatabase();

		String selectAllQueryString="SELECT * FROM "+TABLE + " limit 30";
		Cursor cursor=database.rawQuery(selectAllQueryString, null);

		if (cursor.moveToFirst()) {
			do {
				WordDefinition wordDefinition=new WordDefinition(cursor.getString(cursor.getColumnIndex(WORD_COLUMN)), cursor.getString(cursor.getColumnIndex(DEFINITION_COLUMN)));
				arrayList.add(wordDefinition);
			} while (cursor.moveToNext());
		}
		return arrayList;
	}

	public ArrayList<WordDefinition> getWordDefinition(String word, ListView dictionaryListView) {
		ArrayList searchwords = this.getSearchWords(word);
		return searchwords;
	}

	public WordDefinition getWordDefinition(long id) {
		SQLiteDatabase database=this.getReadableDatabase();
		WordDefinition wordDefinition=null;

		String selectQueryString="SELECT * FROM "+TABLE+ " WHERE "+ITEM_ID_COLUMN+" = '"+id+ "'";
		Cursor cursor=database.rawQuery(selectQueryString, null);

		if (cursor.moveToFirst()) {
			wordDefinition=new WordDefinition(cursor.getString(cursor.getColumnIndex(WORD_COLUMN)), cursor.getString(cursor.getColumnIndex(DEFINITION_COLUMN)));

		}

		return wordDefinition;

	}



}
