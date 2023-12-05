package com.example.capstone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.capstone.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 액티비티 제목 표시줄 숨기기
        getSupportActionBar().hide();

        // SharedPreferences 초기화
        prefs = getSharedPreferences("Pref", MODE_PRIVATE);

        // ActivityMainBinding을 이용하여 레이아웃 설정
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // BottomNavigationView 및 NavController 설정
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications,
                R.id.navigation_chat, R.id.navigation_compare)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

    }


    // 그룹 생성 팝업을 호출하는 메서드
    public void mOnCreatePopupClick(View v){
        // 데이터를 담아서 팝업(액티비티) 호출
        Intent intent = new Intent(this, PopupCreateActivity.class);
        intent.putExtra("data", "그룹 이름");
        startActivityForResult(intent, 1);
    }

    // 그룹 가입 팝업을 호출하는 메서드
    public void mOnJoinPopupClick(View v){
        // 데이터를 담아서 팝업(액티비티) 호출
        Intent intent = new Intent(this, PopupJoinActivity.class);
        intent.putExtra("data", "그룹 번호");
        startActivityForResult(intent, 1);
    }
}