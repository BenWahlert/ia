package com.example.tasks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.DatePickerDialog;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.tasks.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    public static final String ARG_TASK_ID = "task_id";

    private FragmentSecondBinding binding;
    private TaskViewModel viewModel;
    private ArrayAdapter<String> subjectAdapter;
    private Task editingTask;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(TaskViewModel.class);

        setupSubjectDropdown();
        loadEditingTask();
        binding.editDeadline.setOnClickListener(v -> showDatePicker());
        binding.buttonSave.setOnClickListener(v -> saveTask());
    }

    private void loadEditingTask() {
        Bundle args = getArguments();
        if (args == null || !args.containsKey(ARG_TASK_ID)) {
            return;
        }

        long taskId = args.getLong(ARG_TASK_ID, -1L);
        if (taskId <= 0L) {
            return;
        }

        viewModel.getTaskById(taskId).observe(getViewLifecycleOwner(), task -> {
            if (task == null) {
                return;
            }
            editingTask = task;
            binding.editTitle.setText(task.getTitle());
            binding.editSubject.setText(task.getSubject().getSubject(), false);
            binding.editDuration.setText(String.valueOf(task.getDuration()));
            binding.editImportance.setText(String.valueOf(task.getImportance()));
            binding.editDifficulty.setText(String.valueOf(task.getDifficulty()));
            LocalDate date = Instant.ofEpochMilli(task.getDeadline())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            binding.editDeadline.setText(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
        });
    }

    private void setupSubjectDropdown() {
        AutoCompleteTextView subjectView = binding.editSubject;
        subjectAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line
        );
        subjectView.setAdapter(subjectAdapter);
        subjectView.setKeyListener(null);
        subjectView.setOnClickListener(v -> subjectView.showDropDown());
        subjectView.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                subjectView.showDropDown();
            }
        });

        viewModel.getSubjects().observe(getViewLifecycleOwner(), subjects -> {
            subjectAdapter.clear();
            for (Subject subject : subjects) {
                subjectAdapter.add(subject.getSubject());
            }
            subjectAdapter.notifyDataSetChanged();
        });
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                requireContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String date = String.format(
                            Locale.US,
                            "%04d-%02d-%02d",
                            selectedYear,
                            selectedMonth + 1,
                            selectedDay
                    );
                    binding.editDeadline.setText(date);
                },
                year,
                month,
                day
        );
        dialog.show();
    }
    private void saveTask() {
        String title = binding.editTitle.getText() != null ? binding.editTitle.getText().toString().trim() : "";
        String subjectName = binding.editSubject.getText() != null ? binding.editSubject.getText().toString().trim() : "";
        String durationStr = binding.editDuration.getText() != null ? binding.editDuration.getText().toString().trim() : "";
        String deadlineStr = binding.editDeadline.getText() != null ? binding.editDeadline.getText().toString().trim() : "";
        String importanceStr = binding.editImportance.getText() != null ? binding.editImportance.getText().toString().trim() : "";
        String difficultyStr = binding.editDifficulty.getText() != null ? binding.editDifficulty.getText().toString().trim() : "";

        if (title.isEmpty()) {
            binding.editTitle.setError("Title is required");
            return;
        }

        if (deadlineStr.isEmpty()) {
            binding.editDeadline.setError("Deadline is required");
            return;
        }

        if (importanceStr.isEmpty()) {
            binding.editImportance.setError("Importance is required");
            return;
        }

        if (difficultyStr.isEmpty()) {
            binding.editDifficulty.setError("Difficulty is required");
            return;
        }

        if (subjectName.isEmpty()) {
            binding.editSubject.setError("Select a subject");
            return;
        }

        List<Subject> subjects = viewModel.getSubjects().getValue();
        boolean subjectExists = false;
        if (subjects != null) {
            for (Subject subject : subjects) {
                if (subject.getSubject().equalsIgnoreCase(subjectName)) {
                    subjectExists = true;
                    break;
                }
            }
        }

        if (!subjectExists) {
            binding.editSubject.setError("Select a subject from the list");
            return;
        }

        Subject subject = new Subject(subjectName);
        int duration = 0;
        if (!durationStr.isEmpty()) {
            try {
                duration = Integer.parseInt(durationStr);
            } catch (NumberFormatException e) {
                binding.editDuration.setError("Invalid number");
                return;
            }
        }

        long deadline;
        try {
            LocalDate date = LocalDate.parse(deadlineStr, DateTimeFormatter.ISO_LOCAL_DATE);
            deadline = date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        } catch (DateTimeParseException e) {
            binding.editDeadline.setError("Use YYYY-MM-DD");
            return;
        }

        int importance;
        try {
            importance = Integer.parseInt(importanceStr);
        } catch (NumberFormatException e) {
            binding.editImportance.setError("Invalid number");
            return;
        }

        if (importance < 1 || importance > 5) {
            binding.editImportance.setError("Use 1-5");
            return;
        }

        int difficulty;
        try {
            difficulty = Integer.parseInt(difficultyStr);
        } catch (NumberFormatException e) {
            binding.editDifficulty.setError("Invalid number");
            return;
        }

        if (difficulty < 1 || difficulty > 5) {
            binding.editDifficulty.setError("Use 1-5");
            return;
        }

        Task task;
        if (editingTask != null) {
            task = new Task(
                    editingTask.getId(),
                    title,
                    subject,
                    deadline,
                    duration,
                    editingTask.getStatus(),
                    importance,
                    difficulty
            );
        } else {
            task = new Task(title, subject, deadline, duration, importance, difficulty);
        }

        viewModel.saveTask(task);
        NavHostFragment.findNavController(this).navigateUp();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
