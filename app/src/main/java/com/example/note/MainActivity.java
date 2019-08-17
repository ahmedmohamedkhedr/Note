package com.example.note;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import DatabaseUtility.DatabaseHelper;


public class MainActivity extends AppCompatActivity implements Adapter.OnNoteListener {
    private ImageView imageView;
    private RecyclerView recyclerView;
    private Adapter adapter;
    private List list = null;
   private DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.noteList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        imageView = findViewById(R.id.imageView);
        if (list==null){
            imageView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            helper  = new DatabaseHelper(this);
            list = helper.getData();
            list = Container.list;
            adapter = new Adapter(list,this);
            Container.adapter = adapter;
            recyclerView.setAdapter(Container.adapter);

        }
        catch (Exception e){
            Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();
        }
        finally {
            if (list.isEmpty()==true){
                imageView.setVisibility(View.VISIBLE);
            }else {
                imageView.setVisibility(View.GONE);
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.close();
    }

    public void onClickAddNote(View view) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(this, ShowNoteActivity.class);
        intent.putExtra("key", position);
        startActivity(intent);
    }
}
