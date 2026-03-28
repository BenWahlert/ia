package com.example.tasks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tasks.databinding.FragmentAddSubjectBinding;

import java.util.List;

public class AddSubjectFragment extends Fragment {

    private FragmentAddSubjectBinding binding;
    private TaskViewModel viewModel;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentAddSubjectBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(TaskViewModel.class);
        binding.buttonAddSubject.setOnClickListener(v -> addSubject());
    }

    private void addSubject() {
        String name = binding.editNewSubject.getText() != null
                ? binding.editNewSubject.getText().toString().trim()
                : "";

        if (name.isEmpty()) {
            binding.editNewSubject.setError("Subject name is required");
            return;
        }

        List<Subject> current = viewModel.getSubjects().getValue();
        if (current != null) {
            for (Subject subject : current) {
                if (subject.getSubject().equalsIgnoreCase(name)) {
                    binding.editNewSubject.setError("Subject already exists");
                    return;
                }
            }
        }

        viewModel.addSubject(new Subject(name));
        binding.editNewSubject.setText("");
        Toast.makeText(requireContext(), getString(R.string.subject_added), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
