package com.example.lab08_noteswithdb_393_gavrilov;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

//393 Gavrilov
public class DB extends SQLiteOpenHelper{
    public DB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE notes (id INT, txt TEXT);";
        db.execSQL(sql);
    }

    public int getMaxId()
    {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT MAX(id) FROM notes;";
        Cursor cur = db.rawQuery(sql, null);
        if (cur.moveToFirst()==true) return cur.getInt(0);
        return 0;
    }

    public void addNote(int id, String stxt)
    {
        String sid = String.valueOf(id);
        SQLiteDatabase db = getReadableDatabase();
        String sql = "INSERT INTO notes VALUES(" + sid + ", '" + stxt + "');";
        db.execSQL(sql);
    }

    //393 Gavrilov
    public void getAllNotes(ArrayList<Note> lst)
    {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT id, txt FROM notes;";
        Cursor cur = db.rawQuery(sql, null);
        if (cur.moveToFirst() == true)
        {
            do {
                Note n = new Note();
                n.id = cur.getInt(0);
                n.txt = cur.getString(1);
                lst.add(n);
            }while (cur.moveToNext() == true);
        }
    }

    public void updateNote(int id, String stxt)
    {
        String sid = String.valueOf(id);
        String sql = "UPDATE notes SET txt = '" + stxt + "' WHERE id = " + sid + ";";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }

    public void deleteNote(int id)
    {
        String sid = String.valueOf(id);
        String sql = "DELETE FROM notes WHERE id = " + sid + ";";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
