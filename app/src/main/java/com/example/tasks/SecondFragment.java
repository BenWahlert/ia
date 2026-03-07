package com.example.tasks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.tasks.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private TaskViewModel viewModel;

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

        binding.buttonSave.setOnClickListener(v -> saveTask());
    }

    private void saveTask() {
        String title = binding.editTitle.getText() != null ? binding.editTitle.getText().toString().trim() : "";
        String subjectName = binding.editSubject.getText() != null ? binding.editSubject.getText().toString().trim() : "";
        String durationStr = binding.editDuration.getText() != null ? binding.editDuration.getText().toString().trim() : "";

        if (title.isEmpty()) {
            binding.editTitle.setError("Title is required");
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

        Task task = new Task(title, subject, System.currentTimeMillis(), duration);
        viewModel.addTask(task);
        NavHostFragment.findNavController(this).navigateUp();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
