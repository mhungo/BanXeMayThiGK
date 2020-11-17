package vn.edu.stu.thigkbansmartphone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import vn.edu.stu.Model.Classify;
import vn.edu.stu.Model.Vehical;
import vn.edu.stu.Util.DBConfigUtil;
import vn.edu.stu.Util.DuLieu;

public class ThemSuaXeMayActivity extends AppCompatActivity {

    ImageView imgInfoVehical;
    TextView txtma;
    EditText txtten, txtgia, txtmota;
    Spinner spnphanloai;
    Button btnLuu;
    ArrayAdapter<Classify> adapterClassify;
    Classify chonSpn;
    Vehical chon;

    int resultCode = 115;

    final int REQUEST_CODE_GALLERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sua_xe_may);

        addControls();
        hienthiDanhSachLoai();
        getIntenData();
        addEvent();
    }

    private void getIntenData() {
        Intent intent = getIntent();
        if (intent.hasExtra("XEUPDATE")) {
            int pos = intent.getIntExtra("XEUPDATE", 1);
            chon = DuLieu.getDsXeMay().get(pos);
            if (chon != null) {
                byte[] imageVehical = chon.getHinh();
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageVehical, 0, imageVehical.length);
                imgInfoVehical.setImageBitmap(bitmap);

                txtten.setText(chon.getTen());

                for (Classify classify : DuLieu.getDsLoai()) {
                    if (classify.getMaloai() == chon.getMaloai()) {
                        spnphanloai.setSelection(adapterClassify.getPosition(classify));
                    }
                }
                txtgia.setText(chon.getGia());
                txtmota.setText(chon.getMota());

            } else {

            }
        }
    }

    private void addEvent() {

        imgInfoVehical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), REQUEST_CODE_GALLERY);
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (TextUtils.isEmpty(txtten.getText()) || TextUtils.isEmpty(spnphanloai.getSelectedItem().toString()) || TextUtils.isEmpty(txtgia.getText())
                            || TextUtils.isEmpty(txtmota.getText())) {
                        Toast.makeText(ThemSuaXeMayActivity.this, "Không được trống", Toast.LENGTH_SHORT).show();

                    } else {
                        if (chon == null) {
                            chonSpn = (Classify) spnphanloai.getSelectedItem();
                            String ten = txtten.getText().toString();
                            int maloai = chonSpn.getMaloai();
                            String gia = txtgia.getText().toString();
                            String mota = txtmota.getText().toString();
                            DBConfigUtil.insertDataVehical(ten, maloai, gia, mota, imageViewToByte(imgInfoVehical));

                            Toast.makeText(ThemSuaXeMayActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();

                            txtten.setText("");
                            spnphanloai.setSelection(0);
                            txtgia.setText("");
                            txtmota.setText("");
                            imgInfoVehical.setImageResource(R.mipmap.ic_launcher);
                        }

                        chon.setTen(txtten.getText().toString());
                        int maPhanLoai = spnphanloai.getSelectedItemPosition();
                        chon.setMaloai(maPhanLoai);
                        chon.setGia(txtgia.getText().toString());
                        chon.setMota(txtmota.getText().toString());
                        chon.setHinh(imageViewToByte(imgInfoVehical));

                        String ten = txtten.getText().toString();
                        chonSpn = (Classify) spnphanloai.getSelectedItem();
                        int maloai = chonSpn.getMaloai();
                        String gia = txtgia.getText().toString();
                        String mota = txtmota.getText().toString();

                        DBConfigUtil.updateDataVehical(ten, maloai, gia, mota, imageViewToByte(imgInfoVehical), chon.getMa() + "");
                        Toast.makeText(ThemSuaXeMayActivity.this, "Đã sửa thành công", Toast.LENGTH_SHORT).show();

                        txtten.setText("");
                        spnphanloai.setSelection(0);
                        txtgia.setText("");
                        txtmota.setText("");
                        imgInfoVehical.setImageResource(R.mipmap.ic_launcher);

                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void hienthiDanhSachLoai() {
        DuLieu.getAllLoai();
        adapterClassify.notifyDataSetChanged();
    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(this, "Bạn chưa cấp quyền truy cập ảnh", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgInfoVehical.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void addControls() {
        imgInfoVehical = findViewById(R.id.imgInfoVehical);
        txtma = findViewById(R.id.txtmaxe);
        txtten = findViewById(R.id.txttenxe);
        txtgia = findViewById(R.id.txtGia);
        txtmota = findViewById(R.id.txtmota);
        spnphanloai = findViewById(R.id.spnphanloai);
        btnLuu = findViewById(R.id.btnLuu);

        adapterClassify = new ArrayAdapter<>(ThemSuaXeMayActivity.this, android.R.layout.simple_spinner_item, DuLieu.getDsLoai());
        adapterClassify.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnphanloai.setAdapter(adapterClassify);
        chon = null;

    }
}