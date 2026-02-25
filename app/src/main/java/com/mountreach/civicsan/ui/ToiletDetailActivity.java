package com.mountreach.civicsan.ui;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.mountreach.civicsan.R;

public class ToiletDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView tvTitle = findViewById(R.id.tvDetailTitle);
        TextView tvContent = findViewById(R.id.tvDetailContent);

        String name = getIntent().getStringExtra("toilet_name");
        String status = getIntent().getStringExtra("toilet_status");
        String location = getIntent().getStringExtra("toilet_location");

        tvTitle.setText(name);
        tvContent.setText("Status: " + status + "\nLocation: " + location);
    }
}