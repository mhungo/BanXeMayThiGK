package vn.edu.stu.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class DBConfigUtil {
    final static String DATABASE_NAME = "XeMayDB.sqlite";
    final static String DB_PATH_SUFFIX = "/databases/";

    static SQLiteDatabase database = null;

    public static SQLiteDatabase getDatabase() {
        if (database == null) {
            Context context = GlobalApplication.getAppContext();
            copyDatabaseFromAssets(context, DB_PATH_SUFFIX, DATABASE_NAME);
            database = context.openOrCreateDatabase(DATABASE_NAME, context.MODE_PRIVATE, null);
        }
        return database;
    }

    public static void copyDatabaseFromAssets(Context context, String DB_PATH_SUFFIX, String DATABASE_NAME) {
        File dbFile = context.getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists()) {
            File dbDir = new File(context.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!dbDir.exists())
                dbDir.mkdir();

            InputStream is = null;
            OutputStream os = null;

            try {
                is = context.getAssets().open(DATABASE_NAME);
                String outputFilePath = context.getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
                os = new FileOutputStream(outputFilePath);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }

                os.flush();
                Toast.makeText(context, "Da chep CSDL thanh cong", Toast.LENGTH_SHORT).show();

            } catch (Exception ex) {
                Toast.makeText(context, ex.toString(), Toast.LENGTH_SHORT).show();
            } finally {
                try {
                    if (os != null)
                        os.close();
                } catch (IOException e) {
                }
                try {
                    if (is != null)
                        is.close();
                } catch (IOException e) {
                }
            }
        }
    }

    //Them, Sua, Xoa Vehical
    public static void insertDataVehical(String ten, int maloai, String gia, String mota, byte[] hinh) {
        String sql = "INSERT INTO Vehical VALUES (NULL, ?, ?, ?, ?, ?)";

        SQLiteDatabase database = getDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("ten", ten);
        contentValues.put("maloai", maloai);
        contentValues.put("gia", gia);
        contentValues.put("mota", mota);
        contentValues.put("hinh", hinh);

        long returnValue = database.insert("Vehical", null, contentValues);
        if (returnValue < 0) {
            Log.e("ERR", "them that bai");
        }

    }

    public static void updateDataVehical(String ten, int maloai, String gia, String mota, byte[] hinh, String ma) {

        SQLiteDatabase database = getDatabase();

        ContentValues row = new ContentValues();
        row.put("ten", ten);
        row.put("maloai", maloai);
        row.put("gia", gia);
        row.put("mota", mota);
        row.put("hinh", hinh);

        int updatedRowCount = database.update(
                "Vehical",
                row,
                "ma = ?",
                new String[]{ma}
        );
    }

    public static void deleteData(String ma) {
        SQLiteDatabase database = getDatabase();

        int deleteRowCount = database.delete(
                "Sach_TBL",
                "ma = ?",
                new String[]{ma}
        );
    }

    //Them, Sua, Xoa Classify
    public static void insertDataClassify(String ten) {
        String sql = "INSERT INTO Classify VALUES (NULL, ?)";

        SQLiteDatabase database = getDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("tenloai", ten);

        long returnValue = database.insert("Classify", null, contentValues);
        if (returnValue < 0) {
            Log.e("ERR", "them that bai");
        }
    }

    public static void updateDataClassify(String ten, String ma) {

        SQLiteDatabase database = getDatabase();

        ContentValues row = new ContentValues();
        row.put("tenloai", ten);

        int updatedRowCount = database.update(
                "Classify",
                row,
                "maloai = ?",
                new String[]{ma}
        );

    }

}