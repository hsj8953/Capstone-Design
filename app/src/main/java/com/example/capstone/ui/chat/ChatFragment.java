package com.example.capstone.ui.chat;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.capstone.R;
import com.example.capstone.databinding.FragmentChatBinding;

import java.util.Calendar;
import java.util.Locale;

public class ChatFragment extends Fragment {

    private FragmentChatBinding binding;
    private EditText chatEditText;
    private Button sendButton;
    private LinearLayout chatListLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ChatViewModel chatViewModel =
                new ViewModelProvider(this).get(ChatViewModel.class);

        binding = FragmentChatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // 뷰 초기화
        chatEditText = root.findViewById(R.id.chattxt);
        sendButton = root.findViewById(R.id.chatsend);
        chatListLayout = root.findViewById(R.id.chatlist);

        // firstactivity.xml에서 userid 가져오기
        String userId = getActivity().getIntent().getStringExtra("userName");

        // sendButton 클릭 시 처리
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = chatEditText.getText().toString().trim();

                if (!messageText.isEmpty()) {
                    // my_msgbox.xml 레이아웃을 추가하기 위해 LayoutInflater 사용
                    LayoutInflater inflater = LayoutInflater.from(requireContext());
                    View myMsgBox = inflater.inflate(R.layout.my_msgbox, null);

                    // my_msgbox.xml의 각 요소에 접근하여 내용 설정
                    TextView msgTextView = myMsgBox.findViewById(R.id.tv_msg);
                    TextView timeTextView = myMsgBox.findViewById(R.id.tv_time);
                    TextView nameTextView = myMsgBox.findViewById(R.id.tv_name);

                    msgTextView
                            .setText(messageText);
                    // 시간 정보 설정
                    // 현재 시간을 가져오기 위해 Calendar 객체 사용
                    Calendar calendar = Calendar.getInstance();
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    int minute = calendar.get(Calendar.MINUTE);
                    // 현재 시간을 문자열로 변환하여 timeTextView에 설정
                    String currentTime = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
                    timeTextView.setText(currentTime);

                    // userid를 tv_name에 설정
                    nameTextView.setText(userId);

                    // chatlist에 my_msgbox 추가
                    chatListLayout.addView(myMsgBox);

                    // 입력창 초기화
                    chatEditText.setText("");
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}