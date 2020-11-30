package vn.edu.stu.thigkbanxemay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import vn.edu.stu.Model.Vehical;
import vn.edu.stu.Util.DBConfigUtil;
import vn.edu.stu.Util.DuLieu;
import vn.edu.stu.adapter.VehicalAdapter;

public class MainActivity extends AppCompatActivity {

    ListView lvDanhSachDienThoai;
    FloatingActionButton fabThem;
    VehicalAdapter adapter;
    Vehical chon;
    int requestCode = 113, resultCode = 115;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        hienthiDanhSach();
        addEvents();
    }

    private void addEvents() {
        fabThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ThemSuaXeMayActivity.class);
                startActivity(intent);
            }
        });

        lvDanhSachDienThoai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                final int pos = i;
                chon = DuLieu.getDsXeMay().get(i);

                builder.setTitle(R.string.txt_message_title_TuyChon).setMessage(R.string.txt_message_title_MoiChonChucNangSau);

                builder.setPositiveButton(R.string.txt_nagative_XemChiTiet, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (pos >= 0 && pos < DuLieu.getDsXeMay().size()) {
                            Intent intent = new Intent(MainActivity.this, ChiTietXeMayActivity.class);
                            intent.putExtra("XE", pos);
                            startActivity(intent);
                        }
                    }
                });

                builder.setNegativeButton(R.string.txt_nagative_Sua, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (pos >= 0 && pos < DuLieu.getDsXeMay().size()) {
                            Intent intent = new Intent(MainActivity.this, ThemSuaXeMayActivity.class);
                            intent.putExtra("XEUPDATE", pos);
                            startActivity(intent);
                        }
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        lvDanhSachDienThoai.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                final int pos = i;

                builder.setTitle(R.string.txt_message_title_CanhBao).setMessage(R.string.txt_message_title_BanCoMuonXoaKhong);
                builder.setPositiveButton(R.string.txt_nagative_btn_Yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SQLiteDatabase database = DBConfigUtil.getDatabase();
                        database.delete("Vehical", "ma =?", new String[]{DuLieu.getDsXeMay().get(i).getMa() + ""});
                        hienthiDanhSach();
                    }
                });

                builder.setNegativeButton(R.string.txt_nagative_btn_No, new DialogInterface.OnClickListener() {
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

    private void hienthiDanhSach() {
        DuLieu.getAllXe();
        adapter.notifyDataSetChanged();
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
                Toast.makeText(this, R.string.txt_message_ActivityTrangChu, Toast.LENGTH_SHORT).show();
                break;
            case R.id.mnuQuanLyPhanLoai:
                Intent intent = new Intent(MainActivity.this, QuanLyPhanLoaiActivity.class);
                startActivity(intent);
                break;
            case R.id.mnuPhanLoai:
                Intent intent1 = new Intent(MainActivity.this, PhanLoaiActivity.class);
                startActivity(intent1);
                break;
            case R.id.mnuThongTinUngDung:
                Intent intent2 = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent2);
                break;
            case R.id.mnuThoat:
                finish();
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hienthiDanhSach();
    }

    private void addControls() {
        lvDanhSachDienThoai = findViewById(R.id.lvDienThoai);
        fabThem = findViewById(R.id.fabAdd);

        adapter = new VehicalAdapter(MainActivity.this, R.layout.item_vehical, DuLieu.getDsXeMay());
        lvDanhSachDienThoai.setAdapter(adapter);
        chon = null;
    }

}