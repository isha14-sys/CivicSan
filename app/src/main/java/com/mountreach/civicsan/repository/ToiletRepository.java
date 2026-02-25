package com.mountreach.civicsan.repository;

import android.os.Handler;
import android.os.Looper;
import androidx.lifecycle.MutableLiveData;
import com.mountreach.civicsan.model.*;
import com.mountreach.civicsan.network.RetrofitClient;
import com.mountreach.civicsan.ToiletApiService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ToiletRepository {
    private static ToiletRepository instance;
    private final ToiletApiService apiService;
    private final ExecutorService executorService;
    private final Handler mainHandler;

    private final MutableLiveData<ToiletData> toiletData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();

    private ToiletRepository() {
        apiService = RetrofitClient.getInstance();
        executorService = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());
    }

    public static synchronized ToiletRepository getInstance() {
        if (instance == null) {
            instance = new ToiletRepository();
        }
        return instance;
    }

    public MutableLiveData<ToiletData> getToiletData() {
        return toiletData;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public void fetchToiletData(String location, String status) {
        isLoading.setValue(true);
        error.setValue(null);

        apiService.getToiletData(location, status).enqueue(new Callback<ToiletData>() {
            @Override
            public void onResponse(Call<ToiletData> call, Response<ToiletData> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    toiletData.setValue(response.body());
                } else {
                    error.setValue("Failed to load data: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ToiletData> call, Throwable t) {
                isLoading.setValue(false);
                error.setValue("Network error: " + t.getMessage());
            }
        });
    }

    // Mock data for testing
    public ToiletData getMockData() {
        List<PublicToilet> publicToilets = new ArrayList<>();
        publicToilets.add(new PublicToilet("Two", "Available", "Main Building"));
        publicToilets.add(new PublicToilet("Test", "Occupied", "East Wing"));
        publicToilets.add(new PublicToilet("Four", "Available", "West Wing"));

        List<Notice> notices = new ArrayList<>();
        notices.add(new Notice("Tests changes", "high", "10:30 AM"));

        List<LinkChange> linkChanges = new ArrayList<>();
        linkChanges.add(new LinkChange("Link Change", "11.10.00", "https://example.com"));

        List<DetailUpdate> detailsUpdates = new ArrayList<>();
        detailsUpdates.add(new DetailUpdate("Details and Updates", "16:29:34", "System update completed"));

        return new ToiletData(
                "1",
                "Toilets",
                "Public Toilets",
                publicToilets,
                notices,
                linkChanges,
                detailsUpdates,
                "Costs Admin",
                "2024-02-25T16:29:34"
        );
    }
}