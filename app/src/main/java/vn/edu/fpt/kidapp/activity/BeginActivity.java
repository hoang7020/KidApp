package vn.edu.fpt.kidapp.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import vn.edu.fpt.kidapp.R;

public class BeginActivity extends AppCompatActivity {

    private static final String TAG = BeginActivity.class.getSimpleName();

    public static final int CAMERA_REQUEST_CODE = 2222;

    private MediaPlayer mMediaPlayer;
    private MediaPlayer mWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin);

        initPermission();

        mWelcome = MediaPlayer.create(this, R.raw.welcome);
        mWelcome.start();
        ImageView btnCamera = findViewById(R.id.btnCamera);
        mMediaPlayer = MediaPlayer.create(this, R.raw.hatxi);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWelcome.stop();
                mMediaPlayer.start();
                Intent intent = new Intent(BeginActivity.this, CameraActivity.class);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });
    }

    public void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String pictureName = data.getStringExtra("FILENAME");
                Intent intent = new Intent(BeginActivity.this, MainActivity.class);
                intent.putExtra("FILENAME", pictureName);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWelcome.release();
        mMediaPlayer.release();
    }
}
