package net.sourceforge.opencamera.gallery.ui.ui.video;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.sourceforge.opencamera.R;
import net.sourceforge.opencamera.gallery.HelperUtils;
import net.sourceforge.opencamera.gallery.domain.Media;
import net.sourceforge.opencamera.gallery.presentation.MediaScannerBroadcast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class VideoListFragment extends Fragment implements MediaScannerBroadcast.MediaScannerBroadcastListener {

  private static VideoListFragment instance;
  @BindView(R.id.recyclerView)
  RecyclerView recyclerView;
  Unbinder unbinder;
  private Context context;
  private MediaScannerBroadcast broadcast;

  @SuppressLint("ValidFragment")
  public VideoListFragment() {}

  public static VideoListFragment newInstance() {
    if (instance == null) {
      instance = new VideoListFragment();
    }
    return instance;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    this.context = context;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_video_list, container, false);
    unbinder = ButterKnife.bind(this, view);
    initVars();
    initContent();
    return view;
  }

  private void initVars() {
    broadcast = new MediaScannerBroadcast(this::mediaScannerBroadcastCallback);
    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),  4));
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
  public void onPause(){
    super.onPause();
    context.unregisterReceiver(broadcast);
  }

  private void initContent() {
    List<Media> data = HelperUtils.getVideoList(context, null);
    recyclerView.setAdapter(new MediaAdapter(getActivity(), data, getActivity()));

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
