package com.elise.elisefinal.manageJSON;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.elise.elisefinal.R;

public class Util {

    public static void loadImage(ImageView imageView, String url
    ) {
        RequestOptions options = new RequestOptions()
                .error(R.mipmap.load_failed);

        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(options)
                .load(url)
                .into(imageView);
    }
}
