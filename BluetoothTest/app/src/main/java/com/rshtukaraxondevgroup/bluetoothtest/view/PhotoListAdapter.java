package com.rshtukaraxondevgroup.bluetoothtest.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rshtukaraxondevgroup.bluetoothtest.R;
import com.rshtukaraxondevgroup.bluetoothtest.model.PhotoModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.ViewHolder> {
    private List<PhotoModel> mPhotoModelList = new ArrayList<>();
    private boolean mIsVisibility = false;
    private boolean mIsLinearLayoutManager = true;
    private Set<Integer> mCheckSet = new HashSet<>();

    public PhotoListAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                return new PhotoListAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_grid, parent, false));
            default:
                return new PhotoListAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_line, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(holder.itemView)
                .load(mPhotoModelList.get(position).getThumbnailUrl())
                .into(holder.imageViewPhoto);

        switch (holder.getItemViewType()) {
            case 0:
                holder.textViewFullName.setText(mPhotoModelList.get(position).getTitle());
                break;
        }

        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(mCheckSet.contains(position));
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mCheckSet.add(position);
            } else {
                mCheckSet.remove(position);
            }
        });
        holder.itemView.setOnLongClickListener(v -> {
            mIsVisibility = true;
            mCheckSet.add(position);
            holder.checkBox.setChecked(true);
            notifyDataSetChanged();
            return true;
        });
        if (mIsVisibility) {
            holder.checkBox.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mPhotoModelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mIsLinearLayoutManager) {
            return 0;
        } else {
            return 1;
        }
    }

    public void setList(List<PhotoModel> list) {
        this.mPhotoModelList = list;
        notifyDataSetChanged();
    }

    public List<PhotoModel> getCheckBoxList() {
        List<PhotoModel> list = new ArrayList<>();
        for (Integer integer : mCheckSet) {
            list.add(mPhotoModelList.get(integer));
        }
        return list;
    }

    public void setLinearLayoutManager(boolean layoutManager) {
        mIsLinearLayoutManager = layoutManager;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewPhoto;
        TextView textViewFullName;
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            imageViewPhoto = itemView.findViewById(R.id.image_photo_profile);
            textViewFullName = itemView.findViewById(R.id.text_title);
        }
    }
}
