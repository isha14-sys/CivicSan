package com.mountreach.civicsan.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.mountreach.civicsan.R;
import com.mountreach.civicsan.Retrofit.ApiClient;
import com.mountreach.civicsan.Retrofit.ApiService;
import com.mountreach.civicsan.Retrofit.ResponseModel;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportFragment extends Fragment {

    EditText etUserName, etDescription;
    Spinner spIssueType;
    Button btnSubmit;
    Uri imageUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_report, container, false);

        etUserName = view.findViewById(R.id.etUserName);
        etDescription = view.findViewById(R.id.etDescription);
        spIssueType = view.findViewById(R.id.spIssueType);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(v -> submitReport());

        return view;
    }

    private void submitReport() {

        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        RequestBody name = RequestBody.create(
                MediaType.parse("text/plain"), etUserName.getText().toString());

        RequestBody issue = RequestBody.create(
                MediaType.parse("text/plain"), spIssueType.getSelectedItem().toString());

        RequestBody desc = RequestBody.create(
                MediaType.parse("text/plain"), etDescription.getText().toString());

        RequestBody toiletId = RequestBody.create(
                MediaType.parse("text/plain"), "T001");

        RequestBody lat = RequestBody.create(
                MediaType.parse("text/plain"), "19.123");

        RequestBody lng = RequestBody.create(
                MediaType.parse("text/plain"), "72.456");

        File file = new File(imageUri.getPath());
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("image/*"), file);

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        Call<ResponseModel> call = apiService.submitReport(
                toiletId, name, issue, desc, lat, lng, body);

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call,
                                   Response<ResponseModel> response) {
                Toast.makeText(getContext(), "Report Submitted",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getContext(), "Error",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}