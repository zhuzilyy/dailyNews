package com.qianyi.dailynews.utils.loader;

import android.content.Context;
import android.widget.ImageView;

import com.youth.banner.loader.ImageLoaderInterface;


public abstract class ImageLoader implements ImageLoaderInterface<ImageView> {

    @Override
    public ImageView createImageView(Context context) {
        ImageView imageView = new ImageView(context);
        return imageView;
    }

}
