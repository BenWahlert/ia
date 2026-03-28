package com.example.tasks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.tasks.databinding.FragmentArchiveBinding;

import java.util.List;

public class ArchiveFragment extends Fragment {

    private FragmentArchiveBinding binding;
    private TaskViewModel viewModel;
    private TaskAdapter adapter;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentArchiveBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(TaskViewModel.class);

        adapter = new TaskAdapter(
                taskId -> viewModel.removeTask(taskId),
                (task, isChecked) -> viewModel.updateTaskStatus(task, isChecked),
                this::navigateToEdit
        );
        binding.recyclerTasks.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerTasks.setAdapter(adapter);

        viewModel.getArchivedTasks().observe(getViewLifecycleOwner(), tasks -> {
            adapter.setTasks(tasks);
            updateEmptyState(tasks);
        });
    }

    private void navigateToEdit(long taskId) {
        Bundle args = new Bundle();
        args.putLong(SecondFragment.ARG_TASK_ID, taskId);
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_ArchiveFragment_to_SecondFragment, args);
    }

    private void updateEmptyState(List<Task> tasks) {
        if (tasks == null || tasks.isEmpty()) {
            binding.recyclerTasks.setVisibility(View.GONE);
            binding.textEmpty.setVisibility(View.VISIBLE);
        } else {
            binding.recyclerTasks.setVisibility(View.VISIBLE);
            binding.textEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
