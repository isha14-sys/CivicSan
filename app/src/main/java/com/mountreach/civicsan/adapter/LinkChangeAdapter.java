package com.mountreach.civicsan.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mountreach.civicsan.R;
import com.mountreach.civicsan.model.LinkChange;
import java.util.List;

public class LinkChangeAdapter extends RecyclerView.Adapter<LinkChangeAdapter.ViewHolder> {
    private List<LinkChange> items;
    private static Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(LinkChange linkChange);
    }

    public LinkChangeAdapter(Context context, List<LinkChange> items) {
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
                .inflate(R.layout.item_link_change, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (items != null && position < items.size()) {
            LinkChange linkChange = items.get(position);
            holder.bind(linkChange, listener);
        }
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public void updateData(List<LinkChange> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvDescription;
        private final TextView tvTime;
        private final TextView tvLink;

        ViewHolder(View itemView) {
            super(itemView);
            // Initialize all views with null checks
            tvDescription = itemView.findViewById(R.id.tvLinkDescription);
            tvTime = itemView.findViewById(R.id.tvLinkTime);
            tvLink = itemView.findViewById(R.id.tvLinkUrl);

            // Log if any view is null (for debugging)
            if (tvDescription == null) {
                android.util.Log.e("LinkChangeAdapter", "tvLinkDescription not found in layout");
            }
            if (tvTime == null) {
                android.util.Log.e("LinkChangeAdapter", "tvLinkTime not found in layout");
            }
            if (tvLink == null) {
                android.util.Log.e("LinkChangeAdapter", "tvLinkUrl not found in layout");
            }
        }

        void bind(final LinkChange linkChange, final OnItemClickListener listener) {
            // Add null checks for all views
            if (tvDescription != null) {
                tvDescription.setText(linkChange.getDescription() != null ?
                        linkChange.getDescription() : "No description");
            }

            if (tvTime != null) {
                tvTime.setText(linkChange.getTime() != null ?
                        linkChange.getTime() : "");
            }

            if (tvLink != null) {
                if (linkChange.getLink() != null && !linkChange.getLink().isEmpty()) {
                    tvLink.setVisibility(View.VISIBLE);
                    tvLink.setOnClickListener(v -> {
                        try {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkChange.getLink()));
                            context.startActivity(intent);
                        } catch (Exception e) {
                            Toast.makeText(context, "Cannot open link", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    tvLink.setVisibility(View.GONE);
                }
            }

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(linkChange);
                }
            });
        }
    }
}