package com.runnimal.app.android.view.util;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.net.URI;

public class ImageUtils {

    public static void setImage(URI imageUrl, ImageView photoImageView) {
        if (imageUrl != null) {
            Picasso.get()
                    .load(imageUrl.toString())
                    .fit()
                    .centerCrop()
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .into(photoImageView);
        }
    }

    public static Bitmap resizeImage(Bitmap image, int width, int height) {
        return Bitmap.createScaledBitmap(image, width, height, false);
    }
}
