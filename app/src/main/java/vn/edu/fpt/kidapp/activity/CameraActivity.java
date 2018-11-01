package vn.edu.fpt.kidapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.Flash;
import com.otaliastudios.cameraview.Gesture;
import com.otaliastudios.cameraview.GestureAction;

import vn.edu.fpt.kidapp.R;
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
        mCamera.setJpegQuality(100);
        mCamera.mapGesture(Gesture.TAP, GestureAction.FOCUS_WITH_MARKER);
        mCamera.mapGesture(Gesture.PINCH, GestureAction.ZOOM);


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

    public void clickToChangeCamera(View view) {
        mCamera.toggleFacing();

    }
    ImageView btnFlash;

    public void clickToFlash(View view) {
        btnFlash = findViewById(R.id.btnFlash);
        if(mCamera.getFlash()==Flash.ON){
            mCamera.setFlash(Flash.OFF);
            btnFlash.setImageResource(R.drawable.flashoff);
        }else{
            mCamera.setFlash(Flash.ON);
            btnFlash.setImageResource(R.drawable.flashon);

        }
    }
}
