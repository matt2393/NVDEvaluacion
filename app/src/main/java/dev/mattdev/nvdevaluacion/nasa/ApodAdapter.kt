package dev.mattdev.nvdevaluacion.nasa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import dev.mattdev.nvdevaluacion.Model.Entity.Apod
import dev.mattdev.nvdevaluacion.R
import dev.mattdev.nvdevaluacion.databinding.ItemApodBinding

class ApodAdapter(var apods: ArrayList<Apod> = arrayListOf(),
                  val loadImage: (url: String, imageV: ImageView) -> Unit,
                  val openDetail:(apod: Apod, imageV: ImageView) -> Unit): RecyclerView.Adapter<ApodAdapter.ApodViewHolder>() {
    inner class ApodViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val binding = ItemApodBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ApodViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_apod, parent, false)
        )

    override fun onBindViewHolder(holder: ApodViewHolder, position: Int) {
        val apod = apods[position]
        holder.binding.textTitleApod.text = apod.title
        loadImage(apod.url, holder.binding.imageApod)
        holder.binding.root.setOnClickListener {
            openDetail(apod, holder.binding.imageApod)
        }
    }

    override fun getItemCount(): Int = apods.size
}