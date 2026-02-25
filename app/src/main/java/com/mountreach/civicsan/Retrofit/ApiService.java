package com.mountreach.civicsan.Retrofit;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {

    @Multipart
    @POST("submit_report.php")
    Call<ResponseModel> submitReport(
            @Part("toilet_id") RequestBody toiletId,
            @Part("name") RequestBody name,
            @Part("issue_type") RequestBody issue,
            @Part("description") RequestBody desc,
            @Part("latitude") RequestBody lat,
            @Part("longitude") RequestBody lng,
            @Part MultipartBody.Part image
    );
}