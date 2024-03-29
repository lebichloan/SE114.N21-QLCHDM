package com.example.se114n21.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se114n21.Interface.TonKhoInterface;
import com.example.se114n21.Models.ItemBaoCao;
import com.example.se114n21.Models.KhachHang;
import com.example.se114n21.Models.SanPham;
import com.example.se114n21.R;

import java.util.ArrayList;
import java.util.List;

public class TonKhoAdapter extends RecyclerView.Adapter<TonKhoAdapter.TonKhoViewHolder> implements Filterable {

    private List<SanPham> mListSanPham;
    private List<SanPham> mListSanPhamOLD;
    private TonKhoInterface tonKhoInterface;

    public TonKhoAdapter(List<SanPham> mListSanPham, TonKhoInterface tonKhoInterface) {
        this.mListSanPham = mListSanPham;
        this.mListSanPhamOLD = mListSanPham;
        this.tonKhoInterface = tonKhoInterface;
    }

    @NonNull
    @Override
    public TonKhoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tonkho, parent, false);
        return new TonKhoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TonKhoViewHolder holder, int position) {
        SanPham sanPham = mListSanPham.get(position);
        if (sanPham == null) {
            return;
        }

        holder.masp.setText(sanPham.getMaSP());
        holder.tensp.setText(sanPham.getTenSP());
        holder.giavon.setText(String.valueOf(sanPham.getGiaNhap()));
        holder.soluong.setText(String.valueOf(sanPham.getSoLuong()));
        holder.tienvon.setText(String.valueOf(sanPham.getSoLuong() * sanPham.getGiaNhap()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tonKhoInterface.onClick(sanPham);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListSanPham != null) {
            return mListSanPham.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();

                if (strSearch.isEmpty()) {
                    mListSanPham = mListSanPhamOLD;
                }
                else {
                    List<SanPham> list = new ArrayList<>();
                    for (SanPham sanPham : mListSanPhamOLD) {
                        if (sanPham.getTenSP().toLowerCase().contains(strSearch.toLowerCase())
                                || sanPham.getMaSP().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(sanPham);
                        }
                    }

                    mListSanPham = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mListSanPham;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mListSanPham =  (List<SanPham>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class TonKhoViewHolder extends RecyclerView.ViewHolder {
        TextView masp, tensp, soluong, giavon, tienvon;
        public TonKhoViewHolder(@NonNull View itemView) {
            super(itemView);

            masp = itemView.findViewById(R.id.masp);
            tensp = itemView.findViewById(R.id.tensp);
            soluong = itemView.findViewById(R.id.soluong);
            giavon = itemView.findViewById(R.id.giavon);
            tienvon = itemView.findViewById(R.id.tienvon);
        }
    }
}
