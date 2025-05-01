package com.oscarmena.socceropenapi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class VenuesAdapter extends RecyclerView.Adapter<VenuesAdapter.VenueViewHolder> {

    private List<VenuesResponse.Venue> venues = new ArrayList<>();

    @Override
    public VenueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main, parent, false);
        return new VenueViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VenueViewHolder holder, int position) {
        VenuesResponse.Venue venue = venues.get(position);
        holder.nameTextView.setText(venue.getName());
        holder.cityTextView.setText(venue.getCity());
        holder.addressTextView.setText(venue.getAddress());
        holder.capacityTextView.setText(String.valueOf(venue.getCapacity()));
    }

    @Override
    public int getItemCount() {
        return venues.size();
    }

    public void setVenues(List<VenuesResponse.Venue> venues) {
        this.venues = venues;
        notifyDataSetChanged();
    }

    public static class VenueViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, cityTextView, addressTextView, capacityTextView;

        public VenueViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tvName);
            cityTextView = itemView.findViewById(R.id.tvCity);
            addressTextView = itemView.findViewById(R.id.tvAddress);
            capacityTextView = itemView.findViewById(R.id.tvCapacity);
        }
    }
}