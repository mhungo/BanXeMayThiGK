package vn.edu.stu.thigkbanxemay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import vn.edu.stu.Model.Classify;
import vn.edu.stu.Model.Vehical;
import vn.edu.stu.Util.DBConfigUtil;
import vn.edu.stu.Util.DuLieu;

public class ChiTietXeMayActivity extends AppCompatActivity {

    ImageView imageViewInfoChiTietXeMay;
    TextView txtid, txtname, txtphanloai, txtgia, txtmota;
    Button btnBackHome;

    ArrayList<Classify> dsPhanLoai;
    ArrayList<Vehical> dsXeMay;
    Vehical chon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_xe_may);

        imageViewInfoChiTietXeMay = findViewById(R.id.imgInfoVehical);
        txtid = findViewById(R.id.txtChiTietmaxe);
        txtname = findViewById(R.id.txtChiTiettenxe);
        txtphanloai = findViewById(R.id.txtChiTietphanloai);
        txtgia = findViewById(R.id.txtChiTietGia);
        txtmota = findViewById(R.id.txtChiTietmota);
        btnBackHome = findViewById(R.id.btnbackHome);

        dsPhanLoai = new ArrayList<>();
        chon = null;

        DuLieu.getAllLoai();
        getIntendData();
        addEvents();

    }

    private void addEvents() {
        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiTietXeMayActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getIntendData() {
        Intent intent = getIntent();
        if (intent.hasExtra("XE")) {
            int pos = intent.getIntExtra("XE", 1);
            chon = DuLieu.getDsXeMay().get(pos);
            if (chon != null) {

                byte[] imageVehical = chon.getHinh();
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageVehical, 0, imageVehical.length);
                imageViewInfoChiTietXeMay.setImageBitmap(bitmap);

                txtid.setText(chon.getMa() + "");
                txtname.setText(chon.getTen());

                for (Classify classify : DuLieu.getDsLoai()) {
                    if (classify.getMaloai() == chon.getMaloai()) {
                        txtphanloai.setText(classify.getTenloai());
                    }
                }
                txtgia.setText(chon.getGia());
                txtmota.setText(chon.getMota());

            } else {

            }
        }
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

    private void hienthiDanhSach() {
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuTRangChu:
                Intent intent0 = new Intent(ChiTietXeMayActivity.this, MainActivity.class);
                startActivity(intent0);
                break;
            case R.id.mnuQuanLyPhanLoai:
                Intent intent2 = new Intent(ChiTietXeMayActivity.this, QuanLyPhanLoaiActivity.class);
                startActivity(intent2);
                break;
            case R.id.mnuPhanLoai:
                Intent intent1 = new Intent(ChiTietXeMayActivity.this, PhanLoaiActivity.class);
                startActivity(intent1);
                break;
            case R.id.mnuThongTinUngDung:
                Intent intent3 = new Intent(ChiTietXeMayActivity.this, AboutActivity.class);
                startActivity(intent3);
                break;
            case R.id.mnuThoat:
                finish();
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}