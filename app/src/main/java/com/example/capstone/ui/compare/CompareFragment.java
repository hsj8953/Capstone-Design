package com.example.capstone.ui.compare;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.capstone.databinding.FragmentCompareBinding;

public class CompareFragment extends Fragment {

    private FragmentCompareBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CompareViewModel compareViewModel =
                new ViewModelProvider(this).get(CompareViewModel.class);

        binding = FragmentCompareBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textCompare;
        compareViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}