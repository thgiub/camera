package net.sourceforge.opencamera.gallery;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.sourceforge.opencamera.R;
import net.sourceforge.opencamera.gallery.constants.ConstantUtils;
import net.sourceforge.opencamera.gallery.domain.Photo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MediaPhotoFragment extends Fragment implements MediaScannerBroadcast.MediaScannerBroadcastListener {

  private static MediaPhotoFragment instance;
  @BindView(R.id.rv_image_gallery)
  RecyclerView rvImageGallery;
  Unbinder unbinder;
  private MediaScannerBroadcast broadcast;
  private Context context;


  @SuppressLint("ValidFragment")
  public MediaPhotoFragment() {
    // Required empty public constructor
  }

  public static MediaPhotoFragment newInstance() {
    if (instance == null) {
      instance = new MediaPhotoFragment();
    }
    return instance;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    this.context = context;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_media_photo, container, false);
    unbinder = ButterKnife.bind(this, view);
    initVars();
    initContent();
    return view;
  }

  @Override
  public void onResume() {
    super.onResume();
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED);
    intentFilter.addDataScheme("file");
    intentFilter.addDataScheme("content");

    context.registerReceiver(broadcast, intentFilter);
  }

  @Override
  public void onPause() {
    super.onPause();
    context.unregisterReceiver(broadcast);
  }



  private void initVars() {
    broadcast = new MediaScannerBroadcast(this::mediaScannerBroadcastCallback);
    rvImageGallery.setHasFixedSize(true);
    LayoutManager layoutManager = new GridLayoutManager(context, 7);
    rvImageGallery.setLayoutManager(layoutManager);
  }

  private void initContent() {
    if(getArguments() != null){
      long bucketId = getArguments().getLong(ConstantUtils.BUNDLE_ALBUM_ID);
      List<Photo> list = HelperUtils.getPhotoList(context, bucketId);
      PhotoAdapter adapter = new PhotoAdapter(context, list,getActivity());
      rvImageGallery.setAdapter(adapter);
    }
    else {
      List<Photo> list = HelperUtils.getPhotoList(context, null);
      PhotoAdapter adapter = new PhotoAdapter(context, list,getActivity());
      rvImageGallery.setAdapter(adapter);
    }
  }

  @Override
  public void mediaScannerBroadcastCallback() {
    initContent();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }
}