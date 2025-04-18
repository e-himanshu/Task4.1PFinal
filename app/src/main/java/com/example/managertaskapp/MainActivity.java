package com.example.managertaskapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Task> tasks = new ArrayList<>();
    private TaskAdapter adapter;
    private static final int ADD_TASK_REQUEST = 1;
    private static final int EDIT_TASK_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskAdapter(tasks);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab_add_task);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddEditTaskActivity.class);
            startActivityForResult(intent, ADD_TASK_REQUEST);
        });

        adapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Task task) {
                // Optional: handle item click
            }

            @Override
            public void onDeleteClick(Task task) {
                tasks.remove(task);
                updateTaskList();
            }

            @Override
            public void onEditClick(Task task) {
                Intent intent = new Intent(MainActivity.this, AddEditTaskActivity.class);
                intent.putExtra("task_id", task.getId());
                intent.putExtra("task_title", task.getTitle());
                intent.putExtra("task_description", task.getDescription());
                intent.putExtra("task_due_date", task.getDueDate());
                startActivityForResult(intent, EDIT_TASK_REQUEST);
            }

            @Override
            public void onViewClick(Task task) {
                Intent intent = new Intent(MainActivity.this, ViewTaskActivity.class);
                intent.putExtra("task_title", task.getTitle());
                intent.putExtra("task_description", task.getDescription());
                intent.putExtra("task_due_date", task.getDueDate());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            String title = data.getStringExtra("task_title");
            String description = data.getStringExtra("task_description");
            long dueDate = data.getLongExtra("task_due_date", 0);

            if (requestCode == ADD_TASK_REQUEST) {
                Task task = new Task(title, description, dueDate);
                task.setId(tasks.size() + 1);
                tasks.add(task);
            } else if (requestCode == EDIT_TASK_REQUEST) {
                int taskId = data.getIntExtra("task_id", -1);
                for (Task task : tasks) {
                    if (task.getId() == taskId) {
                        task.setTitle(title);
                        task.setDescription(description);
                        task.setDueDate(dueDate);
                        break;
                    }
                }
            }
            updateTaskList();
        }
    }

    private void updateTaskList() {
        Collections.sort(tasks, (t1, t2) -> Long.compare(t1.getDueDate(), t2.getDueDate()));
        adapter.notifyDataSetChanged();
    }
}
