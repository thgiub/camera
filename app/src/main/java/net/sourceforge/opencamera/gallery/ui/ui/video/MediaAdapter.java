package net.sourceforge.opencamera.gallery.ui.ui.video;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import net.sourceforge.opencamera.R;
import net.sourceforge.opencamera.gallery.HelperUtils;
import net.sourceforge.opencamera.gallery.domain.EnumFileType;
import net.sourceforge.opencamera.gallery.domain.Media;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


class MediaAdapter extends Adapter<MediaAdapter.ViewHolder> {

  private List<Media> data;
  private Context context;
  private Activity mActivity;

  public MediaAdapter(Context context, List<Media> data, Activity activity) {
    this.data = data;
    this.context = context;
    mActivity = activity;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.media_item_tile, viewGroup, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
    final Media media = data.get(i);
    viewHolder.title.setText(media.title);
    viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);

    if (media.type == EnumFileType.IMAGE) {
      Glide.with(context).load(HelperUtils.getUri(context, media.path)).into(viewHolder.img);
    } else if (media.type == EnumFileType.VIDEO) {
      Glide.with(context)
          .load(HelperUtils.getUri(context, media.path))
          .into(viewHolder.img);

    }
    viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
      @Override
      public boolean onLongClick(View v) {
        Intent shareIntent= new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM,HelperUtils.getUri(context, media.path));
        shareIntent.setType("video/*");
        mActivity.startActivity(Intent.createChooser(shareIntent, mActivity.getResources().getText(R.string.send_to)));
        return false;
      }
    });



    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (media.type == EnumFileType.VIDEO) {

          Intent intent = new Intent(Intent.ACTION_VIEW);
          intent.setDataAndType(HelperUtils.getUri(context, media.path), "video/*");
          context.startActivity(intent);
        }
      }
    });
  }

  @Override
  public int getItemCount() {
    return data != null ? data.size() : 0;
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.title)
    TextView title;
    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
