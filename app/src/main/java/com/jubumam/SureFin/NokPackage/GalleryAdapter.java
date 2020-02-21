package com.jubumam.SureFin.NokPackage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jubumam.SureFin.R;

import java.util.ArrayList;
import java.util.List;

class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryHolder> {
    interface galleryClickListener {
        void galleryClicked(Gallery model);
    }

    private galleryClickListener mListener;

    private List<Gallery> mItems = new ArrayList<>();

    public GalleryAdapter() {
    }

    public GalleryAdapter(galleryClickListener listener) {
        mListener = listener;
    }

    public void setItems(List<Gallery> items) {
        this.mItems = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GalleryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gallery, parent, false);
        final GalleryHolder viewHolder = new GalleryHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    final Gallery item = mItems.get(viewHolder.getAdapterPosition());
                    mListener.galleryClicked(item);
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryHolder holder, int position) {
        Gallery item = mItems.get(position);
        holder.img_gallery.setImageBitmap(item.getBitmap());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class GalleryHolder extends RecyclerView.ViewHolder {
        private ImageView img_gallery;

        public GalleryHolder(@NonNull View itemView) {
            super(itemView);
            img_gallery = itemView.findViewById(R.id.img_gallery);
        }
    }
}