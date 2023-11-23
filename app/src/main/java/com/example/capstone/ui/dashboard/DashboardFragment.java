package com.example.capstone.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.capstone.R;
import com.example.capstone.databinding.FragmentDashboardBinding;
import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private LinearLayout placeLayout, countryLayout;
    private List<String> country = new ArrayList<>();
    //DB 연결 필수
    private List<Integer> days = new ArrayList<>();
    //DB 연결 필수
    private List<String> locations = new ArrayList<>();
    // 각 나라/일자별로 장소를 저장해서 DB로부터 장소 목록을 받아서 버튼을 추가
    // 장소를 받는 메서드는 아직 구현하지 않음
    // 나라가 없을때 placelayout에 장소를 추가하여 주세요 메서드 구현
    // 장소 추가 삭제 메서드 구현
    // 나라가 추가됐을때 플러스 버튼 왼쪽에 나라탭이 추가되도록 할것
    private Button plus;
    private Integer[] backgroundcolor = {R.color.purple_200, com.google.android.libraries.places.R.color.quantum_brown, com.google.android.libraries.places.R.color.quantum_lightblue};

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        placeLayout = view.findViewById(R.id.place_layout);
        countryLayout = view.findViewById(R.id.country_layout);
        plus = view.findViewById(R.id.plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCountry();
            }
        });

        return view;
    }

    private void addCountry() {
        //팝업창을 띄우면서 나라 입력하고 일자를 받는 메서드 추가해야함
        //나라는 country.add() 일자는 days.add()
        addButtonDynamically();
    }

    private void addButtonDynamically() {
        Button button = new Button(requireContext());
        button.setText(country.get(country.size()-1));
        callPlan(country.size()-1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int countryIndex = country.indexOf(button.getText());
                callPlan(countryIndex);
            }
        });
        button.setBackgroundColor(backgroundcolor[(country.size()-1)%backgroundcolor.length]);
        button.setHeight(80);
        button.setWidth(60);
        button.setBackgroundResource(R.drawable.search_button);
        countryLayout.addView(button);
    }

    private void callPlan(int index) {
        for (int i = 1; i <= days.get(index); i++) {
            TextView textView = new TextView(requireContext());
            textView.setText(i + "일차");
            textView.setWidth(360);
            textView.setHeight(60);
            textView.setBackgroundResource(R.drawable.search_button);
            placeLayout.addView(textView);
            for (int j = 0; j < locations.size(); j++) {
                Button button = new Button(requireContext());
                button.setText(locations.get(j));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //장소이름에 따라 장소정보를 가져와 표시해주는 페이지를 보여줌
                    }
                });
                placeLayout.addView(button);
            }
        }
    }
}