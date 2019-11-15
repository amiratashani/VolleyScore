package com.example.volleyscore.volley;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.collection.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class MyVolley extends Application {

    private static MyVolley mInstance;
    private ImageLoader mImageLoader;
    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mLruCache = new LruCache<>(20);

            @Override
            public Bitmap getBitmap(String url) {
                return mLruCache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                mLruCache.put(url, bitmap);
            }
        });


    }


    public static synchronized MyVolley getInstance() {
        return mInstance;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
