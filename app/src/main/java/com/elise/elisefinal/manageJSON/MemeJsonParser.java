package com.elise.elisefinal.manageJSON;

import com.elise.elisefinal.model.Meme;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MemeJsonParser {

    public static ArrayList<Meme> processJSONData(String jsonString) {

        ArrayList<Meme> memeList = new ArrayList<>();

        try {

            JSONObject rootJSONObject = new JSONObject(jsonString);
            JSONObject nestedJSONObject = rootJSONObject.getJSONObject("data");
            JSONArray memes = nestedJSONObject.getJSONArray("memes");

            for (int i = 0; i < memes.length(); i++) {
                JSONObject currentJsonObject = memes.getJSONObject(i);

                int id = currentJsonObject.getInt("id");
                String name = currentJsonObject.getString("name");
                String url = currentJsonObject.getString("url");
                int width = currentJsonObject.getInt("width");
                int height = currentJsonObject.getInt("height");
                int boxCount = currentJsonObject.getInt("box_count");
                int captions = currentJsonObject.getInt("captions");


                Meme currentMeme = new Meme(id, name, url, width, height, boxCount, captions);
                memeList.add(currentMeme);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return memeList;
    }
}
