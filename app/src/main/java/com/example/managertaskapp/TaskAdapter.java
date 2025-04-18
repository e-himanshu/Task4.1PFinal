package com.example.managertaskapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<Task> tasks;
    private OnItemClickListener listener;

    // Listener interface
    public interface OnItemClickListener {
        void onItemClick(Task task);
        void onDeleteClick(Task task);
        void onEditClick(Task task);
        void onViewClick(Task task); // View task click listener
    }

    // Set the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle, tvDescription, tvDueDate;
        public Button btnView, btnEdit, btnDelete;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvDueDate = itemView.findViewById(R.id.tv_due_date);
            btnView = itemView.findViewById(R.id.btn_view);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }

    // Constructor
    public TaskAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.tvTitle.setText(task.getTitle());
        holder.tvDescription.setText(task.getDescription());

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        holder.tvDueDate.setText("Due: " + sdf.format(task.getDueDate()));

        // Item click listener
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(task);
            }
        });

        // Delete button click listener
        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(task);
            }
        });

        // Edit button click listener
        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditClick(task);
            }
        });

        // View button click listener
        holder.btnView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onViewClick(task);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
