package vn.edu.stu.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import vn.edu.stu.Model.Classify;
import vn.edu.stu.Model.Vehical;
import vn.edu.stu.Util.DBConfigUtil;
import vn.edu.stu.thigkbansmartphone.R;

public class VehicalAdapter extends ArrayAdapter<Vehical> {
    Activity context;
    int resource;
    List<Vehical> objects;

    ArrayList<Classify> dsPhanLoai = new ArrayList<>();

    public VehicalAdapter(@NonNull Activity context, int resource, @NonNull List<Vehical> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    private void hienthiDanhSachLoai() {
        dsPhanLoai.clear();
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
            dsPhanLoai.add(classify);

        }
        cursor.close();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View view = inflater.inflate(this.resource, null);

        TextView txtTen = view.findViewById(R.id.txtTen);
        TextView txtGia = view.findViewById(R.id.txtGia);
        TextView txtPhanLoai = view.findViewById(R.id.txtPhanLoai);
        ImageView imgVehical = view.findViewById(R.id.imgVehical);

        hienthiDanhSachLoai();

        Vehical vehical = this.objects.get(position);

        txtTen.setText(vehical.getTen());

        for (Classify classify: dsPhanLoai) {
            if(classify.getMaloai() == vehical.getMaloai()){
                txtPhanLoai.setText(classify.getTenloai() + "");
            }
        }

        txtGia.setText(vehical.getGia());

        byte[] imageVehical = vehical.getHinh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageVehical, 0, imageVehical.length);
        imgVehical.setImageBitmap(bitmap);

        return view;
    }
}
