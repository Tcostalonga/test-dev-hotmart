package tarsila.costalonga.testdevhotmart.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*
import tarsila.costalonga.testdevhotmart.R
import tarsila.costalonga.testdevhotmart.model.Locations

class LocationsAdapter : RecyclerView.Adapter<LocationsAdapter.LocationsViewHolder>() {

    var data = listOf<Locations>()

    class LocationsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Locations) {

            itemView.name.text = item.name
            itemView.type.text = item.type
            itemView.review.text = item.review.toString()

            when (item.review) {
                in 0.0..1.9 -> {
                    itemView.star1.setImageResource(R.drawable.ic_s_on)
                }
                in 2.0..2.9 -> {
                    itemView.star1.setImageResource(R.drawable.ic_s_on)
                    itemView.star2.setImageResource(R.drawable.ic_s_on)
                }
                in 3.0..3.9 -> {
                    itemView.star1.setImageResource(R.drawable.ic_s_on)
                    itemView.star2.setImageResource(R.drawable.ic_s_on)
                    itemView.star3.setImageResource(R.drawable.ic_s_on)
                }
                in 4.0..4.9 -> {
                    itemView.star1.setImageResource(R.drawable.ic_s_on)
                    itemView.star2.setImageResource(R.drawable.ic_s_on)
                    itemView.star3.setImageResource(R.drawable.ic_s_on)
                    itemView.star4.setImageResource(R.drawable.ic_s_on)
                }
                else -> {
                    itemView.star1.setImageResource(R.drawable.ic_s_on)
                    itemView.star2.setImageResource(R.drawable.ic_s_on)
                    itemView.star3.setImageResource(R.drawable.ic_s_on)
                    itemView.star4.setImageResource(R.drawable.ic_s_on)
                    itemView.star5.setImageResource(R.drawable.ic_s_on)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsViewHolder {
        val item =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return LocationsViewHolder(item)
    }


    override fun getItemCount(): Int {
        return data.size
    }


    override fun onBindViewHolder(holder: LocationsViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }
}