package com.example.managertaskapp;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class AddEditTaskActivity extends AppCompatActivity {
    private EditText etTitle, etDescription;
    private Button btnDate, btnSave;
    private long dueDate;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);

        etTitle = findViewById(R.id.et_title);
        etDescription = findViewById(R.id.et_description);
        btnDate = findViewById(R.id.btn_date);
        btnSave = findViewById(R.id.btn_save);

        calendar = Calendar.getInstance();
        dueDate = calendar.getTimeInMillis();
        updateDateButton();

        btnDate.setOnClickListener(v -> showDatePicker());
        btnSave.setOnClickListener(v -> saveTask());

        if (getIntent().hasExtra("task_id")) {
            setTitle("Edit Task");
            etTitle.setText(getIntent().getStringExtra("task_title"));
            etDescription.setText(getIntent().getStringExtra("task_description"));
            dueDate = getIntent().getLongExtra("task_due_date", 0);
            calendar.setTimeInMillis(dueDate);
            updateDateButton();
        } else {
            setTitle("Add Task");
        }
    }

    private void showDatePicker() {
        new DatePickerDialog(this, (view, year, month, day) -> {
            calendar.set(year, month, day);
            dueDate = calendar.getTimeInMillis();
            updateDateButton();
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateDateButton() {
        btnDate.setText("Due: " + android.text.format.DateFormat.getDateFormat(this).format(dueDate));
    }

    private void saveTask() {
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        if (title.isEmpty()) {
            Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra("task_title", title);
        data.putExtra("task_description", description);
        data.putExtra("task_due_date", dueDate);

        if (getIntent().hasExtra("task_id")) {
            data.putExtra("task_id", getIntent().getIntExtra("task_id", -1));
        }

        setResult(RESULT_OK, data);
        finish();
    }
}