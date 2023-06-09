package com.example.se114n21.ViewModels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.se114n21.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class admin_QLSanPham extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_qlsan_pham);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationAdminView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_admin_sanPham);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.bottom_admin_khachHang:
                    startActivity(new Intent(getApplicationContext(), admin_QLKhachHang.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_admin_sanPham:
                    return true;
                case R.id.bottom_admin_hoaDon:
                    startActivity(new Intent(getApplicationContext(), admin_QLHoaDon.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_admin_nhanVien:
                    startActivity(new Intent(getApplicationContext(), admin_QLNhanVien.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_admin_account:
                    startActivity(new Intent(getApplicationContext(), AdminMain.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
            }
            return false;
        });

    }
}