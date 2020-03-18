package com.jubumam.surefin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

public class SYGalleryDetailActivity extends BaseActivity {
    private ImageView img_gallerydetail;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_detail);

        activateToolbar();

        img_gallerydetail = findViewById(R.id.img_gallerydetail);
        Intent intent = getIntent();
        bitmap = intent.getParcelableExtra("pic");

        img_gallerydetail.setImageBitmap(bitmap);
    }
}
