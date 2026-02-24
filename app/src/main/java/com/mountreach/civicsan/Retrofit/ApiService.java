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
            @Part("user_name") RequestBody userName,
            @Part("issue_type") RequestBody issueType,
            @Part("description") RequestBody description,
            @Part("latitude") RequestBody latitude,
            @Part("longitude") RequestBody longitude,
            @Part MultipartBody.Part image
    );
}