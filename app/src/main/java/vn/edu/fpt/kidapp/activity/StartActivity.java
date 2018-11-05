package vn.edu.fpt.kidapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.airbnb.lottie.LottieAnimationView;

import java.io.IOException;

import vn.edu.fpt.kidapp.R;
import vn.edu.fpt.kidapp.utils.FileUtil;

public class StartActivity extends AppCompatActivity {

    private static final String TAG = StartActivity.class.getSimpleName();

    private LottieAnimationView lavStarting;
    private final String dbName = "PictureSQLite";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        initView();

        lavStarting.setAnimation(R.raw.start);
        lavStarting.playAnimation();
        lavStarting.loop(true);
        Handler handler = new Handler();
        final Intent intent = new Intent(this, BeginActivity.class);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, 3000);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!FileUtil.checkDB(getApplicationContext(), dbName)) {
            try {
                Log.e(TAG, "onCreate: " + getPackageName());
                FileUtil.copyDBFromAssetToData(dbName, "/data/data/" + getPackageName() + "/databases/", getApplicationContext());
            } catch (IOException e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }
    }

    private void initView() {
        lavStarting = findViewById(R.id.lav_starting);
    }
}
