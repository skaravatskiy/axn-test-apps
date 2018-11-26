package com.rshtukaraxondevgroup.bluetoothtest.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rshtukaraxondevgroup.bluetoothtest.R;
import com.rshtukaraxondevgroup.bluetoothtest.model.PhotoModel;

import java.util.ArrayList;
import java.util.List;

public class ReceivedPhotoListAdapter extends RecyclerView.Adapter<ReceivedPhotoListAdapter.ViewHolder> {
    private List<PhotoModel> mPhotoModelList = new ArrayList<>();

    public ReceivedPhotoListAdapter() {
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReceivedPhotoListAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_line, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(holder.itemView)
                .load(mPhotoModelList.get(position).getThumbnailUrl())
                .into(holder.imageViewPhoto);
        holder.textViewFullName.setText(mPhotoModelList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mPhotoModelList.size();
    }

    public void setList(List<PhotoModel> list) {
        this.mPhotoModelList = list;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewPhoto;
        TextView textViewFullName;

        public ViewHolder(View itemView) {
            super(itemView);
            imageViewPhoto = itemView.findViewById(R.id.image_photo_profile);
            textViewFullName = itemView.findViewById(R.id.text_title);
        }
    }
}
