package com.example.note;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import DatabaseUtility.DatabaseHelper;


public class ShowNoteActivity extends AppCompatActivity {
    private int key;
    private EditText showLabel, showSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        key = getIntent().getIntExtra("key", 0);
        showLabel = findViewById(R.id.showLabel);
        showSubject = findViewById(R.id.showSubject);
        DataModel dataModel = Container.list.get(key);
        showLabel.setText(dataModel.getLabel());
        showSubject.setText(dataModel.getSubject());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.show_activity_menues, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.update) {
            DatabaseHelper helper = new DatabaseHelper(this);
            boolean searchResult = helper.search(showLabel.getText().toString(), showSubject.getText().toString());
            if (searchResult == true) {
                Toast.makeText(this, "Note is found change label and subject", Toast.LENGTH_SHORT).show();
            } else {
                helper.updateData(showLabel.getText().toString(), showSubject.getText().toString(), ++key);
                Toast.makeText(getApplicationContext(), "Note Updated", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else if (item.getItemId() == R.id.delete) {
            DatabaseHelper helper = new DatabaseHelper(this);
            Container.list.remove(key);
            helper.deleteData(showLabel.getText().toString(), showSubject.getText().toString());

            Toast.makeText(getApplicationContext(), "Note Deleted", Toast.LENGTH_SHORT).show();

            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
