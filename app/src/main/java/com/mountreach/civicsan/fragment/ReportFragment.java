package com.mountreach.civicsan.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mountreach.civicsan.R;

public class ReportFragment extends Fragment {

    private TextInputEditText etTitle, etDescription;
    private AutoCompleteTextView etCategory;
    private TextInputLayout tilCategory;
    private MaterialButton btnSubmit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        initViews(view);
        setupCategoryDropdown();
        setupClickListeners();

        return view;
    }

    private void initViews(View view) {
        etTitle = view.findViewById(R.id.etReportTitle);
        etDescription = view.findViewById(R.id.etReportDescription);
        etCategory = view.findViewById(R.id.etReportCategory);
        tilCategory = view.findViewById(R.id.tilReportCategory);
        btnSubmit = view.findViewById(R.id.btnSubmitReport);
    }

    private void setupCategoryDropdown() {
        if (etCategory != null) {
            String[] categories = getResources().getStringArray(R.array.report_categories);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                    android.R.layout.simple_dropdown_item_1line, categories);
            etCategory.setAdapter(adapter);
        }
    }

    private void setupClickListeners() {
        btnSubmit.setOnClickListener(v -> submitReport());
    }

    private void submitReport() {
        // Check if views are initialized
        if (etTitle == null || etDescription == null || etCategory == null) {
            Toast.makeText(getContext(), "Error: Form not initialized", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get text from EditTexts with proper null checking
        String title = etTitle.getText() != null ? etTitle.getText().toString().trim() : "";
        String description = etDescription.getText() != null ? etDescription.getText().toString().trim() : "";
        String category = etCategory.getText() != null ? etCategory.getText().toString().trim() : "";

        // Validate inputs
        if (TextUtils.isEmpty(title)) {
            etTitle.setError("Title is required");
            etTitle.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(description)) {
            etDescription.setError("Description is required");
            etDescription.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(category)) {
            etCategory.setError("Category is required");
            etCategory.requestFocus();
            return;
        }

        // If all validations pass, submit the report
        performSubmit(title, description, category);
    }

    private void performSubmit(String title, String description, String category) {
        // Show loading state
        btnSubmit.setEnabled(false);
        btnSubmit.setText("Submitting...");

        // TODO: Implement your report submission logic here
        // For example, sending to API or database

        // Simulate submission (remove this in production)
        btnSubmit.postDelayed(() -> {
            Toast.makeText(getContext(), "Report submitted successfully!", Toast.LENGTH_SHORT).show();

            // Clear form
            clearForm();

            // Reset button
            btnSubmit.setEnabled(true);
            btnSubmit.setText("Submit Report");
        }, 2000);
    }

    private void clearForm() {
        if (etTitle != null && etTitle.getText() != null) {
            etTitle.getText().clear();
        }
        if (etDescription != null && etDescription.getText() != null) {
            etDescription.getText().clear();
        }
        if (etCategory != null && etCategory.getText() != null) {
            etCategory.getText().clear();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Clean up references to prevent memory leaks
        etTitle = null;
        etDescription = null;
        etCategory = null;
        tilCategory = null;
        btnSubmit = null;
    }
}