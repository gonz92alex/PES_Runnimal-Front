package com.runnimal.app.android.view.util;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.net.URI;

public class ImageUtils {

    public static void setImage(URI imageUrl, ImageView photoImageView) {
        if (imageUrl != null) {
            Picasso.get().load(imageUrl.toString()).fit().centerCrop().into(photoImageView);
        }
    }
}
