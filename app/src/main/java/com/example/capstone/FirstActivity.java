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
        getSupportActionBar().hide();
        setContentView(R.layout.activity_first);

        mBinding = ActivityFirstBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
//        txtResult = (TextView)findViewById(R.id.txtResult);

        editText = findViewById(R.id.userid);


        List<String> sliderItems = new ArrayList<>();
            sliderItems.add("https://cdn.pixabay.com/photo/2019/12/26/10/44/horse-4720178_1280.jpg");
            sliderItems.add("https://cdn.pixabay.com/photo/2020/11/04/15/29/coffee-beans-5712780_1280.jpg");
            sliderItems.add("https://cdn.pixabay.com/photo/2020/11/10/01/34/pet-5728249_1280.jpg");
            sliderItems.add("https://cdn.pixabay.com/photo/2020/12/21/19/05/window-5850628_1280.png");
            sliderItems.add("https://cdn.pixabay.com/photo/2014/03/03/16/15/mosque-279015_1280.jpg");

        mBinding.vpImageSlider.setAdapter(new SliderAdapter(this, mBinding.vpImageSlider, sliderItems));

        mBinding.vpImageSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 5000);
            }
        });

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputText = editText.getText().toString();

                if (!inputText.trim().isEmpty()) {
                    // 입력된 텍스트가 공백이 아닌 경우 MainActivity로 이동
                    Toast.makeText(FirstActivity.this, inputText + "님 어서오세요.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FirstActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    editText.setHintTextColor(getResources().getColor(android.R.color.holo_red_light, getTheme()));
                    Toast.makeText(FirstActivity.this, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding = null;
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            mBinding.vpImageSlider.setCurrentItem(mBinding.vpImageSlider.getCurrentItem() + 1);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 4000);
    }




//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(requestCode==1){
//            if(resultCode==RESULT_OK){
//                //데이터 받기
//                String result = data.getStringExtra("result");
//                txtResult.setText(result);
//            }
//        }
//    }
}
