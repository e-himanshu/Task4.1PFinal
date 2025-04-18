package com.example.managertaskapp;

import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class ViewTaskActivity extends AppCompatActivity {

    private TextView tvTitle, tvDescription, tvDueDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        tvTitle = findViewById(R.id.tv_title);
        tvDescription = findViewById(R.id.tv_description);
        tvDueDate = findViewById(R.id.tv_due_date);

        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra("task_title");
            String description = intent.getStringExtra("task_description");
            long dueDateMillis = intent.getLongExtra("task_due_date", 0);

            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
            String dueDate = sdf.format(new Date(dueDateMillis));

            tvTitle.setText("Title: " + title);
            tvDescription.setText("Description: " + description);
            tvDueDate.setText("Deadline: " + dueDate);
        }
    }
}
