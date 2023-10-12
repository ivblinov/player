package com.example.player

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.example.player.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var player: PlayerService
    private var mBound: Boolean = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as PlayerService.LocalBinder
            player = binder.getService()
            mBound = true
        }
        override fun onServiceDisconnected(name: ComponentName?) {
            mBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            play.setOnClickListener { play() }
            pause.setOnClickListener { pause() }
            next.setOnClickListener { next() }
            previous.setOnClickListener { prev() }
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(this, PlayerService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)
        mBound = false
    }

    private fun play() {
        if (mBound)
            player.play()
    }

    private fun pause() {
        if (mBound)
            player.pause()
    }

    private fun next() {
        if (mBound)
            player.next()
    }

    private fun prev() {
        if (mBound)
            player.prev()
    }
}