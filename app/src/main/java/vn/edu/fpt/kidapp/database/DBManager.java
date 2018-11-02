package vn.edu.fpt.kidapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import vn.edu.fpt.kidapp.model.CapturePicture;

public class DBManager extends SQLiteOpenHelper {

    private static final String TAG = DBManager.class.getSimpleName();

    public static final String DATABASE_NAME = "PictureSQLite";
    public static final String FILE_NAME = "PictureSQLite";
    private static final String TABLE_NAME = "picture";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String ENG1 = "eng1";
    private static final String ENG2 = "eng2";
    private static final String ENG3 = "eng3";
    private static final String VIE1 = "vie1";
    private static final String VIE2 = "vie2";
    private static final String VIE3 = "vie3";
    private static final String TIMESHOOT = "timeshoot";

    private Context context;

    public DBManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        String sql = "CREATE TABLE " + TABLE_NAME + " (" +
//                ID + " INTEGER PRIMARY KEY, " +
//                NAME + " TEXT, " +
//                ENG1 + " TEXT, " +
//                ENG2 + " TEXT, " +
//                ENG3 + " TEXT, " +
//                VIE1 + " TEXT, " +
//                VIE2 + " TEXT, " +
//                VIE3 + " TEXT, " +
//                TIMESHOOT + " DOUBLE)";
//        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addPicture(CapturePicture pic) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, pic.getId());
        values.put(NAME, pic.getName());
        values.put(ENG1, pic.getEng1());
        values.put(ENG2, pic.getEng2());
        values.put(ENG3, pic.getEng3());
        values.put(VIE1, pic.getVie1());
        values.put(VIE2, pic.getVie2());
        values.put(VIE3, pic.getVie3());
        values.put(TIMESHOOT, pic.getTimeshoot());
        long result = db.insert(TABLE_NAME, null, values);
        if (result != -1) {
            return true;
        }
        db.close();
        return false;
    }

    public List<CapturePicture> getAllPicture() {
        List<CapturePicture> listPicture = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + ID + " DESC", null);
        if (cursor.moveToFirst()) {
            do {
                CapturePicture pic = new CapturePicture(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getDouble(8));
                listPicture.add(pic);
            } while (cursor.moveToNext());
        }
        db.close();
        return listPicture;
    }

//    public CapturePicture getPictureByName(String name) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query(TABLE_NAME,
//                new String[] {ID, NAME, ENG1, ENG2, ENG3, VIE1, VIE2, VIE3, TIMESHOOT},
//                NAME + "=?",
//                new String[] {String.valueOf(name)},
//                null, null, null, null);
//        if (cursor != null) {
//            cursor.moveToFirst();
//        }
//        Log.e("DB", "getPictureById: " + cursor.toString());
//        CapturePicture pic = new CapturePicture(
//                cursor.getInt(0),
//                cursor.getString(1),
//                cursor.getString(2),
//                cursor.getString(3),
//                cursor.getString(4),
//                cursor.getString(5),
//                cursor.getString(6),
//                cursor.getString(7),
//                cursor.getDouble(8));
//        cursor.close();
//        db.close();
//        return pic;
//    }

    public boolean deletePictureByName(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, NAME + "=?", new String[] {String.valueOf(name)});
        if (result > 0) {
            return true;
        }
        db.close();
        return false;
    }

    public int getMaxId() {
        int id = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select max(" + ID + ") from " + TABLE_NAME, null);
        if (cursor != null) {
            cursor.moveToFirst();
            id = cursor.getInt(0);
        }
        return id;
    }
}
