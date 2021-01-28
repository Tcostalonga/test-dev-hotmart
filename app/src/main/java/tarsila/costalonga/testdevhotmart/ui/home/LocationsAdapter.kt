package tarsila.costalonga.testdevhotmart.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_home.view.*
import tarsila.costalonga.testdevhotmart.R
import tarsila.costalonga.testdevhotmart.model.Locations
import tarsila.costalonga.testdevhotmart.utils.getRandomColor

class LocationsAdapter() : RecyclerView.Adapter<LocationsAdapter.LocationsViewHolder>() {

    var data = listOf<Locations>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    lateinit var clicksAcao: ClicksAcao

    inner class LocationsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Locations) {

            //ClickListener
            itemView.card_home.setOnClickListener {
                clicksAcao.onClick(item.id, item.img)
            }

            itemView.name_home.text = item.name
            itemView.type_home.text = item.type
            itemView.review_home.text = item.review.toString()

            item.img?.let {
                val imgUri = (item.img ?: "").toUri().buildUpon().scheme("https").build()
                Picasso.get()
                    .load(imgUri)
                    .error(getRandomColor())
                    .into(itemView.img_home)
            }

            when (item.review) {
                0.0f -> {
                    itemView.star1_home.setImageResource(R.drawable.ic_s_off)
                    itemView.star2_home.setImageResource(R.drawable.ic_s_off)
                    itemView.star3_home.setImageResource(R.drawable.ic_s_off)
                    itemView.star4_home.setImageResource(R.drawable.ic_s_off)
                    itemView.star5_home.setImageResource(R.drawable.ic_s_off)
                }
                in 0.1f..1.9f -> {
                    itemView.star1_home.setImageResource(R.drawable.ic_s_on)
                    itemView.star2_home.setImageResource(R.drawable.ic_s_off)
                    itemView.star3_home.setImageResource(R.drawable.ic_s_off)
                    itemView.star4_home.setImageResource(R.drawable.ic_s_off)
                    itemView.star5_home.setImageResource(R.drawable.ic_s_off)
                }
                in 2.0f..2.9f -> {
                    itemView.star1_home.setImageResource(R.drawable.ic_s_on)
                    itemView.star2_home.setImageResource(R.drawable.ic_s_on)
                    itemView.star3_home.setImageResource(R.drawable.ic_s_off)
                    itemView.star4_home.setImageResource(R.drawable.ic_s_off)
                    itemView.star5_home.setImageResource(R.drawable.ic_s_off)
                }
                in 3.0f..3.9f -> {
                    itemView.star1_home.setImageResource(R.drawable.ic_s_on)
                    itemView.star2_home.setImageResource(R.drawable.ic_s_on)
                    itemView.star3_home.setImageResource(R.drawable.ic_s_on)
                    itemView.star4_home.setImageResource(R.drawable.ic_s_off)
                    itemView.star5_home.setImageResource(R.drawable.ic_s_off)
                }
                in 4.0f..4.9f -> {
                    itemView.star1_home.setImageResource(R.drawable.ic_s_on)
                    itemView.star2_home.setImageResource(R.drawable.ic_s_on)
                    itemView.star3_home.setImageResource(R.drawable.ic_s_on)
                    itemView.star4_home.setImageResource(R.drawable.ic_s_on)
                    itemView.star5_home.setImageResource(R.drawable.ic_s_off)
                }
                5.0f -> {
                    itemView.star1_home.setImageResource(R.drawable.ic_s_on)
                    itemView.star2_home.setImageResource(R.drawable.ic_s_on)
                    itemView.star3_home.setImageResource(R.drawable.ic_s_on)
                    itemView.star4_home.setImageResource(R.drawable.ic_s_on)
                    itemView.star5_home.setImageResource(R.drawable.ic_s_on)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LocationsAdapter.LocationsViewHolder {
        val item =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_home, parent, false)
        return LocationsViewHolder(item)
    }


    override fun getItemCount(): Int {
        return data.size
    }


    override fun onBindViewHolder(holder: LocationsAdapter.LocationsViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

}

interface ClicksAcao {
    fun onClick(id: Int, oneImg : String?)
}
