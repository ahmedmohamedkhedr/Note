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


public class AddNoteActivity extends AppCompatActivity {
    private EditText editLabel, editSubject;
    private String label, subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_menues, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            editLabel = findViewById(R.id.addLabel);
            editSubject = findViewById(R.id.addSubject);
            label = editLabel.getText().toString();
            subject = editSubject.getText().toString();
            DatabaseHelper helper = new DatabaseHelper(this);
            boolean searchResult = helper.search(label, subject);
            if (searchResult == true) {
                Toast.makeText(this, "Note is found change label and subject", Toast.LENGTH_SHORT).show();
            } else {
                helper.putData(label, subject);
                Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
