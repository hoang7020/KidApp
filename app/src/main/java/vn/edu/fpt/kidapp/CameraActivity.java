package vn.edu.fpt.kidapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;

import vn.edu.fpt.kidapp.utils.FileUtil;

public class CameraActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CROP = 3333;
    private ImageView btnShoot;
    private CameraView mCamera;
    private String FILE_NAME = "IMG_" + System.currentTimeMillis() + ".jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        initView();
        mCamera.addCameraListener(cameraListener);
        mCamera.setJpegQuality(10);
        btnShoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.capturePicture();
                btnShoot.setVisibility(View.INVISIBLE);
            }
        });
    }

    CameraListener cameraListener = new CameraListener() {
        @Override
        public void onPictureTaken(byte[] jpeg) {
            super.onPictureTaken(jpeg);
            FileUtil.savePictureToSdcard(FILE_NAME, jpeg);
            Intent intent = new Intent(CameraActivity.this, CropImageActivity.class);
            intent.putExtra("FILENAME", FILE_NAME);
            startActivityForResult(intent, REQUEST_CODE_CROP);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CROP) {
            if (resultCode == RESULT_OK) {
                Intent intent = getIntent();
                intent.putExtra("FILENAME", FILE_NAME);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                finish();
            }
        }
    }

    private void initView() {
        btnShoot = findViewById(R.id.btnShoot);
        mCamera = findViewById(R.id.cvCamera);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mCamera.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCamera.destroy();
    }
}
