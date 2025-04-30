package com.example.edunomad

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.ui.PlayerView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

class VideoPlayerActivity : AppCompatActivity() {
    private lateinit var playerView: PlayerView
    private var player: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        playerView = findViewById(R.id.playerView)

        val videoUrl = intent.getStringExtra("videoUrl")

        videoUrl?.let {
            val correctedUrl = it.replace("/preview", "/uc?export=view")
            player = ExoPlayer.Builder(this).build().also { exoPlayer ->
                playerView.player = exoPlayer
                val mediaItem = MediaItem.fromUri(Uri.parse(correctedUrl))
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.prepare()
                exoPlayer.play()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        player?.release()
        player = null
    }
}
