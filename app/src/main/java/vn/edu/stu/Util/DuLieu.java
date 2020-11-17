package vn.edu.stu.Util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import vn.edu.stu.Model.Classify;
import vn.edu.stu.Model.Vehical;

public class DuLieu {
    public static ArrayList<Vehical> dsXeMay;
    public static ArrayList<Classify> dsLoai;

    public static ArrayList<Vehical> getDsXeMay() {
        return dsXeMay;
    }

    public static ArrayList<Classify> getDsLoai() {
        return dsLoai;
    }

    static {
        dsLoai = new ArrayList<>();
        dsXeMay = new ArrayList<>();
    }

    public static void getAllXe() {
        dsXeMay.clear();
        SQLiteDatabase database = DBConfigUtil.getDatabase();
        Cursor cursor = database.query(
                "Vehical",
                null,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            int ma = cursor.getInt(0);
            String ten = cursor.getString(1);
            int maloai = cursor.getInt(2);
            String gia = cursor.getString(3);
            String mota = cursor.getString(4);
            byte[] hinh = cursor.getBlob(5);

            Vehical vehical = new Vehical(ma, ten, maloai, gia, mota, hinh);
            dsXeMay.add(vehical);

        }
        cursor.close();
    }

    public static void getAllLoai() {
        dsLoai.clear();
        SQLiteDatabase database = DBConfigUtil.getDatabase();
        Cursor cursor = database.query(
                "Classify",
                null,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            int maloai = cursor.getInt(0);
            String tenloai = cursor.getString(1);

            Classify classify = new Classify(maloai, tenloai);
            dsLoai.add(classify);

        }
        cursor.close();
    }
}
