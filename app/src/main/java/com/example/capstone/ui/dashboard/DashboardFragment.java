package com.example.capstone.ui.dashboard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.capstone.R;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DashboardFragment extends Fragment {
    private LinearLayout placeLayout, countryLayout;
    private TextView noPlace;
    private static List<String> country = new ArrayList<>();
    //DB 연결
    private static List<Integer> days = new ArrayList<>();
    //DB 연결
//    private List<String> locations = new ArrayList<>();
    ArrayList<ArrayList<String>> locations = new ArrayList<>();
    // 각 나라/일자별로 장소를 저장해서 서버로부터 장소 목록을 받아서 버튼을 추가
    // 장소 삭제 메서드 구현
//    private Button plus;
    private Integer[] backgroundcolor = {R.color.purple_200, com.google.android.libraries.places.R.color.quantum_brown, com.google.android.libraries.places.R.color.quantum_lightblue};

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        placeLayout = view.findViewById(R.id.place_layout);
        countryLayout = view.findViewById(R.id.country_layout);
//        plus = view.findViewById(R.id.plus);
        noPlace = view.findViewById(R.id.no_place);

        ButtonDynamically();

        //나라 추가 버튼
//        plus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addCountry();
//            }
//        });

        return view;
    }

    private void addCountry() {
        showCustomLayoutPopup(requireContext());
        //서버에 전송하는 메서드
    }

    //서버에 객체를 전달할때 메서드가 호출되도록 설정해야함
    //나라 탭을 구성하는 메서드
    private void ButtonDynamically() {
        if (locations.size() != -1) {
            for (int i = 0; i < country.size(); i++) {
                noPlace.setVisibility(View.GONE);
                Button button = new Button(requireContext());
                button.setText(country.get(i));
//            callPlan(country.size() - 1);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int countryIndex = country.indexOf(button.getText());
                        callPlan(countryIndex);
                    }
                });
                button.setBackgroundColor(backgroundcolor[(country.size() - 1) % backgroundcolor.length]);
                button.setHeight(80);
                button.setWidth(60);
                button.setBackgroundResource(R.drawable.search_button);
                countryLayout.addView(button);
            }
        } else noPlace.setVisibility(View.VISIBLE);

        //plus버튼 생성
        Button button = new Button(requireContext());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCountry();
            }
        });

        //plus버튼 디자인 부분
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.width = 210;
        layoutParams.height = 180;
        button.setLayoutParams(layoutParams);
        button.setBackgroundResource(R.drawable.plus_btn);
        countryLayout.addView(button);
    }

    //나라 탭의 이름에 따라 맞는 일자와 장소를 표시해주는 메서드
    private void callPlan(int index) {
        for (int i = 1; i <= days.get(index); i++) {
            Button button = new Button(requireContext());

            //일자 탭의 디자인 부분
            button.setText(i + "일차");
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.width = 1000;
            layoutParams.height = 180;
            button.setLayoutParams(layoutParams);
            button.setBackgroundResource(R.drawable.search_button);

            //일자 버튼을 눌렀을때 나오는 메서드
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showCustomSearchpop(requireContext());
                }
            });

            //textView 추가
            placeLayout.addView(button);

            // 첫 번째 행 추가 예시
            // days에 의존해서 일자가 제한적으로 나오도록 해야함
            // 아마 한다면 전에 있던 ArrayList를 pop해서 새로운 배열을 만든 다음에 장소를 추가해서 새로운 ArrayList를 만들어 주는 거
            ArrayList<String> row1 = new ArrayList<>();
            row1.add("서울");
            row1.add("경기도");
            row1.add("부산");
            locations.add(row1);

            //2차원 배열 locations의 일자 순서대로 장소를 추가 해주는 메서드
            for (String value : locations.get(i - 1)) {
                TextView textView = new TextView(requireContext());

                //장소 버튼의 디자인 부분
                textView.setText(value);
                layoutParams.width = 900;
                layoutParams.height = 150;
                button.setLayoutParams(layoutParams);
                button.setBackgroundResource(R.drawable.search_button);

                String details = value;
                textView.setText(details);
                placeLayout.addView(textView);
            }
        }
    }

    private static void showCustomSearchpop(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customLayout = inflater.inflate(R.layout.custom_search_layout, null);

        // 레이아웃 내의 View들 참조
        TextView titleTextView = customLayout.findViewById(R.id.titleTextView);
        EditText editText1 = customLayout.findViewById(R.id.editText1);

        titleTextView.setText("장소 검색"); // 제목 설정

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(customLayout);

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 확인 버튼을 클릭할 때 수행할 동작
                String value1 = editText1.getText().toString();

                //검색할때 넘겨야할 장소이름 value1
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 취소 버튼을 클릭할 때 수행할 동작
                dialog.cancel(); // 팝업창 닫기
            }
        });

        // 팝업창 생성 및 표시
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //나라 추가 팝업창을 띄우는 메서드
    public static void showCustomLayoutPopup(final Context context) {
        // 레이아웃 인플레이션
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customLayout = inflater.inflate(R.layout.custom_popup_layout, null);

        // 레이아웃 내의 View들 참조
        TextView titleTextView = customLayout.findViewById(R.id.titleTextView);
        EditText editText1 = customLayout.findViewById(R.id.editText1);
        EditText editText2 = customLayout.findViewById(R.id.editText2);

        titleTextView.setText("나라를 추가 해 주세요"); // 제목 설정

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(customLayout);

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 확인 버튼을 클릭할 때 수행할 동작
                String value1 = editText1.getText().toString();
                String value2 = editText2.getText().toString();

                country.add(value1);
                days.add(Integer.valueOf(value2));
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 취소 버튼을 클릭할 때 수행할 동작
                dialog.cancel(); // 팝업창 닫기
            }
        });

        // 팝업창 생성 및 표시
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    //장소이름에 따라 장소정보를 가져와 표시해주는 페이지를 보여주는 메서드
    //검색어에 따라 장소 정보를 가져오는 메서드로 교체 예정
//    private void getPlaceDetails(String placeId) {
//        // Place Details 요청
//        List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.PHONE_NUMBER);
//        FetchPlaceRequest request = FetchPlaceRequest.builder(placeId, placeFields).build();
//
//        placesClient.fetchPlace(request)
//                //요청이 성공하였을 경우 호출되는 리스너
//                .addOnSuccessListener((response) -> {
//            Place place = response.getPlace();
//            Log.i("PlaceDetails", "Place found: " + place.getName());
//
//            // 원하는 정보를 사용하도록 수정
//            String name = place.getName();
//            String address = place.getAddress();
//            String phoneNumber = place.getPhoneNumber();
//
//            //
//        })
//                //요청이 실패하였을 경우 호출되는 리스너
//                .addOnFailureListener((exception) -> {
//            if (exception instanceof ApiException) {
//                ApiException apiException = (ApiException) exception;
//                int statusCode = apiException.getStatusCode();
//                Log.e("PlaceDetails", "Place not found: " + exception.getMessage());
//            }
//        });
//    }
}