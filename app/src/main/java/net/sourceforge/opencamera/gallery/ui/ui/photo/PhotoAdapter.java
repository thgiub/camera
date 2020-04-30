package net.sourceforge.opencamera.gallery.ui.ui.photo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import net.sourceforge.opencamera.R;
import net.sourceforge.opencamera.gallery.HelperUtils;
import net.sourceforge.opencamera.gallery.constants.ConstantUtils;
import net.sourceforge.opencamera.gallery.domain.Photo;
import net.sourceforge.opencamera.gallery.domain.interfaces.photo.MediaPhotoInterface;
import net.sourceforge.opencamera.gallery.presentation.PhotoViewActivity;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PhotoAdapter extends Adapter<PhotoAdapter.ViewHolder> {

    private List<Photo> galleryList;
    private Context context;
    private MediaPhotoInterface.Presenter presenter;
    private int lastLongPressedItemID = -1;

    public PhotoAdapter(Context context, List<Photo> galleryList, MediaPhotoInterface.Presenter presenter) {
        this.galleryList = galleryList;
        this.context = context;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.photo_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.img.setScaleType(ImageView.ScaleType.FIT_CENTER);

        Picasso.get().load(HelperUtils.getUri(context, galleryList.get(i).getImagePath()))
                .resize(240, 240).centerCrop().into(viewHolder.img);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              presenter.onPhotoClicked(i);
            }
        });

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
              presenter.onPhotoLongClicked(i);
              lastLongPressedItemID = i;
              return true;
            }
        });

        if (presenter.isEditMode()) {
            viewHolder.checkBox.setVisibility(View.VISIBLE);
        } else {
            viewHolder.checkBox.setVisibility(View.GONE);
        }

        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                presenter.onPhotoCheckedChanged(i, isChecked);
            }
        });

        if (lastLongPressedItemID == i) {
            viewHolder.checkBox.setChecked(true);
            lastLongPressedItemID = -1;
        }
    }

    @Override
    public int getItemCount() {
        return galleryList != null ? galleryList.size() : 0;
    }

    public void updateData(List<Photo> data) {
        galleryList.clear();
        galleryList.addAll(data);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.checkBox)
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
