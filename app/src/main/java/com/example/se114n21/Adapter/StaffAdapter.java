package com.example.se114n21.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se114n21.Models.NhanVien;
import com.example.se114n21.databinding.ItemStaffBinding;
import com.example.se114n21.listener.IOnManagerStaffListener;
import com.example.se114n21.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.StaffViewHolder> {

    private List<NhanVien> staffs;
    public final IOnManagerStaffListener IOnManagerStaffListener;

    public StaffAdapter(List<NhanVien> staffs, IOnManagerStaffListener listener) {
        this.staffs = staffs;
        this.IOnManagerStaffListener = listener;
    }

    @NonNull
    @Override
    public StaffViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemStaffBinding itemStaffBinding = ItemStaffBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new StaffViewHolder(itemStaffBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull StaffViewHolder holder, int position) {
        NhanVien staff = staffs.get(position);
        if (staff == null) {
            return;
        }

        holder.itemStaffBinding.tvStaffName.setText(staff.getHoTen());
        holder.itemStaffBinding.tvStaffPhone.setText(staff.getSDT());
        holder.itemStaffBinding.tvStaffEmail.setText(staff.getEmail());
        holder.itemStaffBinding.tvStaffAddress.setText(staff.getDiaChi());

        if (staff.getLoaiNhanVien() == 1){
            holder.itemStaffBinding.tvStaffPhone.setText("Admin");
        }else{
            holder.itemStaffBinding.tvStaffPhone.setText("Nhan Vien");
        }

        holder.itemStaffBinding.imgEdit.setOnClickListener(v -> IOnManagerStaffListener.onClickUpdateStaff(staff));
        holder.itemStaffBinding.imgDelete.setOnClickListener(v -> IOnManagerStaffListener.onClickDeleteStaff(staff));
    }

    @Override
    public int getItemCount() {
        return null == staffs ? 0 : staffs.size();
    }

    public void setData(List<NhanVien> staffs) {
        if (staffs == null)
            staffs = new ArrayList<>();

        this.staffs = staffs;
        notifyDataSetChanged();
    }

    public static class StaffViewHolder extends RecyclerView.ViewHolder {

        private final ItemStaffBinding itemStaffBinding;

        public StaffViewHolder(ItemStaffBinding itemStaffBinding) {
            super(itemStaffBinding.getRoot());
            this.itemStaffBinding = itemStaffBinding;
        }
    }
}
