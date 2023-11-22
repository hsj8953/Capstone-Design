package com.example.capstone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PopupCreateActivity extends Activity {
//    public class PopupCreateActivity extends Activity implements View.OnClickListener

    TextView txtText_create;
    private LinearLayout groupList;
    Button createbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_createpopup);

//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null) {
//            int listID = bundle.getInt("LIST_ID", 0);
//            if (listID != 0) {
//                groupList = findViewById(listID);
//                Button createButton = findViewById(R.id.createbtn);
//                createButton.setOnClickListener(this);
//            }
//        }

        //UI 객체생성
        txtText_create = (TextView)findViewById(R.id.txtText_create);
        createbtn = findViewById(R.id.createbtn);

        //데이터 가져오기
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        txtText_create.setText(data);

        createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 데이터를 전달할 Intent 생성
                Intent intent = new Intent();
                // 전달할 데이터 추가 (여기서는 단순한 문자열로 예시를 듭니다)
                intent.putExtra("data_key", "sibal");
                // setResult를 통해 데이터를 fragment_home 액티비티로 전달
                setResult(RESULT_OK, intent);
                // 현재 액티비티 종료
                finish();
            }
        });
    }

//    @Override
//    public void onClick(View view) {
//        View customListItem = LayoutInflater.from(this).inflate(R.layout.list_item, groupList, false);
//
//        if (groupList == null) {
//            // groupList가 null인 경우 처리할 작업
//            // 예를 들어, Log 메시지 출력 또는 다른 초기화 작업 등을 수행할 수 있습니다.
//            Log.e("PopupCreateActivity", "groupList is null");
//        } else {
//            // groupList가 null이 아닌 경우
//            // groupList를 사용한 작업 수행
//            groupList.addView(customListItem);
//        }
//    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        //데이터 전달하기
        Intent intent = new Intent();
        intent.putExtra("result", "Close Popup");
        setResult(RESULT_OK, intent);

        //액티비티(팝업) 닫기
        finish();
    }

    public void mOnCreate(View v){
        //데이터 전달하기
        Intent intent = new Intent();
        intent.putExtra("result", "Close Popup");
        setResult(RESULT_OK, intent);

        //액티비티(팝업) 닫기
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}


