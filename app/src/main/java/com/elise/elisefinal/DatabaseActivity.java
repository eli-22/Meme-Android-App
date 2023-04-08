package com.elise.elisefinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.elise.elisefinal.model.Meme;

import java.io.Serializable;
import java.util.ArrayList;

public class DatabaseActivity extends AppCompatActivity {

    ListView dataListView;
    CellControllerBaseAdapter cellControllerBaseAdapter;
    ArrayList<Meme> memeArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("intentExtra");
        Serializable bundledMemeList = bundle.getSerializable("bundleExtra");

        memeArrayList = (ArrayList<Meme>) bundledMemeList;

        initializeCustomList();
    }

    private void initializeCustomList() {
        dataListView = findViewById(R.id.dataListView);

        cellControllerBaseAdapter = new CellControllerBaseAdapter(this, memeArrayList);
        dataListView.setAdapter(cellControllerBaseAdapter);
    }
}