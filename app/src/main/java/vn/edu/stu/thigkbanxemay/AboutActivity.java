package vn.edu.stu.thigkbanxemay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AboutActivity extends AppCompatActivity implements OnMapReadyCallback {

    TextView txtphone;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        txtphone = findViewById(R.id.txtphone);

        txtphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNum = txtphone.getText().toString();
                Intent intent = new Intent(
                        Intent.ACTION_DIAL,
                        Uri.parse("tel:" + phoneNum)
                );
                startActivity(intent);
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
                Intent intent0 = new Intent(AboutActivity.this, MainActivity.class);
                startActivity(intent0);
                break;
            case R.id.mnuQuanLyPhanLoai:
                Intent intent2 = new Intent(AboutActivity.this, QuanLyPhanLoaiActivity.class);
                startActivity(intent2);
                break;
            case R.id.mnuPhanLoai:
                Intent intent1 = new Intent(AboutActivity.this, PhanLoaiActivity.class);
                startActivity(intent1);
                break;
            case R.id.mnuThongTinUngDung:
                Toast.makeText(this, R.string.txt_message_ActivityThongTinCaNhan, Toast.LENGTH_SHORT).show();
                break;
            case R.id.mnuThoat:
                finish();
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng stu = new LatLng(10.738075332422126, 106.67787706780615);
        mMap.addMarker(new MarkerOptions().position(stu).title("STU"));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(stu, 18));

    }
}