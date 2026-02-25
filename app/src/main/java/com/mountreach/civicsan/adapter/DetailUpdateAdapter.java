package com.mountreach.civicsan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mountreach.civicsan.R;
import com.mountreach.civicsan.model.DetailUpdate;
import java.util.List;

public class DetailUpdateAdapter extends RecyclerView.Adapter<DetailUpdateAdapter.ViewHolder> {
    private List<DetailUpdate> items;
    private final Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(DetailUpdate update);
    }

    public DetailUpdateAdapter(Context context, List<DetailUpdate> items) {
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
                .inflate(R.layout.item_detail_update, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DetailUpdate update = items.get(position);
        holder.bind(update, listener);
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public void updateData(List<DetailUpdate> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvDescription;
        private final TextView tvTime;
        private final TextView tvDetails;

        ViewHolder(View itemView) {
            super(itemView);
            tvDescription = itemView.findViewById(R.id.tvDetailDescription);
            tvTime = itemView.findViewById(R.id.tvDetailTime);
            tvDetails = itemView.findViewById(R.id.tvDetailInfo);
        }

        void bind(DetailUpdate update, final OnItemClickListener listener) {
            tvDescription.setText(update.getDescription());
            tvTime.setText(update.getTime());

            if (update.getDetails() != null && !update.getDetails().isEmpty()) {
                tvDetails.setText(update.getDetails());
                tvDetails.setVisibility(View.VISIBLE);
            } else {
                tvDetails.setVisibility(View.GONE);
            }

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(update);
                }
            });
        }
    }
}