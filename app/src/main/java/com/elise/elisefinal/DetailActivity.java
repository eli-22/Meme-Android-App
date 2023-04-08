package com.elise.elisefinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.elise.elisefinal.manageJSON.Util;
import com.elise.elisefinal.model.Meme;

import java.io.Serializable;

public class DetailActivity extends AppCompatActivity {

    Meme meme;
    TextView textViewMemeID, textViewMemeName, textViewUrl;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initialize();
        displayData();
    }

    private void initialize(){
        textViewMemeID = findViewById(R.id.textViewID);
        textViewMemeName = findViewById(R.id.textViewName);
        textViewUrl = findViewById(R.id.textViewUrl);
        imageView = findViewById(R.id.imageViewPhoto);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("intentExtra");
        Serializable bundledMeme = bundle.getSerializable("bundleExtra");

        meme = (Meme) bundledMeme;
    }

    private void displayData(){
        String memeID = String.valueOf(meme.getId());
        textViewMemeID.setText(memeID);
        textViewMemeName.setText(meme.getName());
        textViewUrl.setText(meme.getUrl());

        displayImage();
    }

    private void displayImage() {
        String memeUrl = meme.getUrl();
        Util.loadImage(imageView, memeUrl);
    }
}