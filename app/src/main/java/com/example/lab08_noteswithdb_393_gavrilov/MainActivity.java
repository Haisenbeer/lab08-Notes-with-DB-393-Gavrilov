package com.example.lab08_noteswithdb_393_gavrilov;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

//393 Gavrilov
public class MainActivity extends AppCompatActivity {

    ListView lstctl;
    ArrayList<Note> lst = new ArrayList<Note> ();
    ArrayAdapter<Note> adp;

    Context ctx;

    void update_list()
    {
        lst.clear();
        g.notes.getAllNotes(lst);
        adp.notifyDataSetChanged();
    }

    //393 Gavrilov
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        update_list();
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = this;

        g.notes = new DB(this, "notes.db", null, 1);

        lstctl = findViewById(R.id.lst_note);
        adp = new ArrayAdapter<Note>(this, android.R.layout.simple_list_item_1, lst);
        lstctl.setAdapter(adp);

        lstctl.setOnItemClickListener((parent, view, position, id) ->
        {
            Note n = adp.getItem(position);
            Intent i = new Intent(ctx, SecondActivity.class);
            i.putExtra("note-id",n.id);
            i.putExtra("note-txt",n.txt);
            startActivityForResult(i, 1);
        });

        update_list();
    }

    //393 Gavrilov
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        int id = item.getItemId();

        switch (id)
        {
            case R.id.itm_new:
            {
                int nid = g.notes.getMaxId() + 1;
                g.notes.addNote(nid, "New Note");
                update_list();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}