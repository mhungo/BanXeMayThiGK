package vn.edu.stu.thigkbansmartphone;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import vn.edu.stu.Model.Classify;
import vn.edu.stu.Model.Vehical;
import vn.edu.stu.Util.DBConfigUtil;
import vn.edu.stu.adapter.ClassifyAdapter;

public class QuanLyPhanLoaiActivity extends AppCompatActivity {

    ListView lvPhanloai;
    EditText txtTenPhanLoai;
    Button btnSave;
    Classify chon;

    ArrayList<Classify> dsPhanLoai;
    ClassifyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_phan_loai);

        addControls();
        hienthiDanhSachLoai();
        addEvents();
    }

    private void addEvents() {

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (TextUtils.isEmpty(txtTenPhanLoai.getText())) {
                        Toast.makeText(QuanLyPhanLoaiActivity.this, "Không được trống", Toast.LENGTH_SHORT).show();
                    } else {
                        String ten = txtTenPhanLoai.getText().toString();
                        DBConfigUtil.insertDataClassify(ten);

                        Toast.makeText(QuanLyPhanLoaiActivity.this, "Thêm loại thành công", Toast.LENGTH_SHORT).show();
                        txtTenPhanLoai.setText("");

                        hienthiDanhSachLoai();
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        lvPhanloai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                chon = dsPhanLoai.get(i);
                txtTenPhanLoai.setText(chon.getTenloai());

            }
        });

        lvPhanloai.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(QuanLyPhanLoaiActivity.this);

                final int pos = i;

                builder.setTitle("Cảnh báo").setMessage("Bạn có chắc muốn xóa không?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SQLiteDatabase database = DBConfigUtil.getDatabase();
                        database.delete("Classify", "maloai =?", new String[]{dsPhanLoai.get(pos).getMaloai() + ""});

                        hienthiDanhSachLoai();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
                return true;

            }
        });
    }

    private void hienthiDanhSachLoai() {
        dsPhanLoai.clear();
        adapter.clear();
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
        adapter.notifyDataSetChanged();
    }

    private void addControls() {
        txtTenPhanLoai = findViewById(R.id.txtTenLoai);
        btnSave = findViewById(R.id.btnSave);
        lvPhanloai = findViewById(R.id.lvPhanLoai);

        dsPhanLoai = new ArrayList<>();
        adapter = new ClassifyAdapter(QuanLyPhanLoaiActivity.this, R.layout.item_classify, dsPhanLoai);
        lvPhanloai.setAdapter(adapter);

    }
}