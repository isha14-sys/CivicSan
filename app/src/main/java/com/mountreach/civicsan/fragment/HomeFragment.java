package com.mountreach.civicsan.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mountreach.civicsan.R;
import com.mountreach.civicsan.adapter.PublicToiletAdapter;
import com.mountreach.civicsan.adapter.NoticeAdapter;
import com.mountreach.civicsan.adapter.LinkChangeAdapter;
import com.mountreach.civicsan.adapter.DetailUpdateAdapter;
import com.mountreach.civicsan.model.ToiletData;
import com.mountreach.civicsan.viewmodel.HomeViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private com.mountreach.civicsan.viewmodel.HomeViewModel viewModel;

    // UI Components
    private SwipeRefreshLayout swipeRefresh;
    private ProgressBar progressBar;
    private TextView tvStatusValue;
    private TextView tvCostsAdmin;
    private TextView tvLastUpdated;

    // RecyclerViews
    private RecyclerView rvPublicToilets;
    private RecyclerView rvNotices;
    private RecyclerView rvLinkChanges;
    private RecyclerView rvDetailsUpdates;

    // Adapters
    private PublicToiletAdapter publicToiletAdapter;
    public NoticeAdapter noticeAdapter;
    public LinkChangeAdapter linkChangeAdapter;
    public DetailUpdateAdapter detailUpdateAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initViews(view);
        setupRecyclerViews();
        setupViewModel();
        setupSwipeRefresh();
        setupClickListeners();

        return view;
    }

    private void initViews(View view) {
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        progressBar = view.findViewById(R.id.progressBar);
        tvStatusValue = view.findViewById(R.id.tvStatusValue);
        tvCostsAdmin = view.findViewById(R.id.tvCostsAdmin);
        tvLastUpdated = view.findViewById(R.id.tvLastUpdated);

        rvPublicToilets = view.findViewById(R.id.rvPublicToilets);
        rvNotices = view.findViewById(R.id.rvNotices);
        rvLinkChanges = view.findViewById(R.id.rvLinkChanges);
        rvDetailsUpdates = view.findViewById(R.id.rvDetailsUpdates);
    }

    private void setupRecyclerViews() {
        // Public Toilets RecyclerView
        rvPublicToilets.setLayoutManager(new LinearLayoutManager(getContext()));
        publicToiletAdapter = new PublicToiletAdapter(getContext(), null);
        publicToiletAdapter.setOnItemClickListener(toilet -> {
            // Handle public toilet click
            Toast.makeText(getContext(), "Selected: " + toilet.getName(), Toast.LENGTH_SHORT).show();

            // Navigate to detail activity (you'll need to create this)
            // Intent intent = new Intent(getContext(), ToiletDetailActivity.class);
            // intent.putExtra("toilet_name", toilet.getName());
            // intent.putExtra("toilet_status", toilet.getStatus());
            // intent.putExtra("toilet_location", toilet.getLocation());
            // startActivity(intent);
        });
        rvPublicToilets.setAdapter(publicToiletAdapter);

        // Notices RecyclerView
        rvNotices.setLayoutManager(new LinearLayoutManager(getContext()));
        noticeAdapter = new NoticeAdapter(getContext(), null);
        noticeAdapter.setOnItemClickListener(notice -> {
            // Handle notice click
            Toast.makeText(getContext(), "Notice: " + notice.getMessage(), Toast.LENGTH_LONG).show();

            // Navigate to notice detail
            // Intent intent = new Intent(getContext(), NoticeDetailActivity.class);
            // intent.putExtra("notice_message", notice.getMessage());
            // intent.putExtra("notice_priority", notice.getPriority());
            // intent.putExtra("notice_timestamp", notice.getTimestamp());
            // startActivity(intent);
        });
        rvNotices.setAdapter(noticeAdapter);

        // Link Changes RecyclerView
        rvLinkChanges.setLayoutManager(new LinearLayoutManager(getContext()));
        linkChangeAdapter = new LinkChangeAdapter(getContext(), null);
        linkChangeAdapter.setOnItemClickListener(linkChange -> {
            // Handle link change click
            Toast.makeText(getContext(), "Link: " + linkChange.getDescription(), Toast.LENGTH_SHORT).show();

            // The link will open when clicking the "View Link" button
            // This is for the item click
        });
        rvLinkChanges.setAdapter(linkChangeAdapter);

        // Details Updates RecyclerView
        rvDetailsUpdates.setLayoutManager(new LinearLayoutManager(getContext()));
        detailUpdateAdapter = new DetailUpdateAdapter(getContext(), null);
        detailUpdateAdapter.setOnItemClickListener(update -> {
            // Handle update click
            Toast.makeText(getContext(), "Update: " + update.getDescription(), Toast.LENGTH_SHORT).show();

            // Navigate to update detail
            // Intent intent = new Intent(getContext(), UpdateDetailActivity.class);
            // intent.putExtra("update_description", update.getDescription());
            // intent.putExtra("update_time", update.getTime());
            // intent.putExtra("update_details", update.getDetails());
            // startActivity(intent);
        });
        rvDetailsUpdates.setAdapter(detailUpdateAdapter);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // Observe loading state
        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
                swipeRefresh.setRefreshing(false);
            }
        });

        // Observe toilet data
        viewModel.getToiletData().observe(getViewLifecycleOwner(), toiletData -> {
            if (toiletData != null) {
                updateUI(toiletData);
            }
        });

        // Observe errors
        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });

        // Observe last refreshed time
        viewModel.getLastRefreshed().observe(getViewLifecycleOwner(), time -> {
            if (time != null && time > 0) {
                updateLastRefreshedTime(time);
            }
        });
    }

    private void updateUI(ToiletData toiletData) {
        // Update status
        if (toiletData.getStatus() != null) {
            tvStatusValue.setText(toiletData.getStatus());
        }

        // Update costs admin
        if (toiletData.getCostsAdmin() != null) {
            tvCostsAdmin.setText(toiletData.getCostsAdmin());
        }

        // Update last updated
        if (toiletData.getLastUpdated() != null) {
            tvLastUpdated.setText("Last updated: " + toiletData.getLastUpdated());
        }

        // Update public toilets list
        if (toiletData.getPublicToilets() != null && !toiletData.getPublicToilets().isEmpty()) {
            publicToiletAdapter.updateData(toiletData.getPublicToilets());
        }

        // Update notices list
        if (toiletData.getNotices() != null && !toiletData.getNotices().isEmpty()) {
            noticeAdapter.updateData(toiletData.getNotices());
        } else {
            // Show empty state or hide section
            rvNotices.setVisibility(View.GONE);
        }

        // Update link changes list
        if (toiletData.getLinkChanges() != null && !toiletData.getLinkChanges().isEmpty()) {
            linkChangeAdapter.updateData(toiletData.getLinkChanges());
        }

        // Update details updates list
        if (toiletData.getDetailsUpdates() != null && !toiletData.getDetailsUpdates().isEmpty()) {
            detailUpdateAdapter.updateData(toiletData.getDetailsUpdates());
        }
    }

    private void setupSwipeRefresh() {
        swipeRefresh.setOnRefreshListener(() -> {
            viewModel.refreshData();
        });
        swipeRefresh.setColorSchemeResources(
                R.color.primary_light1,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark
        );
    }

    private void setupClickListeners() {
        // Costs Admin click listener
        tvCostsAdmin.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Costs Admin clicked", Toast.LENGTH_SHORT).show();
            // Navigate to costs admin activity
            // startActivity(new Intent(getContext(), CostsAdminActivity.class));
        });
    }

    private void updateLastRefreshedTime(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String formattedTime = sdf.format(new Date(time));
        tvLastUpdated.setText("Last updated: " + formattedTime);
    }
}