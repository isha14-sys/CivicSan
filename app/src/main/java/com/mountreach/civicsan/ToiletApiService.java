package com.mountreach.civicsan;

import com.mountreach.civicsan.model.ToiletData;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ToiletApiService {
    @GET("api/toilets")
    Call<ToiletData> getToiletData(
            @Query("location") String location,
            @Query("status") String status
    );

    @GET("api/toilets/updates")
    Call<List<ToiletData>> getLatestUpdates();
}