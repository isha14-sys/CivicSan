package com.mountreach.civicsan.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mountreach.civicsan.R;
import com.mountreach.civicsan.model.Notice;
import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {
    private List<Notice> items;
    private final Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Notice notice);
    }

    public NoticeAdapter(Context context, List<Notice> items) {
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
                .inflate(R.layout.item_notice, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notice notice = items.get(position);
        holder.bind(notice, listener);
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public void updateData(List<Notice> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvMessage;
        private final TextView tvPriority;
        private final TextView tvTimestamp;

        ViewHolder(View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tvNoticeMessage);
            tvPriority = itemView.findViewById(R.id.tvNoticePriority);
            tvTimestamp = itemView.findViewById(R.id.tvNoticeTimestamp);
        }

        void bind(final Notice notice, final OnItemClickListener listener) {
            tvMessage.setText(notice.getMessage());

            String priority = notice.getPriority() != null ? notice.getPriority().toUpperCase() : "NORMAL";
            tvPriority.setText(priority);

            // Set priority background color
            if (notice.getPriority() != null) {
                switch (notice.getPriority().toLowerCase()) {
                    case "high":
                        tvPriority.setBackgroundColor(Color.parseColor("#F44336"));
                        break;
                    case "medium":
                        tvPriority.setBackgroundColor(Color.parseColor("#FF9800"));
                        break;
                    default:
                        tvPriority.setBackgroundColor(Color.parseColor("#4CAF50"));
                        break;
                }
                tvPriority.setTextColor(Color.WHITE);
            }

            if (notice.getTimestamp() != null && !notice.getTimestamp().isEmpty()) {
                tvTimestamp.setText(notice.getTimestamp());
                tvTimestamp.setVisibility(View.VISIBLE);
            } else {
                tvTimestamp.setVisibility(View.GONE);
            }

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(notice);
                }
            });
        }
    }
}