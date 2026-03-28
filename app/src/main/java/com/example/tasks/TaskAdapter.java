package com.example.tasks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> tasks;
    private final OnTaskDeleteListener deleteListener;
    private final OnTaskStatusToggleListener statusToggleListener;
    private final OnTaskEditListener editListener;

    public interface OnTaskDeleteListener {
        void onDelete(long taskId);
    }

    public interface OnTaskStatusToggleListener {
        void onToggle(Task task, boolean isChecked);
    }

    public interface OnTaskEditListener {
        void onEdit(long taskId);
    }

    public TaskAdapter(
            OnTaskDeleteListener deleteListener,
            OnTaskStatusToggleListener statusToggleListener,
            OnTaskEditListener editListener
    ) {
        this.deleteListener = deleteListener;
        this.statusToggleListener = statusToggleListener;
        this.editListener = editListener;
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
        holder.priorityText.setText(String.format("Priority: %.1f", task.getPriority()));
        holder.deleteButton.setOnClickListener(v -> deleteListener.onDelete(task.getId()));

        holder.statusCheck.setOnCheckedChangeListener(null);
        holder.statusCheck.setChecked(task.getStatus());
        holder.statusCheck.setOnCheckedChangeListener((buttonView, isChecked) ->
                statusToggleListener.onToggle(task, isChecked)
        );

        holder.itemView.setOnLongClickListener(v -> {
            editListener.onEdit(task.getId());
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return tasks != null ? tasks.size() : 0;
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView titleText;
        TextView subjectText;
        TextView durationText;
        TextView priorityText;
        ImageButton deleteButton;
        CheckBox statusCheck;

        TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.text_title);
            subjectText = itemView.findViewById(R.id.text_subject);
            durationText = itemView.findViewById(R.id.text_duration);
            priorityText = itemView.findViewById(R.id.text_priority);
            deleteButton = itemView.findViewById(R.id.button_delete);
            statusCheck = itemView.findViewById(R.id.checkbox_status);
        }
    }
}
