package com.example.se114n21.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.se114n21.Models.Account;
import com.example.se114n21.Models.NhanVien;
import com.example.se114n21.R;
import com.example.se114n21.databinding.FragmentAddStaffBinding;
import com.example.se114n21.utils.GlideUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;
import java.util.UUID;

public class AddOrEditStaffFragment extends BaseFragment {
    FragmentAddStaffBinding addOrEditStaffFragment;
    Uri uri;
    NhanVien nhanVien;
    private String[] arr = new String[2];

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        uri = data.getData();
                        addOrEditStaffFragment.avatar.setImageURI(uri);
                    } else {
                        Toast.makeText(getContext(), "No Image Selected", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //get data from StaffFragment
        Bundle bundle = getArguments();

        if (bundle != null) {
            nhanVien = (NhanVien) bundle.getSerializable("staff");
        }

        arr[0]= getResources().getString(R.string.staff);
        arr[1]= getResources().getString(R.string.admin);

        initEvents();
        initSpinner();
        setData(nhanVien);
    }

    private void initSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, arr);
        addOrEditStaffFragment.spnTypeStaff.setAdapter(adapter);
    }

    private void setData(NhanVien nhanVien) {
        if (nhanVien != null) {
            addOrEditStaffFragment.txtHoTen.setText(nhanVien.getHoTen());
            addOrEditStaffFragment.txtEmail.setText(nhanVien.getEmail());
            addOrEditStaffFragment.tvAddStaffAddress.setText(nhanVien.getDiaChi());
            addOrEditStaffFragment.titleAddOrUpdateStaff.setText(getResources().getText(R.string.update_staff));
            addOrEditStaffFragment.txtEmail.setEnabled(false);
            GlideUtils.loadUrl(nhanVien.getLinkAvt(), addOrEditStaffFragment.avatar);
        }
    }

    //oke. nếu bạn mệt r thì để mai cũngdđược. ti tui sợ bạn có việc khác á, ok 3 phút
    private void initEvents() {
        // add staff
        addOrEditStaffFragment.imgAddStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOrUpdateStaff();
            }
        });

        //select image staff
        addOrEditStaffFragment.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  openGallery();
            }
        });

        //back to staff fragment
        addOrEditStaffFragment.butBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToStaffFragment();
            }
        });
    }

    private void openGallery() {
        Intent photoPicker = new Intent(Intent.ACTION_PICK);
        photoPicker.setType("image/*");
        activityResultLauncher.launch(photoPicker);
    }

    private boolean isNameEmpty(){
        return addOrEditStaffFragment.txtHoTen.getText().toString().isEmpty();
    }

    private boolean isEmaimEmpty(){
        return addOrEditStaffFragment.txtEmail.getText().toString().isEmpty();
    }


    private boolean isAddressEmpty(){
        return addOrEditStaffFragment.tvAddStaffAddress.getText().toString().isEmpty();
    }

    private void addOrUpdateStaff() {

        if (isNameEmpty()){
            showMessage("Ten khong duoc trong");
            return;
        }

        if (isEmaimEmpty()){
            showMessage("Email khong duoc trong");
            return;
        }

        if (isAddressEmpty()){
            showMessage("Dia chia khong duoc trong");
            return;
        }

        showProgressDialog(true);

        if (nhanVien != null) {
            updateStaff();
            return;
        }

                NhanVien nhanVien = getDataStaff();
                createAuthUser(nhanVien);

                FirebaseDatabase.getInstance().getReference("Staff").child(nhanVien.getMaNV())
                        .setValue(nhanVien).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    showProgressDialog(false);
                                    showMessage("Successful");
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                showProgressDialog(false);
                                showMessage(e.getMessage());
                            }
                        });
            }

    private void createAuthUser(NhanVien nhanVien ) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(nhanVien.getEmail(), nhanVien.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                sendMessageChangePassword(nhanVien.getEmail());
            }
        });
    }

    private void sendMessageChangePassword(String email){
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            showMessage(requireActivity().getString(R.string.send_email_change_password));
                            goToStaffFragment();
                        }
                    }
                });
    }

    private void updateStaff() {
        NhanVien nhanVien = getDataStaff();
        nhanVien.setLinkAvt(this.nhanVien.getLinkAvt());

        FirebaseDatabase.getInstance().getReference("Staff").child(nhanVien.getMaNV())
                .setValue(nhanVien).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            showProgressDialog(false);
                            showMessage("Successful");
                            goToStaffFragment();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showProgressDialog(false);
                        showMessage(e.getMessage());
                    }
                });
    }

    private void goToStaffFragment() {
        getParentFragmentManager().popBackStack();
    }


    private NhanVien getDataStaff() {
        NhanVien nhanVien = new NhanVien();
        if (this.nhanVien != null) {
            nhanVien.setMaNV(this.nhanVien.getMaNV());
        } else {
            nhanVien.setMaNV(UUID.randomUUID().toString());
        }
        nhanVien.setHoTen(addOrEditStaffFragment.txtHoTen.getText().toString());
        nhanVien.setEmail(addOrEditStaffFragment.txtEmail.getText().toString());
        nhanVien.setDiaChi(addOrEditStaffFragment.tvAddStaffAddress.getText().toString());

        //set loai tai khoan:
        //type = 1 la admin
        //type = 2 la nhan vien
        int type = 1;
        if (addOrEditStaffFragment.spnTypeStaff.getSelectedItem().toString().equals(getResources().getString(R.string.admin))) {
            type = 1;
        }  else {
            type = 2;
        }
        nhanVien.setLoaiNhanVien(type);
        nhanVien.setPassword("12345678");
        return nhanVien;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        addOrEditStaffFragment = FragmentAddStaffBinding.inflate(inflater, container, false);
        return addOrEditStaffFragment.getRoot();
    }
}