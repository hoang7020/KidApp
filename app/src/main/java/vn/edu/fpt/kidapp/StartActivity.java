package vn.edu.fpt.kidapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;

public class StartActivity extends AppCompatActivity {

    LottieAnimationView lavStarting;


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
            }
        }, 3000);

    }

    private void initView() {
        lavStarting = findViewById(R.id.lav_starting);
    }
}
