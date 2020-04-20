package net.sourceforge.opencamera.gallery;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import net.sourceforge.opencamera.R;
import net.sourceforge.opencamera.gallery.presentation.video.VideoListFragment;

public class GalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment= new MediaPhotoPagerFragment();
        transaction.replace(R.id.gobedia2, fragment);
    }


}
