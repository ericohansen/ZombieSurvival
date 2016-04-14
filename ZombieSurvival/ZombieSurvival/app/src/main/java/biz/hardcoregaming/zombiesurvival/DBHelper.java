package biz.hardcoregaming.zombiesurvival;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

/**
 * Created by ericohansen on 2/26/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ZombieSurvival.db";
    public static final String TABLE_NAME = "SavedGame";
    public static final String COLUMN_ID = "id";
    public static final String PLAYER = "playerAttributes";     // stores player level, score, and wave number
    public static final String BASE = "baseAttributes";           // stores base level
    public static final String WEAPONSLIST = "weaponList";    // stores a list of weapon names, and levels
    private HashMap hashMap;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE SavedGame " +
                        "(id integer primary key, playerAttributes text, baseAttributes text, weaponList text)"
        );// stores the string values for player attributes, base attributes, and weaponslist into SavedGame table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS SavedGame");
        onCreate(db);
    }

    // creates a content value for sqlite db and inserts into db table
    public boolean insertSavedGame(String playerAtt, String baseAtt, String weaponList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("playerAttributes", playerAtt);
        contentValues.put("baseAttributes", baseAtt);
        contentValues.put("weaponList", weaponList);
        db.insert("SavedGame", null, contentValues);
        return true;
    }

    // returns the data for specific table id
    public Cursor getData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM SavedGame WHERE id=" + id + "", null);
        return res;
    }

    // updates saved game using id of saved game
    public boolean updateSavedGame(Integer id, String playerAtt, String baseAtt, String weaponList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("playerAttributes", playerAtt);
        contentValues.put("baseAttributes", baseAtt);
        contentValues.put("weaponList", weaponList);
        db.update("SavedGame", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteSavedGame(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("SavedGame", "id = ? ", new String[]{Integer.toString(id)});
    }
}
