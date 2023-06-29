package kr.co.company.minigame_1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "game_database";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "game_scores";
    private static final String COLUMN_PLAY_NO = "playNo";
    private static final String COLUMN_PLAYER_NAME = "playerName";
    private static final String COLUMN_ENEMY_KILL_COUNT = "enemyKillCount";
    private static final String COLUMN_TIME_STRING = "timeString";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_PLAY_NO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PLAYER_NAME + " TEXT, " +
                COLUMN_ENEMY_KILL_COUNT + " INTEGER, " +
                COLUMN_TIME_STRING + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableQuery);
        onCreate(db);
    }

    @Override
    public SQLiteDatabase getReadableDatabase(){
        return super.getReadableDatabase();
    }
}