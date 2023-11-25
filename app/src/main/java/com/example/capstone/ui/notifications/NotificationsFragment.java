package com.example.capstone.ui.notifications;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone.MainActivity;
import com.example.capstone.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment implements OnMapReadyCallback {
    private MapView mapView;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private Button locationButton;
    private List<LatLng> locations = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        // 현재 위치를 가져오는 버튼 클릭 이벤트
        locationButton = view.findViewById(R.id.here_btn);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        updateMarkers();
        getLocation();
    }

    private void updateMarkers() {
        mMap.clear(); // 이전 마커 제거

        //나중에 삭제해야할 구문!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        locations.add(new LatLng(37.541, 126.986));

        // 마커 추가
        for (int i = 0; i < locations.size(); i++) {
            LatLng location = locations.get(i);
            addMarker(location, "마커 " + (i + 1));
        }
    }

    private void addMarker(LatLng position, String title) {
        MarkerOptions markerOptions = new MarkerOptions()
                .position(position)
                .title(title);

        Marker marker = mMap.addMarker(markerOptions);
        marker.showInfoWindow(); // 마커에 제목 표시
    }

//    // 배열이 동적으로 변경되었을 때 호출되는 메서드
//    private void onDataChange() {
//        // locations 리스트 업데이트 후 마커 갱신
//        updateMarkers();
//    }
//
//    // 데이터 추가 및 삭제 메서드
//    private void addLocation(LatLng location) {
//        locations.add(location);
//        onDataChange();
//    }
//
//    private void removeLocation() {
//        if (!locations.isEmpty()) {
//            locations.remove(locations.size() - 1);
//            onDataChange();
//        }
//    }

    private void getLocation() {
        // 위치 권한이 허용되었는지 확인
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            // 사용자의 현재 위치 받아오기
            fusedLocationClient.getLastLocation().addOnSuccessListener(requireActivity(), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        LatLng locate = new LatLng(location.getLatitude(), location.getLongitude());
                        // 현재 위치로 지도 이동
                        mMap.addMarker(new MarkerOptions().position(locate).title("현재 위치"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locate, 10));
                    }
                }
            });
        } else {
            // 위치 권한이 없을 경우 권한 요청
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},1);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
