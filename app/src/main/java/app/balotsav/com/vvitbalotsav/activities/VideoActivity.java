package app.balotsav.com.vvitbalotsav.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import java.util.Arrays;
import java.util.List;

import app.balotsav.com.vvitbalotsav.R;
import app.balotsav.com.vvitbalotsav.model.OnClickAdapter;
import app.balotsav.com.vvitbalotsav.utils.GalleryAdapter;

public class VideoActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener {
    public static final String API_KEY = "AIzaSyD2R7nt_8I12-gyUo_XCKydYRgyCNuuNYg";
    public static String VIDEO_ID = "fhWaJi1Hsfo";
    private YouTubePlayerFragment youTubePlayerView;
    private static final int RECOVERY_REQUEST = 1;
    RecyclerView thumbnailView;
    GalleryAdapter galleryAdapter;
    YouTubePlayer myPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        //youTubePlayerView =(YouTubePlayerSupportFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
        //youTubePlayerView = YouTubePlayerSupportFragment.newInstance();
        youTubePlayerView = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);



        thumbnailView = findViewById(R.id.id_video_thumbnail_list);
        galleryAdapter = new GalleryAdapter(this);
        List<String> galleryList = Arrays.asList(getResources().getStringArray(R.array.video_thumbnails));
        final List<String> videoIdList = Arrays.asList(getResources().getStringArray(R.array.video_links));
        thumbnailView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        galleryAdapter.setGalleryList(galleryList);
        thumbnailView.setAdapter(galleryAdapter);
        galleryAdapter.setClickAdapter(new OnClickAdapter() {
            @Override
            public void onClickAdapter(ImageView v, String url, int i) {
                myPlayer.cueVideo(videoIdList.get(i));
            }
        });
        VIDEO_ID = videoIdList.get(0);
        youTubePlayerView.initialize(API_KEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

        if (null == youTubePlayer) return;

        // Start buffering
        if (!b) {
            myPlayer = youTubePlayer;
            myPlayer.cueVideo(VIDEO_ID);
        }
        myPlayer.setPlaybackEventListener(new YouTubePlayer.PlaybackEventListener() {
            @Override
            public void onPlaying() {

            }

            @Override
            public void onPaused() {

            }

            @Override
            public void onStopped() {

            }

            @Override
            public void onBuffering(boolean b) {

            }

            @Override
            public void onSeekTo(int i) {

            }
        });

    }


    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Failed to initialize.", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(API_KEY, this);
        }
    }

    protected YouTubePlayerFragment getYouTubePlayerProvider() {
        return youTubePlayerView;
    }
}
