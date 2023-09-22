// DonationAdapter.java
package com.example.foodtruck;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.DonationViewHolder> {

    private List<Donation> donations = new ArrayList<>();

    public void setDonations(List<Donation> donations) {
        this.donations = donations;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DonationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donation, parent, false);
        return new DonationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonationViewHolder holder, int position) {
        Donation donation = donations.get(position);
        holder.bind(donation);
    }

    @Override
    public int getItemCount() {
        return donations.size();
    }

    public static class DonationViewHolder extends RecyclerView.ViewHolder {
        private TextView itemNameTextView;
        private TextView itemTypeTextView;
        private TextView itemQuantityTextView;
        private TextView itemAddressTextView;

        public DonationViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.itemNameTextView);
            itemTypeTextView = itemView.findViewById(R.id.itemTypeTextView);
            itemQuantityTextView = itemView.findViewById(R.id.itemQuantityTextView);
            itemAddressTextView = itemView.findViewById(R.id.itemAddressTextView);
        }

        public void bind(Donation donation) {
            itemNameTextView.setText(donation.getItemName());
            itemTypeTextView.setText(donation.getItemType());
            itemQuantityTextView.setText(String.valueOf(donation.getItemQuantity()));
            itemAddressTextView.setText(donation.getItemAddress());
        }
    }
}
