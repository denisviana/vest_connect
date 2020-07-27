package thedantas.vestconnect.presentation.features.product_content.adapter

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import thedantas.vestconnect.R

class ProductContentAdapter(
    layoutResId : Int,
    data : MutableList<String?>,
    private val listener : OnItemClick
) : BaseQuickAdapter<String, BaseViewHolder>(layoutResId, data), YouTubePlayerListener {

    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.getView<YouTubePlayerView>(R.id.youtubePlayerView)
            ?.apply {

                getYouTubePlayerWhenReady(object : YouTubePlayerCallback{
                    override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.cueVideo(item!!, 0f)
                    }
                })

                inflateCustomPlayerUi(R.layout.layout_custom_player)

                addYouTubePlayerListener(this@ProductContentAdapter)
            }

        helper?.getView<View>(R.id.youtTubePlayerContainer)?.apply {
            setOnClickListener { listener.onVideoClicked(item!!) }
        }
    }

    interface OnItemClick {
        fun onVideoClicked(url : String)
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

    override fun onReady(youTubePlayer: YouTubePlayer) {
    }

    override fun onStateChange(youTubePlayer: YouTubePlayer, state: PlayerConstants.PlayerState) {

    }

    override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {
    }

    override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {
    }

    override fun onVideoLoadedFraction(youTubePlayer: YouTubePlayer, loadedFraction: Float) {
    }

}