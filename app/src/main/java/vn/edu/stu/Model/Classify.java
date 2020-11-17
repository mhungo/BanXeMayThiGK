package vn.edu.stu.Model;

import java.util.Objects;

public class Classify {
    private int maloai;
    private String tenloai;

    public Classify(int maloai, String tenloai) {
        this.maloai = maloai;
        this.tenloai = tenloai;
    }

    public Classify() {
    }

    public int getMaloai() {
        return maloai;
    }

    public void setMaloai(int maloai) {
        this.maloai = maloai;
    }

    public String getTenloai() {
        return tenloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }

    @Override
    public String toString() {
        return tenloai;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Classify classify = (Classify) o;
        return maloai == classify.maloai;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maloai);
    }
}
