package net.sourceforge.opencamera.gallery.presentation;

import android.net.Uri;
import android.os.Bundle;


import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;



import net.sourceforge.opencamera.R;
import net.sourceforge.opencamera.gallery.ExtendedViewPager;
import net.sourceforge.opencamera.gallery.HelperUtils;
import net.sourceforge.opencamera.gallery.constants.ConstantUtils;
import net.sourceforge.opencamera.gallery.domain.Photo;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoViewActivity extends AppCompatActivity {

  @BindView(R.id.view_pager)
  ExtendedViewPager viewPager;
  @BindView(R.id.activity_main)
  RelativeLayout activityMain;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_photo_view);
    ButterKnife.bind(this);

    if (getIntent() != null && getIntent().getData() != null) {
      String filePath = getIntent().getDataString();
      List<Photo> list = HelperUtils.getPhotoListFromDirectory(this, Uri.parse(filePath).getPath());
      ImageAdapter adapter = new ImageAdapter(this, list, onClickListener);
      viewPager.setAdapter(adapter);
    } else if (getIntent() != null && getIntent().getExtras() != null) {
      Bundle b = getIntent().getExtras();
      if (b.getString(ConstantUtils.BUNDLE_PHOTO_PATH) != null) {
        String filePath = b.getString(ConstantUtils.BUNDLE_PHOTO_PATH);
        List<Photo> list = HelperUtils.getPhotoListFromDirectory(this, filePath);
        ImageAdapter adapter = new ImageAdapter(this, list, onClickListener);
        viewPager.setAdapter(adapter);
      } else {
        long openPhotoId = getIntent().getExtras().getLong(ConstantUtils.BUNDLE_PHOTO_ID);
        List<Photo> list = (List<Photo>) getIntent().getExtras().getSerializable(ConstantUtils.BUNDLE_PHOTOS_LIST);
        int selectPosition = getIntent().getExtras().getInt(ConstantUtils.BUNDLE_LIST_SELECT_POSITION, 0);
        ImageAdapter adapter = new ImageAdapter(this, list, onClickListener);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(selectPosition); // устанавливаем ту картинку с которой пришли в окно просмотра картинок
      }
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    HelperUtils.setTranslucentStatus(true, this);
    HelperUtils.hideSystemUI(this);
  }

  @Override
  public void onPause() {
    super.onPause();
    HelperUtils.setTranslucentStatus(false, this);
  }

  View.OnClickListener onClickListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      if (getWindow().getDecorView().getSystemUiVisibility() == (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
          | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
          | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)) {
        HelperUtils.hideSystemUI(PhotoViewActivity.this);
      } else {
        HelperUtils.showSystemUI(PhotoViewActivity.this);
        HelperUtils.setTranslucentStatus(true, PhotoViewActivity.this);
      }
    }
  };
}
