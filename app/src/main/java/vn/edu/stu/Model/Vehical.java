package vn.edu.stu.Model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class Vehical implements Serializable {
    private int ma;
    private String ten;
    private int maloai;
    private String gia;
    private String mota;
    private byte[] hinh;


    public Vehical(int ma, String ten, int maloai, String gia, String mota, byte[] hinh) {
        this.ma = ma;
        this.ten = ten;
        this.maloai = maloai;
        this.gia = gia;
        this.mota = mota;
        this.hinh = hinh;
    }

    public Vehical() {
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getMaloai() {
        return maloai;
    }

    public void setMaloai(int maloai) {
        this.maloai = maloai;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public byte[] getHinh() {
        return hinh;
    }

    public void setHinh(byte[] hinh) {
        this.hinh = hinh;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehical vehical = (Vehical) o;
        return ma == vehical.ma &&
                maloai == vehical.maloai &&
                ten.equals(vehical.ten) &&
                gia.equals(vehical.gia) &&
                mota.equals(vehical.mota) &&
                Arrays.equals(hinh, vehical.hinh);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(ma, ten, maloai, gia, mota);
        result = 31 * result + Arrays.hashCode(hinh);
        return result;
    }
}
