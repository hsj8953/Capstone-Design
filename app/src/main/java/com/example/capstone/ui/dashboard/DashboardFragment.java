package com.example.capstone.ui.dashboard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.capstone.R;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardFragment extends Fragment {
    private static final String TAG = "PlacesSearch";
    private LinearLayout dashboard;

    private CheckBox placeChecked;
    private LinearLayout countryLayout;
    private LinearLayout placeLayout;
    private LinearLayout daysLayout;

    private LinearLayout placeAddDelete;

    private LinearLayout checkPlace;
    private TextView noPlace;
    private Button selectedCountryTab;
    Map<String, ArrayList<ArrayList<String>>> countries = new HashMap<>();
    String apiKey = "AIzaSyBJrumY0iDABQtCBlwS2jtabIeU80M6hk4";
    PlacesClient placesClient;
    private Integer[] backgroundcolor = {com.google.android.libraries.places.R.color.quantum_lightblue, R.color.purple_200};
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        dashboard = view.findViewById(R.id.dashboard);
        countryLayout = view.findViewById(R.id.country_layout);
        placeLayout = view.findViewById(R.id.place_layout);
        daysLayout = view.findViewById(R.id.days_layout);
        noPlace = view.findViewById(R.id.no_place);


        CountryDynamically(null);
        return view;
    }

    //서버에 객체를 전달할때 메서드가 호출되도록 설정해야함
    //나라 탭을 구성해주는 메서드
    private void CountryDynamically(String whatTab) {
        if (countries.size() != -1) {
            countryLayout.removeAllViews();
            int i = 0;
            int color = 0;
            for (String key : countries.keySet()) {
//                int whatColor = backgroundcolor[(i) % backgroundcolor.length];
//                color = ContextCompat.getColor(requireContext(), whatColor);
                Button countryTab = new Button(requireContext());
                countryTab.setText(key);
                int finalColor = color;
                countryTab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //배경색을 탭 색깔에 맞춰 지정
                        if (selectedCountryTab != null) {
                            selectedCountryTab.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.custom2));
                        }
                        // 선택된 탭의 색상을 변경
                        countryTab.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.custom3));
                        selectedCountryTab = countryTab; // 선택된 탭으로 설정
                        DayDynamically(key , finalColor);
                    }
                });
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setColor(color);
                float[] cornerRadii = new float[]{30, 30, 30, 30, 0, 0, 0, 0};
                gradientDrawable.setCornerRadii(cornerRadii);
                countryTab.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.custom2));
                countryTab.setHeight(250);
                countryTab.setWidth(200);
                countryLayout.addView(countryTab);
                i++;
            }
            DayDynamically(whatTab, color);

        }
        //plus버튼 생성
        Button plusBtn = new Button(requireContext());
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomLayoutPopup(requireContext());
            }
        });

        //plus버튼 디자인 부분
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.width = 160;
        layoutParams.height = 160;
        plusBtn.setLayoutParams(layoutParams);
        plusBtn.setBackgroundResource(R.drawable.baseline_add_24);
        countryLayout.addView(plusBtn);
    }

    //나라 탭의 이름에 따라 맞는 일자를 구성해주는 메서드
    private void DayDynamically(String whatKey, int whatColor) {
        if(countries.get(whatKey) != null) {
            dashboard.setBackgroundColor(whatColor);
            daysLayout.setBackgroundColor(whatColor);
            placeLayout.setBackgroundColor(whatColor);
            if(countries.get(whatKey).size() > 0) {
                daysLayout.removeAllViews();
                for (int i = 0; i < countries.get(whatKey).size(); i++) {
                    Button daysTab = new Button(requireContext());

                    //일자 탭의 디자인 부분
                    daysTab.setText((i + 1) + "일차");
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.width = 250;
                    layoutParams.height = 120;
                    layoutParams.topMargin = 10;
                    layoutParams.bottomMargin = 10;
                    layoutParams.leftMargin = 10;
                    daysTab.setLayoutParams(layoutParams);
                    daysTab.setBackgroundResource(R.drawable.slide_button_background);

                    //일자 버튼을 눌렀을때 나오는 메서드
                    int finalI = i;
                    daysTab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PlaceDynamically(whatKey, finalI);
                        }
                    });

                    //textView 추가
                    daysLayout.addView(daysTab);
                }
                PlaceDynamically(whatKey, 0);

                Button addDays = new Button(requireContext());

                Button removeDays = new Button(requireContext());
                addDays.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addDays(whatKey, whatColor);
                    }
                });

                removeDays.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        removeDays(whatKey, whatColor);
                    }
                });

                //plus버튼 디자인 부분
                addDays.setText("+");
                removeDays.setText("-");

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                layoutParams.width = 250;
                layoutParams.height = 120;
                layoutParams.topMargin = 10;
                layoutParams.leftMargin = 10;

                LinearLayout.LayoutParams layoutParamsbtn = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                layoutParamsbtn.width = 120;
                layoutParamsbtn.height = 120;
                layoutParamsbtn.topMargin = 10;
                layoutParamsbtn.leftMargin = 10;

                addDays.setLayoutParams(layoutParamsbtn);
                addDays.setBackgroundResource(R.drawable.slide_button_background);
                daysLayout.addView(addDays);

                removeDays.setLayoutParams(layoutParamsbtn);
                removeDays.setBackgroundResource(R.drawable.slide_button_background);
                daysLayout.addView(removeDays);
            } else {
                addDays(whatKey, whatColor);
            }
        }
    }

    private void addDays(String whatKey, int whatColor) {
        ArrayList<String> Days = new ArrayList<>();
        countries.get(whatKey).add(Days);
        DayDynamically(whatKey, whatColor);
    }
    private void removeDays(String whatKey, int whatColor){
        ArrayList<String> Days = new ArrayList<>();
        countries.get(whatKey).remove(Days);
        DayDynamically(whatKey, whatColor);
    }

    //나라 탭의 이름과 일자에 따라 맞는 장소들을 구성해주는 메서드
    private void PlaceDynamically(String whatKey, int i) {
        placeLayout.removeAllViews();

        for (String s : countries.get(whatKey).get(i)) {

            if(s != null && !s.isEmpty()) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View placeItemView = inflater.inflate(R.layout.placeitem, null);
                TextView placeTextView = placeItemView.findViewById(R.id.placeName);

                placeTextView.setText(s);
                placeLayout.addView(placeItemView);
            }

        }

        //추가,삭제 버튼을 수평정렬할 horizontal속성의 레이아웃 추가
        placeAddDelete = getView().findViewById(R.id.placeAddDelete);
        placeAddDelete.setGravity(Gravity.CENTER);
        placeAddDelete.removeAllViews();

        Button addPlace = new Button(requireContext());
        Button deletePlace = new Button(requireContext());
        addPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomSearchpop(whatKey, i, requireContext());
            }
        });
        deletePlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeChecked = getView().findViewById(R.id.placeChecked);
                for (int j = placeLayout.getChildCount() - 1; j >= 0; j--) {
                    if (placeChecked.isChecked()) {
                        countries.get(whatKey).get(i).remove(j);
                        PlaceDynamically(whatKey, i);
                    }
                }
            }
        });

        //버튼 디자인 부분
        addPlace.setText("장소 추가");
        deletePlace.setText("장소 삭제");
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.width = 300;
        layoutParams.height = 150;
        layoutParams.topMargin = 20;
        addPlace.setLayoutParams(layoutParams);
        addPlace.setBackgroundResource(R.drawable.slide_button_background);

        deletePlace.setLayoutParams(layoutParams);
        deletePlace.setBackgroundResource(R.drawable.slide_button_background);
        placeAddDelete.addView(addPlace);
        placeAddDelete.addView(deletePlace);
    }

    //장소 추가 팝업창을 띄우는 메서드
    private void showCustomSearchpop(String whatKey, int i, Context context) {
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
                dialog.dismiss();
                // Places API 초기화
                Places.initialize(requireContext(), apiKey);
                // 새로운 Places 클라이언트 생성
                placesClient = Places.createClient(requireContext());
                searchForPlaces(whatKey, i, value1);
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
    public void showCustomLayoutPopup(final Context context) {
        // 레이아웃 인플레이션
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customLayout = inflater.inflate(R.layout.custom_popup_layout, null);

        // 레이아웃 내의 View들 참조
        TextView titleTextView = customLayout.findViewById(R.id.titleTextView);
        EditText editText1 = customLayout.findViewById(R.id.editText1);

        titleTextView.setText("나라를 추가 해 주세요"); // 제목 설정

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(customLayout);

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 확인 버튼을 클릭할 때 수행할 동작
                String value1 = editText1.getText().toString();
                ArrayList<ArrayList<String>> days = new ArrayList<>();
                ArrayList<String> places = new ArrayList<>();
                days.add(places);
                countries.put(value1, days);
                CountryDynamically(value1);
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

    //장소를 검색하는 메서드
    private void searchForPlaces(String whatKey, int i, String value) {
        // Autocomplete 검색어 예측 요청 생성
        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder().setQuery(value).build();

        // Autocomplete 검색어 예측 비동기 요청 수행
        placesClient.findAutocompletePredictions(request).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FindAutocompletePredictionsResponse response = task.getResult();
                if (response != null) {
                    List<AutocompletePrediction> predictions = response.getAutocompletePredictions();
                    // 선택한 장소에 대한 세부 정보 가져오기
                    String placeId = predictions.get(0).getPlaceId();
                    getPlaceDetails(whatKey, i, placeId);
                }
            } else {
                Log.e(TAG, "검색어 예측 중 오류 발생: " + task.getException());
            }
        });
    }
    private void getPlaceDetails(String whatKey, int i, String placeId) {
        FetchPlaceRequest request = FetchPlaceRequest.builder(placeId, Arrays.asList(Place.Field.NAME, Place.Field.PHONE_NUMBER, Place.Field.ADDRESS)).build();

        // Place 세부 정보를 비동기적으로 가져오기
        placesClient.fetchPlace(request).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Place place = task.getResult().getPlace();

                // 필요한 세부 정보 추출
                String placeName = place.getName();
                String placePhoneNumber = place.getPhoneNumber();
                String placeAddress = place.getAddress();

                //팝업창 띄우기
                LayoutInflater inflater = (LayoutInflater) requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customLayout = inflater.inflate(R.layout.custom_result_layout, null);

                // 레이아웃 내의 View들 참조
                TextView titleTextView = customLayout.findViewById(R.id.titleTextView);
                EditText editText1 = customLayout.findViewById(R.id.editText1);
                Button resultBtn = customLayout.findViewById(R.id.button1);

                // 제목 설정
                titleTextView.setText("장소 검색");
                resultBtn.setText(placeName + "\n" + placePhoneNumber + "\n" + placeAddress);

                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setView(customLayout);

                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override

                    public void onClick(DialogInterface dialog, int which) {
                        // 확인 버튼을 클릭할 때 수행할 동작
                        String value1 = editText1.getText().toString();
                        dialog.dismiss();
                        // Places API 초기화
                        Places.initialize(requireContext(), apiKey);
                        // 새로운 Places 클라이언트 생성
                        placesClient = Places.createClient(requireContext());
                        searchForPlaces(whatKey, i, value1);
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

                //장소 버튼을 눌렀을때 실행되는 동작
                resultBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        countries.get(whatKey).get(i).add(placeName);
                        PlaceDynamically(whatKey, i);
                        dialog.cancel();
                    }
                });

                dialog.show();
            } else {
                Log.e(TAG, "장소 세부 정보를 가져오는 중 오류 발생: " + task.getException());
            }
        });
    }
}
