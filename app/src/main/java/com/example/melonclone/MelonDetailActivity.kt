package com.example.melonclone

import android.media.Image
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.net.URI

class MelonDetailActivity : AppCompatActivity() {

    lateinit var playPauseButton: ImageView
    lateinit var mediaPlayer: MediaPlayer
    lateinit var melonItemList: ArrayList<MelonItem>

    var is_playing: Boolean = false
        set(value) {
            if (value == true) {
                playPauseButton.setImageDrawable(
                    this.resources.getDrawable(
                        R.drawable.pause,
                        this.theme
                    )
                )
            } else {
                playPauseButton.setImageDrawable(
                    this.resources.getDrawable(
                        R.drawable.play,
                        this.theme
                    )
                )
            }
            field = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_melon_detail)

        // Serializable 형태로 온 분해된 재료들을 어떤 데이터 형태로 사용할 것인지 명시
        melonItemList = intent.getSerializableExtra("melon_item_list") as ArrayList<MelonItem>
        var currentPosition = intent.getIntExtra("position", 0)

        mediaPlayerCreate(currentPosition)
        changeThumbnail(currentPosition)

        playPauseButton = findViewById(R.id.play)
        playPauseButton.setOnClickListener {
            if (is_playing == true) {
                is_playing = false
                mediaPlayer.pause()
            } else {
                is_playing = true
                // mediaPlayer.start()
                mediaPlayer.start()
            }
        }

        findViewById<ImageView>(R.id.back).setOnClickListener {
            mediaPlayer.stop()
            is_playing = false

            if(currentPosition <= 0) currentPosition = melonItemList.size - 1
            else currentPosition--

            changeThumbnail(currentPosition)
            mediaPlayerCreate(currentPosition)
        }

        findViewById<ImageView>(R.id.next).setOnClickListener {
            mediaPlayer.stop()
            is_playing = false

            if(currentPosition >= melonItemList.size - 1) currentPosition = 0
            else currentPosition++

            changeThumbnail(currentPosition)
            mediaPlayerCreate(currentPosition)
        }
    }

    fun mediaPlayerCreate(position: Int) {
        mediaPlayer = MediaPlayer.create(
            this,
            Uri.parse(melonItemList.get(position).song)
        )
    }

    fun changeThumbnail(position: Int) {
        findViewById<ImageView>(R.id.thumbnail).apply {
            val glide = Glide.with(this@MelonDetailActivity)
            glide.load(melonItemList.get(position).thumbnail).into(this)
        }
    }
}