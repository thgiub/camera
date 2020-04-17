package net.sourceforge.opencamera.gallery;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import net.sourceforge.opencamera.R;
import net.sourceforge.opencamera.gallery.constants.ConstantUtils;
import net.sourceforge.opencamera.gallery.domain.Photo;
import net.sourceforge.opencamera.gallery.presentation.PhotoViewActivity;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PhotoAdapter extends Adapter<PhotoAdapter.ViewHolder> {

  private List<Photo> galleryList;
  private Context context;

  public PhotoAdapter(Context context, List<Photo> galleryList) {
    this.galleryList = galleryList;
    this.context = context;
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
        Intent intent = new Intent(context, PhotoViewActivity.class);
        intent.putExtra(ConstantUtils.BUNDLE_PHOTO_ID, galleryList.get(i).getPhotoId());
        intent.putExtra(ConstantUtils.BUNDLE_PHOTOS_LIST, (Serializable) galleryList);
        intent.putExtra(ConstantUtils.BUNDLE_LIST_SELECT_POSITION, i);
        context.startActivity(intent);
      }
    });
  }

  @Override
  public int getItemCount() {
    return galleryList != null ? galleryList.size() : 0;
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.img)
    ImageView img;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
