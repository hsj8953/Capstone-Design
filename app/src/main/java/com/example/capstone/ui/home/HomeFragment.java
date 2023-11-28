package com.example.capstone.ui.home;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.capstone.PopupCreateActivity;
import com.example.capstone.R;
import com.example.capstone.databinding.FragmentHomeBinding;

import org.w3c.dom.Text;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private static final int YOUR_REQUEST_CODE = 100;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 기존의 코드를 그대로 유지합니다.
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        if (binding == null) {
            binding = FragmentHomeBinding.inflate(inflater, container, false);
            View root = binding.getRoot();

            Button openPopupButton = root.findViewById(R.id.createpop);

            openPopupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), PopupCreateActivity.class);
                    startActivityForResult(intent, YOUR_REQUEST_CODE);
                }
            });

            return root;
        } else {
            // binding이 이미 존재하는 경우, 기존의 View를 반환하여 화면에 표시합니다.
            return binding.getRoot();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == YOUR_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // 팝업 액티비티에서 전달된 데이터 받기
            String receivedData = data.getStringExtra("groupName");
            Log.d("ReceivedData", "Received Data: " + receivedData);

            // 데이터가 null이 아닌 경우에만 처리
            if (receivedData != null) {
                // LayoutInflater를 사용하여 list_item.xml을 View로 변환
                View listItem = LayoutInflater.from(getContext()).inflate(R.layout.list_item, null);

                // TextView 중에서 groupname을 찾아서 값을 설정합니다.
                TextView groupNameTextView = listItem.findViewById(R.id.groupname);
                groupNameTextView.setText(receivedData);

                // 기존에 있는 리스트 텍스트 숨기기
                TextView listTextView = getView().findViewById(R.id.listtxt);
                listTextView.setVisibility(View.GONE);

                // LinearLayout 찾기
                LinearLayout linearLayout = getView().findViewById(R.id.grouplist);

                // LinearLayout에 listItem 추가
                linearLayout.addView(listItem);
            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}