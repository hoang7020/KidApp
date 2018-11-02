package vn.edu.fpt.kidapp.utils;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {

    private static final String TAG = FileUtil.class.getSimpleName();

    public static void savePictureToSdcard(String fileName, byte[] data) {
        File appKidDir = new File(Environment.getExternalStorageDirectory() + "/KidApp/");
        appKidDir.mkdirs();
        File picture = new File(appKidDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(picture);
            fos.write(data);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e(TAG, "convertByteArrayToFileImage: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "convertByteArrayToFileImage: " + e.getMessage());
        }
    }

    public static Bitmap readFileFromSdCard(String fileName) {

        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard.getAbsolutePath() + "/KidApp/", fileName);
        Bitmap bitmap = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(fis);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static boolean deleteFileFromSdCard(String fileName) {
        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard.getAbsolutePath() + "/KidApp/", fileName);
        return file.delete();
    }


    public static boolean isPictureExist(String fileName) {
        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard.getAbsolutePath() + "/KidApp/", fileName);
        return file.exists();
    }

    public static byte[] convertBitmapToByteArray(Bitmap bm) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bytesArray = stream.toByteArray();
        return bytesArray;
    }

    public static Uri getImageUri(String fileName) {
        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard.getAbsolutePath() + "/KidApp/", fileName);
        Uri uri = Uri.fromFile(file);
        return uri;
    }

    public static boolean checkDB(Context context, String dbName) {
        boolean result = false;
        File dbFile = null;
        try {
            String dbPath = "/data/data/" + context.getPackageName() + "/databases/" + dbName;
            dbFile = new File(dbPath);
            result = dbFile.exists();
        } catch (SQLiteException e) {
            Log.e(TAG, "DB doesn't exist!!!");
        } finally {
            if (dbFile != null) {
                dbFile = null;
            }
        }
        return result;
    }

    public static void copyDB(InputStream is, OutputStream os) throws IOException {
        byte[] buffer = new byte[1024];
        int length;
        while ((length = is.read(buffer)) > 0) {
            os.write(buffer, 0, length);
        }
        os.flush();
        is.close();
        os.close();
    }

    public static void copyDBFromAssetToData(final String fileName, final String desDirectory, final Context context) throws IOException {
        File directory = new File(desDirectory);
        if (!directory.exists()) {
            directory.mkdir();
        }
        File f = new File(directory.getAbsolutePath(), fileName);
        InputStream is = context.getAssets().open(fileName);
        OutputStream os = new FileOutputStream(desDirectory + "/" + fileName);
        copyDB(is, os);
    }
}
