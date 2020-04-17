package net.sourceforge.opencamera.gallery.presentation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import net.sourceforge.opencamera.R;
import net.sourceforge.opencamera.gallery.HelperUtils;
import net.sourceforge.opencamera.gallery.TouchImageView;
import net.sourceforge.opencamera.gallery.domain.Photo;

import java.util.List;


class ImageAdapter extends PagerAdapter {

  private Context context;
  private List<Photo> data;
  View.OnClickListener onClickListener = null;

  public ImageAdapter(Context _context, List<Photo> _data, View.OnClickListener onClickListener)
  {
    this.context = _context;
    this.data = _data;
    this.onClickListener = onClickListener;
  }

  @Override
  public int getCount() {
    return data != null ? data.size() : 0;
  }

  @Override
  public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
    return view == ((RelativeLayout) o);
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {
    View itemView = LayoutInflater
        .from(context).inflate(R.layout.image_adapter_item, container, false);

    final TouchImageView imageView = itemView.findViewById(R.id.iv_event_pager);
    imageView.setOnClickListener(onClickListener);
    container.addView(itemView);

    Picasso.get().load(HelperUtils.getUri(context, data.get(position).getImagePath()))
        .resize(2048, 1200) //2048*1200, максимум 3072*1800
        .onlyScaleDown()
        .centerInside()
        .into(imageView);

    return itemView;
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    ((ViewPager) container).removeView((RelativeLayout) object);
  }
}
