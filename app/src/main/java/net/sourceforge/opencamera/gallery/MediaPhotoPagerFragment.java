package net.sourceforge.opencamera.gallery;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import net.sourceforge.opencamera.R;
import net.sourceforge.opencamera.gallery.constants.ConstantUtils;
import net.sourceforge.opencamera.gallery.presentation.video.VideoListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class MediaPhotoPagerFragment extends Fragment {

  private static MediaPhotoPagerFragment instance;
  @BindView(R.id.viewpager)
  ViewPager viewpager;
  @BindView(R.id.rb_photo)
  RadioButton rbPhoto;
  @BindView(R.id.rb_albums)
  RadioButton rbAlbums;
  @BindView(R.id.radioGroup)
  RadioGroup radioGroup;
  Unbinder unbinder;

  @SuppressLint("ValidFragment")
  public MediaPhotoPagerFragment() {
    // Required empty public constructor
  }

  public static MediaPhotoPagerFragment newInstance() {
    if (instance == null) {
      instance = new MediaPhotoPagerFragment();
    }
    return instance;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_photo_pager, container, false);
    unbinder = ButterKnife.bind(this, v);

    setListeners();
    setupViewPager(viewpager);
    return v;
  }

  private void setListeners() {
    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rb_photo){
          viewpager.setCurrentItem(0);
        } else if (checkedId == R.id.rb_albums){
          viewpager.setCurrentItem(1);
        }
      }
    });

    viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageSelected(int position) {
        if (position == 0){
          if (radioGroup.getCheckedRadioButtonId() != R.id.rb_photo) radioGroup.check(R.id.rb_photo);
        } else if (position == 1){
          if (radioGroup.getCheckedRadioButtonId() != R.id.rb_albums) radioGroup.check(R.id.rb_albums);
        }
      }
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
      }

      @Override
      public void onPageScrollStateChanged(int state) {
      }
    });
  }

  private void setupViewPager(ViewPager viewPager) {
    ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
    adapter.addFragment(MediaPhotoFragment.newInstance(), ConstantUtils.MEDIA_PHOTO_FRAGMENT);
    adapter.addFragment(VideoListFragment.newInstance(), ConstantUtils.VIDEO_LIST_FRAGMENT);
    viewPager.setAdapter(adapter);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }
}
