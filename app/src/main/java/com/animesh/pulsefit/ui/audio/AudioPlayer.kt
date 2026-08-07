package com.animesh.pulsefit.ui.audio

import android.content.Context
import android.media.MediaPlayer
import androidx.annotation.RawRes

object AudioPlayer {

    private var player: MediaPlayer? = null

    fun play(
        context: Context,
        @RawRes sound: Int
    ) {

        player?.release()

        player = MediaPlayer.create(
            context,
            sound
        )

        player?.setOnCompletionListener {

            it.release()

            player = null
        }

        player?.start()
    }

    fun release() {

        player?.release()

        player = null
    }
}