package vn.edu.fpt.kidapp.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import vn.edu.fpt.kidapp.R;
import vn.edu.fpt.kidapp.model.APIObjectJSON;

public class BeginActivity extends AppCompatActivity {

    private static final String TAG = BeginActivity.class.getSimpleName();

    public static final int CAMERA_REQUEST_CODE = 2222;
    public static final int HISTORY_REQUEST_CODE = 4567;

    private MediaPlayer mMediaPlayer;
    private MediaPlayer mWelcome;
    private ImageView btnCamera;
    private Button btnHistory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin);

        initView();

        mWelcome.start();

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWelcome.stop();
                mMediaPlayer.start();
                Intent intent = new Intent(BeginActivity.this, CameraActivity.class);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWelcome.stop();
                Intent intent = new Intent(BeginActivity.this, ViewHistoryActivity.class);
                startActivityForResult(intent, HISTORY_REQUEST_CODE);
            }
        });
    }

    private void initView() {
        mWelcome = MediaPlayer.create(this, R.raw.welcome);
        mMediaPlayer = MediaPlayer.create(this, R.raw.hatxi);
        btnCamera = findViewById(R.id.btnCamera);
        btnHistory = findViewById(R.id.btnHistory);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String pictureName = data.getStringExtra("FILENAME");
                Intent intent = new Intent(BeginActivity.this, MainActivity.class);
                intent.putExtra("TYPE", CAMERA_REQUEST_CODE);
                intent.putExtra("FILENAME", pictureName);
                startActivity(intent);
                finish();
            }
        }
        if (requestCode == HISTORY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                APIObjectJSON.Picture pic = (APIObjectJSON.Picture) data.getSerializableExtra("PICTURE");
                Intent intent = new Intent(BeginActivity.this, MainActivity.class);
                intent.putExtra("TYPE", HISTORY_REQUEST_CODE);
                intent.putExtra("PICTURE", pic);
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
