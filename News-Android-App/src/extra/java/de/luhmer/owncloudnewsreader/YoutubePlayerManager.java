package de.luhmer.owncloudnewsreader;

import android.app.Activity;
import android.app.FragmentTransaction;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import org.greenrobot.eventbus.EventBus;

import de.luhmer.owncloudnewsreader.events.podcast.RegisterYoutubeOutput;

public class YoutubePlayerManager {

    public static void StartYoutubePlayer(final Activity activity, int YOUTUBE_CONTENT_VIEW_ID, final EventBus eventBus, final Runnable onInitSuccess) {
        YouTubePlayerFragment youTubePlayerFragment = YouTubePlayerFragment.newInstance();
        FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
        ft.add(YOUTUBE_CONTENT_VIEW_ID, youTubePlayerFragment).commit();
        youTubePlayerFragment.initialize("AIzaSyA2OHKWvF_hRVtPmLcwnO8yF6-iah2hjbk", new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
                eventBus.post(new RegisterYoutubeOutput(youTubePlayer, wasRestored));
                onInitSuccess.run();
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                youTubeInitializationResult.getErrorDialog(activity, 0).show();
                //Toast.makeText(activity, "Error while playing youtube video! (InitializationFailure)", Toast.LENGTH_LONG).show();
            }
        });
    }
}
