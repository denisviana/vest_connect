package thedantas.vestconnect.presentation.features.youtube_player


import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import kotlinx.android.synthetic.main.activity_youtube_player.*
import thedantas.vestconnect.R

class YouTubePlayerActivity : AppCompatActivity(), YouTubePlayerListener {

    companion object {
        private const val TAG = "url"

        fun newIntent(context: Context, videoUrl: String): Intent =
            Intent(context, YouTubePlayerActivity::class.java)
                .apply {
                    putExtra(TAG, videoUrl)
                }
    }

    lateinit var videoId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_youtube_player)

        videoId = intent.getStringExtra(TAG)!!

        lifecycle.addObserver(youTubePlayer)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        youTubePlayer.enterFullScreen()
        youTubePlayer.addFullScreenListener(object : YouTubePlayerFullScreenListener {
            override fun onYouTubePlayerEnterFullScreen() {

            }

            override fun onYouTubePlayerExitFullScreen() {
                finish()
            }

        })

        youTubePlayer.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
            override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadOrCueVideo(lifecycle, videoId, 0f)
            }
        })

    }

    override fun onReady(youTubePlayer: YouTubePlayer) {
        youTubePlayer.loadOrCueVideo(
            lifecycle,
            videoId,
            0f
        )
    }

    override fun onApiChange(youTubePlayer: YouTubePlayer) {

    }

    override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
    }

    override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
    }

    override fun onPlaybackQualityChange(
        youTubePlayer: YouTubePlayer,
        playbackQuality: PlayerConstants.PlaybackQuality
    ) {
    }

    override fun onPlaybackRateChange(
        youTubePlayer: YouTubePlayer,
        playbackRate: PlayerConstants.PlaybackRate
    ) {
    }

    override fun onStateChange(youTubePlayer: YouTubePlayer, state: PlayerConstants.PlayerState) {
    }

    override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {
    }

    override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {
    }

    override fun onVideoLoadedFraction(youTubePlayer: YouTubePlayer, loadedFraction: Float) {
    }

    override fun onDestroy() {
        super.onDestroy()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        youTubePlayer.release()
    }
}