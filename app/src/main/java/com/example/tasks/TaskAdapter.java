package com.example.tasks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> tasks;
    private final OnTaskDeleteListener deleteListener;

    public interface OnTaskDeleteListener {
        void onDelete(long taskId);
    }

    public TaskAdapter(OnTaskDeleteListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.titleText.setText(task.getTitle());
        holder.subjectText.setText(task.getSubject().getSubject());
        holder.durationText.setText(task.getDuration() + " minutes");
        holder.deleteButton.setOnClickListener(v -> deleteListener.onDelete(task.getId()));
    }

    @Override
    public int getItemCount() {
        return tasks != null ? tasks.size() : 0;
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView titleText;
        TextView subjectText;
        TextView durationText;
        ImageButton deleteButton;

        TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.text_title);
            subjectText = itemView.findViewById(R.id.text_subject);
            durationText = itemView.findViewById(R.id.text_duration);
            deleteButton = itemView.findViewById(R.id.button_delete);
        }
    }
}
