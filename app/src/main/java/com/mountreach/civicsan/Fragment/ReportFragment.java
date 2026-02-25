package com.mountreach.civicsan.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
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

    TextInputEditText etUserName, etDescription;
    Spinner spIssueType;
    Button btnSubmit, btnUploadImage;
    Uri imageUri;

    private static final int IMAGE_REQUEST = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_report, container, false);

        etUserName = view.findViewById(R.id.etUserName);
        etDescription = view.findViewById(R.id.etDescription);
        spIssueType = view.findViewById(R.id.spIssueType);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnUploadImage = view.findViewById(R.id.btnUploadImage);

        btnUploadImage.setOnClickListener(v -> openGallery());
        btnSubmit.setOnClickListener(v -> submitReport());

        return view;
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.getData();
            Toast.makeText(getContext(), "Image Selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void submitReport() {

        String nameText = etUserName.getText().toString().trim();
        String descText = etDescription.getText().toString().trim();

        if (TextUtils.isEmpty(nameText)) {
            etUserName.setError("Enter Name");
            return;
        }

        if (TextUtils.isEmpty(descText)) {
            etDescription.setError("Enter Description");
            return;
        }

        if (imageUri == null) {
            Toast.makeText(getContext(), "Select Image", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), nameText);
        RequestBody issue = RequestBody.create(MediaType.parse("text/plain"),
                spIssueType.getSelectedItem().toString());
        RequestBody desc = RequestBody.create(MediaType.parse("text/plain"), descText);
        RequestBody toiletId = RequestBody.create(MediaType.parse("text/plain"), "T001");
        RequestBody lat = RequestBody.create(MediaType.parse("text/plain"), "19.123");
        RequestBody lng = RequestBody.create(MediaType.parse("text/plain"), "72.456");

        File file = new File(getRealPathFromURI(imageUri));
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part imagePart =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        Call<ResponseModel> call = apiService.submitReport(
                toiletId, name, issue, desc, lat, lng, imagePart);

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call,
                                   Response<ResponseModel> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getContext(),
                            response.body().getMessage(),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(),
                            "Server Error",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getContext(),
                        "Network Error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = requireActivity().getContentResolver()
                .query(uri, projection, null, null, null);

        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(columnIndex);
            cursor.close();
            return path;
        }
        return uri.getPath();
    }
}