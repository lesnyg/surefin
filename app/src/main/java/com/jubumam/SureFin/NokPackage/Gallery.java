package com.jubumam.SureFin.NokPackage;

import android.graphics.Bitmap;

public class Gallery {
    private Bitmap bitmap;

    public Gallery(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
