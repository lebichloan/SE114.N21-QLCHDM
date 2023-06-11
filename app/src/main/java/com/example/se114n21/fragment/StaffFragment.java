package com.example.se114n21.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.se114n21.Adapter.StaffAdapter;
import com.example.se114n21.Models.NhanVien;
import com.example.se114n21.R;
import com.example.se114n21.Adapter.StaffAdapter;
import com.example.se114n21.databinding.FragmentStaffBinding;
import com.example.se114n21.listener.IOnManagerStaffListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StaffFragment extends BaseFragment {
    FragmentStaffBinding fragmentStaffBinding;
    StaffAdapter staffAdapter;
    List<NhanVien> staffs;

    public StaffFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter();
        initEvents();
        initRecyclerView();
        initSearch();
        loadData("");
    }

    private void initEvents() {
        fragmentStaffBinding.fabAddStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAddFragment(null);
            }
        });
    }

    private void goToAddFragment(NhanVien nhanVien) {
        Bundle bundle = null;
        if (nhanVien != null){
            bundle = new Bundle();
            bundle.putSerializable("staff", nhanVien);
        }

        getParentFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.container_staff, AddOrEditStaffFragment.class, bundle)
                .addToBackStack(null)
                .commit();
    }

    private void initSearch() {
        fragmentStaffBinding.imgStaffSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideStaffTitleAndShowStaffSearch();
            }
        });

        fragmentStaffBinding.edtStaffSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    hideStaffSearchAndShowStaffTitle();
                }
                loadData(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void hideStaffTitleAndShowStaffSearch() {
        fragmentStaffBinding.tvStaffTitle.setVisibility(View.GONE);
        fragmentStaffBinding.edtStaffSearch.setVisibility(View.VISIBLE);
    }

    private void hideStaffSearchAndShowStaffTitle() {
        fragmentStaffBinding.tvStaffTitle.setVisibility(View.VISIBLE);
        fragmentStaffBinding.edtStaffSearch.setVisibility(View.GONE);
    }

    private void initRecyclerView() {
        fragmentStaffBinding.rcvStaff.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        fragmentStaffBinding.rcvStaff.setAdapter(staffAdapter);
    }

    private void initAdapter() {
        staffs = new ArrayList<>();
        staffAdapter = new StaffAdapter(staffs, new IOnManagerStaffListener() {
            @Override
            public void onClickUpdateStaff(NhanVien nhanVien) {
                goToAddFragment(nhanVien);
            }

            @Override
            public void onClickDeleteStaff(NhanVien nhanVien) {
                deleteStaffByIDStaff(nhanVien.getMaNV());
            }
        });
    }

    private void deleteStaffByIDStaff(String maNV) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("Staff").child(maNV).removeValue();


    }

    public void loadData(String key) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Staff");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                staffs.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    NhanVien nhanVien = snap.getValue(NhanVien.class);
                    if (nhanVien.getHoTen().contains(key)) {
                        staffs.add(nhanVien);
                    }
                }
                staffAdapter.setData(staffs);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentStaffBinding = FragmentStaffBinding.inflate(inflater, container, false);
        return fragmentStaffBinding.getRoot();
    }
}