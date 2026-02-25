package com.mountreach.civicsan.adapter;  // lowercase 'a'

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.mountreach.civicsan.R;
import com.mountreach.civicsan.model.PublicToilet;
import java.util.List;

public class PublicToiletAdapter extends RecyclerView.Adapter<PublicToiletAdapter.ViewHolder> {
    private List<PublicToilet> items;
    private final Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(PublicToilet toilet);
    }

    public PublicToiletAdapter(Context context, List<PublicToilet> items) {
        this.context = context;
        this.items = items;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_public_toilet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PublicToilet toilet = items.get(position);
        holder.bind(toilet, listener);
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public void updateData(List<PublicToilet> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final TextView tvStatus;
        private final TextView tvLocation;
        private final CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvToiletName);
            tvStatus = itemView.findViewById(R.id.tvToiletStatus);
            tvLocation = itemView.findViewById(R.id.tvToiletLocation);
            cardView = (CardView) itemView;
        }

        void bind(final PublicToilet toilet, final OnItemClickListener listener) {
            tvName.setText(toilet.getName());
            tvStatus.setText(toilet.getStatus());

            if ("Available".equalsIgnoreCase(toilet.getStatus())) {
                tvStatus.setBackgroundColor(Color.parseColor("#4CAF50"));
            } else {
                tvStatus.setBackgroundColor(Color.parseColor("#F44336"));
            }
            tvStatus.setTextColor(Color.WHITE);

            tvLocation.setText(toilet.getLocation() != null ? toilet.getLocation() : "Location not specified");

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(toilet);
                }
            });
        }
    }
}