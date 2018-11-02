package vn.edu.fpt.kidapp.utils;

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
import java.net.URI;

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
}
