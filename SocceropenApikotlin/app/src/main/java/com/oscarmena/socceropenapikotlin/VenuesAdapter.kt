package com.oscarmena.socceropenapikotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class VenuesAdapter : RecyclerView.Adapter<VenuesAdapter.VenueViewHolder>() {

    private var venues: List<VenuesResponse.Venue> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VenueViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_main, parent, false)
        return VenueViewHolder(view)
    }

    override fun onBindViewHolder(holder: VenueViewHolder, position: Int) {
        val venue = venues[position]
        holder.nameTextView.text = venue.name
        holder.cityTextView.text = venue.cityName
        holder.addressTextView.text = venue.address
        holder.capacityTextView.text = venue.capacity?.toString()
    }

    override fun getItemCount(): Int = venues.size

    fun setVenues(venues: List<VenuesResponse.Venue>) {
        this.venues = venues
        notifyDataSetChanged()
    }

    class VenueViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.tvName)
        val cityTextView: TextView = view.findViewById(R.id.tvCity)
        val addressTextView: TextView = view.findViewById(R.id.tvAddress)
        val capacityTextView: TextView = view.findViewById(R.id.tvCapacity)
    }
}