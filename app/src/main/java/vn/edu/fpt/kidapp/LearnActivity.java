package vn.edu.fpt.kidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

public class LearnActivity extends AppCompatActivity {

    VideoView videoView;
    ImageView btnPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        videoView = findViewById(R.id.videoView);
        btnPlay = findViewById(R.id.btnPlay);

        videoView.setVideoPath("/sdcard/monkey.mp4");

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPlay.setVisibility(View.INVISIBLE);
                videoView.start();
            }
        });

        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                videoView.pause();
                btnPlay.setVisibility(View.VISIBLE);
                return false;
            }
        });

    }
}
