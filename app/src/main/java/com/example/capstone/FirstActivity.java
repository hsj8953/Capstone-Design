package com.example.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.capstone.databinding.ActivityFirstBinding;
import com.example.capstone.ui.chat.ChatFragment;

import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FirstActivity extends AppCompatActivity {

    private ActivityFirstBinding mBinding;
    private EditText editText;
    private Handler sliderHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 액티비티 제목 표시줄 숨기기
        getSupportActionBar().hide();

        // activity_first 레이아웃 설정
        setContentView(R.layout.activity_first);

        // ActivityFirstBinding을 이용하여 레이아웃 설정
        mBinding = ActivityFirstBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        // EditText 초기화
        editText = findViewById(R.id.userid);

        // 로그인 버튼 설정 및 클릭 리스너 추가
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputText = editText.getText().toString();

                if (!inputText.trim().isEmpty()) {
                    // 입력된 텍스트가 공백이 아닌 경우 MainActivity로 이동
                    Toast.makeText(FirstActivity.this, inputText + "님 어서오세요.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FirstActivity.this, MainActivity.class);

                    // 데이터를 Intent에 추가
                    intent.putExtra("userName", inputText);

                    // ChatFragment로 전환하면서 Intent와 함께 전송
                    ChatFragment chatFragment = new ChatFragment();
                    chatFragment.setArguments(intent.getExtras());

                    startActivity(intent);
                } else {
                    // 입력된 텍스트가 공백인 경우 사용자에게 메시지 및 힌트 제공
                    editText.setHintTextColor(getResources().getColor(android.R.color.holo_red_light, getTheme()));
                    Toast.makeText(FirstActivity.this, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 메모리 누수 방지를 위해 바인딩 해제
        mBinding = null;
    }
}
