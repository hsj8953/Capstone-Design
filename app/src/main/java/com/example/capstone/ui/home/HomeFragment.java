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
        // 기존 코드 유지
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        if (binding == null) {
            // binding이 null인 경우, FragmentHomeBinding을 사용하여 레이아웃 설정
            binding = FragmentHomeBinding.inflate(inflater, container, false);
            View root = binding.getRoot();

            // 팝업을 열기 위한 버튼 클릭 리스너 설정
            Button openPopupButton = root.findViewById(R.id.createpop);
            openPopupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // PopupCreateActivity를 열기 위한 Intent 생성 및 시작
                    Intent intent = new Intent(getActivity(), PopupCreateActivity.class);
                    startActivityForResult(intent, YOUR_REQUEST_CODE);
                }
            });

            return root;
        } else {
            // binding이 이미 존재하는 경우, 기존의 View 반환하여 화면에 표시
            return binding.getRoot();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == YOUR_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // 팝업 액티비티에서 전달된 데이터 수신
            String receivedData = data.getStringExtra("groupName");
            Log.d("ReceivedData", "Received Data: " + receivedData);

            // 수신된 데이터가 null이 아닌 경우 처리
            if (receivedData != null) {
                // list_item.xml을 View로 변환
                View listItem = LayoutInflater.from(getContext()).inflate(R.layout.list_item, null);

                // groupname TextView 찾아 값 설정
                TextView groupNameTextView = listItem.findViewById(R.id.groupname);
                groupNameTextView.setText(receivedData);

                // 기존 리스트 텍스트 숨기기
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
        // 바인딩 해제하여 메모리 누수 방지
        binding = null;
    }
}