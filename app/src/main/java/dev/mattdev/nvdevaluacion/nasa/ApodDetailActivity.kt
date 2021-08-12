package dev.mattdev.nvdevaluacion.nasa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Explode
import android.util.Log
import android.view.Window
import androidx.core.view.ViewCompat
import com.bumptech.glide.Glide
import dev.mattdev.nvdevaluacion.Model.Entity.Apod
import dev.mattdev.nvdevaluacion.databinding.ActivityApodDetailBinding
import java.text.SimpleDateFormat
import java.util.*

class ApodDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityApodDetailBinding
    companion object {
        const val APOD = "APOD"
        const val IMAGE = "IMAGE"
    }
    private var apod = Apod()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarDetail)
        ViewCompat.setTransitionName(binding.imageDetailApod, IMAGE)
        intent?.let {
            it.getParcelableExtra<Apod>(APOD)?.let { ap->
                apod = ap
            }
        }
        if(apod.url.isNotEmpty()) {
            Glide.with(this)
                .load(apod.url)
                .into(binding.imageDetailApod)
        }
        binding.collapsingDetailApod.title = apod.title

        binding.textDetailApod.text = apod.explanation

        val sp1 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val sp2 = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        if(apod.date.isNotEmpty()) {
            val d = sp1.parse(apod.date)
            val dd = if(d != null) {
                sp2.format(d)
            } else {
                apod.date
            }
            binding.chipDateDetailApod.text = dd
        }
    }
}