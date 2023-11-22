package com.example.capstone.ui.home;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.capstone.PopupCreateActivity;
import com.example.capstone.R;
import com.example.capstone.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private LinearLayout grouplist;
    private static final int REQUEST_CODE = 123;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        grouplist = root.findViewById(R.id.grouplist);

        // savedInstanceState가 null이고, 처음 생성될 때만 list_item을 추가합니다.
        if (savedInstanceState == null) {
            addListItem();
        }

        return root;
    }

    // list_item 추가하는 메소드
    private void addListItem() {
        View customListItem = LayoutInflater.from(requireContext()).inflate(R.layout.list_item, grouplist, false);
        grouplist.addView(customListItem);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                // createpopup 액티비티로부터 전달받은 데이터 읽기
                String receivedData = data.getStringExtra("data_key");

                // LinearLayout에 list_item.xml 추가하는 코드
                if (receivedData != null && receivedData.equals("sibal")) {
                    addListItem(); // list_item 추가하는 메소드 호출
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}