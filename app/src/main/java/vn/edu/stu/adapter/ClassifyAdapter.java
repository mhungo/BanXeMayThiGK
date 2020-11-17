package vn.edu.stu.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import vn.edu.stu.Model.Classify;
import vn.edu.stu.thigkbansmartphone.R;

public class ClassifyAdapter extends ArrayAdapter<Classify> {
    Activity context;
    int resource;
    List<Classify> objects;


    public ClassifyAdapter(@NonNull Activity context, int resource, @NonNull List<Classify> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View view = inflater.inflate(this.resource, null);

        TextView textviewMaLoai = view.findViewById(R.id.textviewMaLoai);
        TextView textviewTenLoai = view.findViewById(R.id.textviewTenLoai);

        Classify classify = this.objects.get(position);

        textviewMaLoai.setText(classify.getMaloai()+"");
        textviewTenLoai.setText(classify.getTenloai());

        return view;
    }
}
