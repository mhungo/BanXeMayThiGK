package vn.edu.stu.thigkbanxemay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

    Vehical xeDuocChon;
    int poss;


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

        DuLieu.getAllXe();

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
                timTheoPhanLoai(chon.getMaloai() + "");
            }
        });

        lvPhanLoaiSP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int pos = i;
                xeDuocChon = dsXeMay.get(i);
                final int posy = DuLieu.dsXeMay.indexOf(xeDuocChon);

                if (pos >= 0 && pos < dsXeMay.size()) {
                    Intent intent = new Intent(PhanLoaiActivity.this, ChiTietXeMayActivity.class);
                    intent.putExtra("XE", posy);
                    startActivity(intent);
                }
            }
        });

        lvPhanLoaiSP.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final int pos = i;

                xeDuocChon = dsXeMay.get(i);
                for (Vehical vehical : DuLieu.dsXeMay) {
                    if (vehical.getMa() == xeDuocChon.getMa()) {
                        poss = DuLieu.dsXeMay.indexOf(vehical);
                    }
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(PhanLoaiActivity.this);

                builder.setTitle(R.string.txt_message_title_CanhBao).setMessage(R.string.txt_message_title_BanCoMuonXoaKhong);
                builder.setPositiveButton(R.string.txt_nagative_Sua, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (pos >= 0 && pos < DuLieu.getDsXeMay().size()) {
                            Intent intent = new Intent(PhanLoaiActivity.this, ThemSuaXeMayActivity.class);
                            intent.putExtra("XEUPDATE", poss);
                            startActivity(intent);
                        }
                    }
                });

                builder.setNegativeButton(R.string.txt_nagative_Xoa, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(PhanLoaiActivity.this);

                        builder.setTitle(R.string.txt_message_title_CanhBao).setMessage(R.string.txt_message_title_BanCoMuonXoaKhong);
                        builder.setPositiveButton(R.string.txt_nagative_btn_Yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SQLiteDatabase database = DBConfigUtil.getDatabase();
                                database.delete("Vehical", "ma =?", new String[]{dsXeMay.get(pos).getMa() + ""});
                                timTheoPhanLoai(chon.getMaloai() + "");
                            }
                        });

                        builder.setNegativeButton(R.string.txt_nagative_btn_No, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();

                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                return true;
            }
        });
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
                Intent intent0 = new Intent(PhanLoaiActivity.this, MainActivity.class);
                startActivity(intent0);
                break;
            case R.id.mnuQuanLyPhanLoai:
                Intent intent1 = new Intent(PhanLoaiActivity.this, QuanLyPhanLoaiActivity.class);
                startActivity(intent1);
                break;
            case R.id.mnuPhanLoai:
                Toast.makeText(this, R.string.txt_message_ActivityPhanLoai, Toast.LENGTH_SHORT).show();
                break;
            case R.id.mnuThongTinUngDung:
                Intent intent2 = new Intent(PhanLoaiActivity.this, AboutActivity.class);
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
        if(chon!= null){
            timTheoPhanLoai(chon.getMaloai()+"");
        }
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