package vn.edu.stu.thigkbansmartphone;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import vn.edu.stu.Model.Classify;
import vn.edu.stu.Model.Vehical;
import vn.edu.stu.Util.DBConfigUtil;
import vn.edu.stu.Util.DuLieu;
import vn.edu.stu.adapter.VehicalAdapter;

public class PhanLoaiActivity extends AppCompatActivity {

    Spinner spnPhanLoaiSP;
    ListView lvPhanLoaiSP;
    Button btnGo;
    Classify chon;

    ArrayList<Vehical> dsXeMay = new ArrayList<>();
    ArrayAdapter<Classify> classifyAdapter;
    VehicalAdapter vehicalAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phan_loai);

        addControls();
        hienthiDanhSachLoai();
        addEvents();
    }

    private void hienthiDanhSachLoai() {
        DuLieu.getAllLoai();
        classifyAdapter.notifyDataSetChanged();
    }

    public void timTheoPhanLoai(String idLoai) {
        dsXeMay.clear();
        vehicalAdapter.clear();

        SQLiteDatabase database = DBConfigUtil.getDatabase();
        Cursor cursor = database.rawQuery("select * from Vehical where maloai=" + idLoai, null);

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
        vehicalAdapter.notifyDataSetChanged();

    }

    private void addEvents() {
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chon = (Classify) spnPhanLoaiSP.getSelectedItem();
                Toast.makeText(PhanLoaiActivity.this, chon.getTenloai(), Toast.LENGTH_SHORT).show();

                timTheoPhanLoai(chon.getMaloai() + "");
                chon = null;
            }
        });
    }

    private void addControls() {
        spnPhanLoaiSP = findViewById(R.id.spnPhanLoai);
        btnGo = findViewById(R.id.btnGO);
        lvPhanLoaiSP = findViewById(R.id.lvPhanLoaiSP);


        classifyAdapter = new ArrayAdapter<>(PhanLoaiActivity.this, android.R.layout.simple_spinner_item, DuLieu.getDsLoai());
        classifyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPhanLoaiSP.setAdapter(classifyAdapter);

        vehicalAdapter = new VehicalAdapter(PhanLoaiActivity.this, R.layout.item_vehical, dsXeMay);
        lvPhanLoaiSP.setAdapter(vehicalAdapter);


    }
}