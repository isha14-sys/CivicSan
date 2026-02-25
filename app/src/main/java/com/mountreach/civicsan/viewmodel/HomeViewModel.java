package com.mountreach.civicsan.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.mountreach.civicsan.model.ToiletData;
import com.mountreach.civicsan.repository.ToiletRepository;

public class HomeViewModel extends AndroidViewModel {
    private final ToiletRepository repository;
    private final MutableLiveData<ToiletData> toiletData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Long> lastRefreshed = new MutableLiveData<>();

    public HomeViewModel(@NonNull Application application) {
        super(application);
        repository = ToiletRepository.getInstance();
        loadMockData(); // Load mock data initially
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

    public MutableLiveData<Long> getLastRefreshed() {
        return lastRefreshed;
    }

    public void loadData() {
        isLoading.setValue(true);
        repository.fetchToiletData(null, null);

        // Observe repository data (you need to implement this in repository)
        // For now, we'll use mock data
    }

    private void loadMockData() {
        isLoading.setValue(true);

        // Simulate network delay
        new android.os.Handler().postDelayed(() -> {
            toiletData.setValue(repository.getMockData());
            isLoading.setValue(false);
            lastRefreshed.setValue(System.currentTimeMillis());
        }, 1000);
    }

    public void refreshData() {
        isLoading.setValue(true);

        // Simulate network delay
        new android.os.Handler().postDelayed(() -> {
            toiletData.setValue(repository.getMockData());
            isLoading.setValue(false);
            lastRefreshed.setValue(System.currentTimeMillis());
        }, 1000);
    }
}