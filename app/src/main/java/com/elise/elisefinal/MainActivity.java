package com.elise.elisefinal;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.elise.elisefinal.database.DBManager;
import com.elise.elisefinal.database.SQLCommands;
import com.elise.elisefinal.manageJSON.MemeJsonParser;
import com.elise.elisefinal.manageJSON.Util;
import com.elise.elisefinal.model.Meme;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemLongClickListener {

    private OkHttpClient okHttpClient;
    String apiUrlString = "https://api.imgflip.com/get_memes";
    ArrayList<Meme> memeArrayList;

    Button btnDatabase;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;

    DBManager dbManager;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbManager = (DBManager) getApplication();
        initialize();
    }

    private void initialize(){

        btnDatabase = findViewById(R.id.btnDatabase);
        btnDatabase.setOnClickListener(this);

        memeArrayList = new ArrayList<>();
        getWebService();

    }

    private void getWebService() {
        okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(apiUrlString).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("onFailure called.");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String data = response.body().string();
                            // Check data from JSON
                            // System.out.println(data);
                            parseJson(data);
                        } catch (IOException ioe) {
                            System.out.println("onResponse called - Request failed.\n" + ioe.getMessage());
                        }
                    }
                });
            }
        });
    }

    public void parseJson(String jsonData) {
        MemeJsonParser jsonParser = new MemeJsonParser();
        memeArrayList = jsonParser.processJSONData(jsonData);
        // Make sure array was created successfully.
        // Log.d(TAG, "parseJson: " + memeArrayList);
        populateRecyclerViewAndDatabase();
    }

    private void populateRecyclerViewAndDatabase(){
        initializeRecyclerView();
        updateDatabaseWithJsonData();
    }

    private void initializeRecyclerView() {
        recyclerView = findViewById(R.id.mainRecyclerView);
        recyclerViewAdapter = new RecyclerViewAdapter(
                this,
                memeArrayList,
                new RecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Meme meme) {
                        intentMemeDetail(meme);
                    }
                },
                new RecyclerViewAdapter.OnItemLongClickListener() {
                    @Override
                    public void onLongClick(Meme meme) {
                        showAlertDialog(this, meme);
                    }
                });
        recyclerView.setAdapter(recyclerViewAdapter);
        setRecyclerViewLayoutManager();
    }

    private void setRecyclerViewLayoutManager() {
        // recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        // recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, RecyclerView.VERTICAL));
    }

    private void intentMemeDetail(Meme meme){
        Bundle bundle = new Bundle();
        bundle.putSerializable("bundleExtra", meme);

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("intentExtra", bundle);
        startActivity(intent);
    }

    public void showAlertDialog(RecyclerViewAdapter.OnItemLongClickListener rvAdapter, Meme meme) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ImageView image = new ImageView(this);

        builder.setTitle("Delete \"" + meme.getName() + "\"?")
                .setMessage("Are you sure you want to delete this meme?")
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(image)

                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteMeme(meme);
                        Toast.makeText(MainActivity.this,
                                "\"" + meme.getName() + "\" has been deleted.",
                                Toast.LENGTH_SHORT).show();
                        // To do: Move toast to middle of screen
                    }
                })
                //.setNegativeButton("No", null)
                .setNeutralButton("Cancel", null);

        builder.create().show();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        return true;
    }

    private void updateDatabaseWithJsonData() {
        ArrayList<ContentValues> contentValues = dbManager.javaObjectToContentValue(memeArrayList);
        dbManager.insertInTable(SQLCommands.TABLE_NAME, contentValues);
        readFromDB();
    }

    private Cursor readFromDB() {
        cursor = dbManager.queryInTable(
                SQLCommands.TABLE_NAME,
                SQLCommands.TABLE_COLUMNS,
                null,
                null);
        System.out.println("******\n----------- Read from database ------------\n******");
        // Make sure process was successful.
        System.out.println(dbManager.cursorToArrayList(cursor));
        return cursor;
    }

    private void deleteMeme(Meme meme){
        Iterator<Meme> iterator = memeArrayList.iterator();
        boolean memeFound = false;

        while (!memeFound && iterator.hasNext()) {
            Meme iteratorMeme = iterator.next();
            if (iteratorMeme.getId() == meme.getId()) {
                iterator.remove();
                memeFound = true;
            }
        }

        recyclerViewAdapter.notifyDataSetChanged();

        dbManager.deleteRowFromTable(SQLCommands.TABLE_NAME,
                SQLCommands.COLUMN_NAME + "=?"
                , new String[]{meme.getName()});
        readFromDB();
}

    @Override
    public void onClick(View view) {

        Cursor cursor = readFromDB();
        ArrayList<Meme> databaseList = dbManager.cursorToArrayList(cursor);

        Bundle bundle = new Bundle();
        bundle.putSerializable("bundleExtra", databaseList);

        Intent intent = new Intent(this, DatabaseActivity.class);
        intent.putExtra("intentExtra", bundle);
        startActivity(intent);

    }
}