package vn.edu.fpt.kidapp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.theartofdev.edmodo.cropper.CropImageView;

import vn.edu.fpt.kidapp.R;
import vn.edu.fpt.kidapp.utils.FileUtil;

public class CropImageActivity extends AppCompatActivity {

    CropImageView cropImageView;
    ImageButton btnCrop, btnCancel;
    Intent intent;
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_image);

        cropImageView = findViewById(R.id.cropImageView);
        btnCrop = findViewById(R.id.btnCrop);
        btnCancel = findViewById(R.id.btnCancel);

        intent = getIntent();
        fileName = intent.getStringExtra("FILENAME");
        Bitmap bitmap = FileUtil.readFileFromSdCard(fileName);
        cropImageView.setImageBitmap(bitmap);


        btnCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap cropImage = cropImageView.getCroppedImage();
                FileUtil.savePictureToSdcard(fileName, FileUtil.convertBitmapToByteArray(cropImage));
                setResult(RESULT_OK);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}
