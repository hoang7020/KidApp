package vn.edu.fpt.kidapp;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import vn.edu.fpt.kidapp.receiver.EnglishTranslateReceiver;
import vn.edu.fpt.kidapp.receiver.PicturePredictReceiver;
import vn.edu.fpt.kidapp.utils.ClarifaiUtil;
import vn.edu.fpt.kidapp.utils.FileUtil;
import vn.edu.fpt.kidapp.utils.TranslateUtil;


public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int CAMERA_REQUEST_CODE = 1111;

    private ImageView btnRead1, btnRead2, btnRead3, btnCamera;
    private TextView txtResult1, txtResult2, txtResult3, txtVietname1, txtVietname2, txtVietname3;
    private ImageView ivResult;
    private Toolbar mToolbar;
    private TextToSpeech tts;
    private MediaPlayer mMediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        mToolbar.setTitle("Cú Cú Thông Thái");
        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initView();

        Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
        Intent intent = this.getIntent();
        String fileName = intent.getStringExtra("FILENAME");
        Bitmap bm = FileUtil.readFileFromSdCard(fileName);
        ivResult.setImageBitmap(bm);
        ClarifaiUtil util = new ClarifaiUtil();
        util.predictImage(FileUtil.convertBitmapToByteArray(bm), this);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaPlayer.start();
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });

        btnRead1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakOut(txtResult1);
            }
        });
        btnRead2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakOut(txtResult2);
            }
        });
        btnRead3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakOut(txtResult3);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
                String fileName = data.getStringExtra("FILENAME");
                Bitmap bm = FileUtil.readFileFromSdCard(fileName);
                ivResult.setImageBitmap(bm);
                ClarifaiUtil util = new ClarifaiUtil();
                util.predictImage(FileUtil.convertBitmapToByteArray(bm), this);
            }
        }
    }

    PicturePredictReceiver mReceiverPicturePredict;
    EnglishTranslateReceiver mReceiverEnglishTranslate;

    @Override
    protected void onStart() {
        super.onStart();
        mReceiverPicturePredict = new PicturePredictReceiver(txtResult1, txtResult2, txtResult3);
        mReceiverEnglishTranslate = new EnglishTranslateReceiver(txtVietname1, txtVietname2, txtVietname3);
        registerReceiver(mReceiverPicturePredict, new IntentFilter("ACTION_PREDICT_SUCCESS"));
        registerReceiver(mReceiverEnglishTranslate, new IntentFilter("ACTION_TRANSLATE_SUCCESS"));
    }

    private void initView() {
        btnCamera = findViewById(R.id.btnCamera);
        txtResult1 = findViewById(R.id.txtResult1);
        txtResult2 = findViewById(R.id.txtResult2);
        txtResult3 = findViewById(R.id.txtResult3);
        txtVietname1 = findViewById(R.id.txtVietname1);
        txtVietname2 = findViewById(R.id.txtVietname2);
        txtVietname3 = findViewById(R.id.txtVietname3);
        ivResult = findViewById(R.id.ivResult);
        btnRead1 = findViewById(R.id.btnRead1);
        btnRead2 = findViewById(R.id.btnRead2);
        btnRead3 = findViewById(R.id.btnRead3);
        tts = new TextToSpeech(this, this);
        mMediaPlayer = MediaPlayer.create(this, R.raw.hatxi);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mReceiverPicturePredict);
        unregisterReceiver(mReceiverEnglishTranslate);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This JSONLanguage is not supported");
            }
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    private void speakOut(TextView tv) {
        String text = tv.getText().toString();
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaPlayer.release();
    }
}
