package tarsila.costalonga.testdevhotmart.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_details.view.*
import tarsila.costalonga.testdevhotmart.R
import tarsila.costalonga.testdevhotmart.model.Images

class DetailsAdapter : RecyclerView.Adapter<DetailsAdapter.DetailsImagesViewHolder>() {

    var imgsArray = Images()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class DetailsImagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: String) {

            val imgUri = item.toUri().buildUpon().scheme("https").build()
            Picasso.get()
                .load(imgUri)
                .into(itemView.img_rcView_details)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsImagesViewHolder {
        val item =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_details, parent, false)
        return DetailsImagesViewHolder(item)
    }

    override fun onBindViewHolder(holder: DetailsImagesViewHolder, position: Int) {
        val item = imgsArray.hits[position].imgURL
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return imgsArray.hits.size
    }
}