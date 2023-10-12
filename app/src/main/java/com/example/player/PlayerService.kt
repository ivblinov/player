package com.example.player

import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

class PlayerService : Service() {
    private val songList = mutableListOf(R.raw.ahra_my_lover, R.raw.guf, R.raw.dj)
    private val binder = LocalBinder()
    private lateinit var player: ExoPlayer

    override fun onBind(intent: Intent?): IBinder? {
        player = ExoPlayer.Builder(this).build()
        songList.forEach {
            val songURI = Uri.parse("android.resource://com.example.player/$it")
            player.addMediaItem(MediaItem.fromUri(songURI))
        }
        player.prepare()
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        player.release()
        return super.onUnbind(intent)
    }

    inner class LocalBinder : Binder() {
        fun getService(): PlayerService = this@PlayerService
    }

    fun play() {
        player.play()
    }

    fun pause() {
        player.pause()
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    fun next() {
        player.next()
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    fun prev() {
        player.previous()
    }
}