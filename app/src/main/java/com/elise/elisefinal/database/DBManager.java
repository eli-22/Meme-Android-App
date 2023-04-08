package com.elise.elisefinal.database;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.elise.elisefinal.model.Meme;

import java.util.ArrayList;

public class DBManager extends Application {

    public SQLiteDatabase sqLiteDatabase;
    public DBOpenHelper dbOpenHelper;

    @Override
    public void onCreate() {
        super.onCreate();

        dbOpenHelper = new DBOpenHelper(this);

        // Create a reference to database for CRUD
        sqLiteDatabase = dbOpenHelper.getWritableDatabase();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    // Wrapper method is just here to do some additional preparation if needed.
    public void insertInTable(String tableName,
                              ArrayList<ContentValues> values) {
        for (ContentValues value : values) {
            insertInTable(tableName, value);
        }
    }

    public long insertInTable(String tableName,
                              ContentValues values) {

        return sqLiteDatabase.insert(tableName,
                null, values);
    }

    public Cursor queryInTable(String tableName,
                               String[] columns,
                               String selection,
                               String[] selectionArgs) {

        return sqLiteDatabase.query(tableName,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }

    public int deleteRowFromTable(String tableName,
                                  String selection,
                                  String[] selectionArgs) {

        return sqLiteDatabase.delete(tableName,
                selection,
                selectionArgs);
    }

    public int updateTable(String tableName,
                           ContentValues values,
                           String whereClause,
                           String[] whereArgs) {

        return sqLiteDatabase.update(tableName,
                values,
                whereClause,
                whereArgs);
    }

    // This is where we map Java objects to elements in a table.
    public ArrayList<ContentValues> javaObjectToContentValue(ArrayList<Meme> memes) {
        ArrayList<ContentValues> contentValuesArrayList = new ArrayList<>();

        for (Meme meme : memes) {
            ContentValues contentValues = new ContentValues();

            contentValues.put(SQLCommands.COLUMN_ID, meme.getId());
            contentValues.put(SQLCommands.COLUMN_NAME, meme.getName());
            contentValues.put(SQLCommands.COLUMN_URL, meme.getUrl());
            contentValues.put(SQLCommands.COLUMN_WIDTH, meme.getWidth());
            contentValues.put(SQLCommands.COLUMN_HEIGHT, meme.getHeight());
            contentValues.put(SQLCommands.COLUMN_BOX_COUNT, meme.getBoxCount());
            contentValues.put(SQLCommands.COLUMN_CAPTIONS, meme.getCaptions());

            contentValuesArrayList.add(contentValues);
        }
        return contentValuesArrayList;
        // Now they are ready to be saved to the database.
    }

    public ArrayList<Meme> cursorToArrayList(Cursor cursor) {
        ArrayList<Meme> memeList = new ArrayList<>();

        cursor.moveToFirst();
        if (cursor.getCount() != 0) {
            do {
                Meme meme = new Meme();

                meme.setId(cursor.getInt(0));
                meme.setName(cursor.getString(1));
                meme.setUrl(cursor.getString(2));
                meme.setWidth(cursor.getInt(3));
                meme.setHeight(cursor.getInt(4));
                meme.setBoxCount(cursor.getInt(5));
                meme.setCaptions(cursor.getInt(6));

                memeList.add(meme);

            } while (cursor.moveToNext());
        }
        return memeList;
    }
}
