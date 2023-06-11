package com.example.se114n21.Models;

import java.io.Serializable;
import java.util.List;
public class NhanVien implements Serializable {
    private String LinkAvt;
    private String MaNV;
    private String HoTen;
    private String NgaySinh;
    private String GioiTinh;
    private String DiaChi;
    private String SDT;
    private String Email;
    private String NgayVaoLam;
    private Double LuongTheoGio;

    private String password;
    private int loaiNhanVien;

    private List<String> DanhSachCaLam;

    public NhanVien() {
    }

    public NhanVien(String linkAvt, String maNV, String hoten, String ngaySinh, String gioiTinh, String diaChi, String sdt, String email, String ngayVaoLam, Double luongTheoGio, List<String> danhSachCaLam) {
        LinkAvt = linkAvt;
        MaNV = maNV;
        HoTen = hoten;
        NgaySinh = ngaySinh;
        GioiTinh = gioiTinh;
        DiaChi = diaChi;
        SDT = sdt;
        Email = email;
        NgayVaoLam = ngayVaoLam;
        LuongTheoGio = luongTheoGio;
        DanhSachCaLam = danhSachCaLam;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLoaiNhanVien() {
        return loaiNhanVien;
    }

    public void setLoaiNhanVien(int loaiNhanVien) {
        this.loaiNhanVien = loaiNhanVien;
    }

    public String getLinkAvt() {
        return LinkAvt;
    }

    public void setLinkAvt(String linkAvt) {
        LinkAvt = linkAvt;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String maNV) {
        MaNV = maNV;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoten) {
        HoTen = hoten;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        NgaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        GioiTinh = gioiTinh;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String sdt) {
        SDT = sdt;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNgayVaoLam() {
        return NgayVaoLam;
    }

    public void setNgayVaoLam(String ngayVaoLam) {
        NgayVaoLam = ngayVaoLam;
    }

    public Double getLuongTheoGio() {
        return LuongTheoGio;
    }

    public void setLuongTheoGio(Double luongTheoGio) {
        LuongTheoGio = luongTheoGio;
    }

    public List<String> getDanhSachCaLam() {
        return DanhSachCaLam;
    }

    public void setDanhSachCaLam(List<String> danhSachCaLam) {
        DanhSachCaLam = danhSachCaLam;
    }

    @Override
    public String toString() {
        return "NhanVien{" +
                "LinkAvt='" + LinkAvt + '\'' +
                ", MaNV='" + MaNV + '\'' +
                ", HoTen='" + HoTen + '\'' +
                ", NgaySinh='" + NgaySinh + '\'' +
                ", GioiTinh='" + GioiTinh + '\'' +
                ", DiaChi='" + DiaChi + '\'' +
                ", SDT='" + SDT + '\'' +
                ", Email='" + Email + '\'' +
                ", NgayVaoLam='" + NgayVaoLam + '\'' +
                ", LuongTheoGio=" + LuongTheoGio +
                ", DanhSachCaLam=" + DanhSachCaLam +
                '}';
    }
}
