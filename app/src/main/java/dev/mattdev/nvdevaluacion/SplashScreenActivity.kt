package dev.mattdev.nvdevaluacion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.target.ImageViewTarget
import dev.mattdev.nvdevaluacion.databinding.ActivitySplashScreenBinding
import dev.mattdev.nvdevaluacion.nasa.MainActivity
import java.util.*

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Glide.with(this)
            .asGif()
            .load(R.drawable.nasa_logo)
            .into(object : ImageViewTarget<GifDrawable>(binding.imageSplash) {
                override fun setResource(resource: GifDrawable?) {
                    if(resource!=null) {
                        binding.imageSplash.setImageDrawable(resource)
                    }
                }
            })
        val timer = Timer()
        val timerTask = object: TimerTask() {
            override fun run() {
                runOnUiThread {
                    startActivity(
                        Intent(this@SplashScreenActivity, MainActivity::class.java)
                    )
                    finish()
                }
            }
        }
        timer.schedule(timerTask, 2500)
    }
}