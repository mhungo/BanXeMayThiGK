package vn.edu.stu.thigkbanxemay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import vn.edu.stu.Model.Classify;
import vn.edu.stu.Model.Vehical;
import vn.edu.stu.Util.DBConfigUtil;
import vn.edu.stu.Util.DuLieu;
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
                    if (chon != null) {
                        String tenloai = txtTenPhanLoai.getText().toString();
                        DBConfigUtil.updateDataClassify(tenloai, chon.getMaloai() + "");
                        hienthiDanhSachLoai();

                        txtTenPhanLoai.setText("");
                        chon = null;
                        Toast.makeText(QuanLyPhanLoaiActivity.this, R.string.txt_message_Dasuaxong, Toast.LENGTH_SHORT).show();

                    } else {
                        if (TextUtils.isEmpty(txtTenPhanLoai.getText())) {
                            Toast.makeText(QuanLyPhanLoaiActivity.this, R.string.txt_error_empty, Toast.LENGTH_SHORT).show();
                        } else {
                            String ten = txtTenPhanLoai.getText().toString();
                            DBConfigUtil.insertDataClassify(ten);
                            Toast.makeText(QuanLyPhanLoaiActivity.this, R.string.txt_message_LuuThanhCong, Toast.LENGTH_SHORT).show();
                            txtTenPhanLoai.setText("");
                            hienthiDanhSachLoai();
                        }
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        lvPhanloai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        lvPhanloai.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(QuanLyPhanLoaiActivity.this);
                final int pos = i;
                builder.setTitle(R.string.txt_message_title_TuyChon).setMessage(R.string.txt_message_title_MoiChonChucNangSau);
                builder.setPositiveButton(R.string.txt_nagative_Sua, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        chon = dsPhanLoai.get(i);
                        txtTenPhanLoai.setText(chon.getTenloai());
                    }
                });

                builder.setNegativeButton(R.string.txt_nagative_Xoa, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        chon = dsPhanLoai.get(pos);
                        if (!kiemtraVehical(chon)) {
                            Toast.makeText(QuanLyPhanLoaiActivity.this, R.string.txt_message_title_KhongDuocXoa, Toast.LENGTH_SHORT).show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(QuanLyPhanLoaiActivity.this);
                            builder.setTitle(R.string.txt_message_title_CanhBao).setMessage(R.string.txt_message_title_BanCoMuonXoaKhong);
                            builder.setPositiveButton(R.string.txt_nagative_btn_Yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    SQLiteDatabase database = DBConfigUtil.getDatabase();
                                    database.delete("Classify", "maloai =?", new String[]{dsPhanLoai.get(pos).getMaloai() + ""});
                                    hienthiDanhSachLoai();
                                }
                            });
                            builder.setNegativeButton(R.string.txt_nagative_btn_No, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });

                            AlertDialog alert = builder.create();
                            alert.show();
                        }

                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

                return true;
            }
        });
    }

    public boolean kiemtraVehical(Classify classify) {
        for (Vehical vehical : DuLieu.dsXeMay) {
            if (vehical.getMaloai() == classify.getMaloai()) {
                return false;
            }
        }
        return true;
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
                Intent intent0 = new Intent(QuanLyPhanLoaiActivity.this, MainActivity.class);
                startActivity(intent0);
                break;
            case R.id.mnuQuanLyPhanLoai:
                Toast.makeText(this, R.string.txt_message_ActivityQuanLyPhanLoai, Toast.LENGTH_SHORT).show();
                break;
            case R.id.mnuPhanLoai:
                Intent intent1 = new Intent(QuanLyPhanLoaiActivity.this, PhanLoaiActivity.class);
                startActivity(intent1);
                break;
            case R.id.mnuThongTinUngDung:
                Intent intent2 = new Intent(QuanLyPhanLoaiActivity.this, AboutActivity.class);
                startActivity(intent2);
                break;
            case R.id.mnuThoat:
                finish();
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
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
        chon = null;

    }
}