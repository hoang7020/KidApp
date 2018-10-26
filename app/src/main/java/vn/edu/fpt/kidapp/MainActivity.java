package vn.edu.fpt.kidapp;

import android.app.DialogFragment;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import vn.edu.fpt.kidapp.Model.CapturePicture;
import vn.edu.fpt.kidapp.database.DBManager;
import vn.edu.fpt.kidapp.interfaces.Observer;
import vn.edu.fpt.kidapp.interfaces.Observerable;
import vn.edu.fpt.kidapp.receiver.EnglishTranslateReceiver;
import vn.edu.fpt.kidapp.receiver.PicturePredictReceiver;
import vn.edu.fpt.kidapp.utils.ClarifaiUtil;
import vn.edu.fpt.kidapp.utils.Constant;
import vn.edu.fpt.kidapp.utils.FileUtil;


public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener, Observer {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int CAMERA_REQUEST_CODE = 1111;
    private static final int HISTORY_REQUEST_CODE = 2222;

    private ImageView btnRead1, btnRead2, btnRead3, btnCamera;
    private TextView txtResult1, txtResult2, txtResult3, txtVietname1, txtVietname2, txtVietname3, txtPronunciation;
    private ImageView ivResult;
    private Toolbar mToolbar;
    private TextToSpeech tts;
    private MediaPlayer mMediaPlayer;
    private DialogFragment mLoading;
    private String fileName;

    private PicturePredictReceiver mReceiverPicturePredict;
    private EnglishTranslateReceiver mReceiverEnglishTranslate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initView();

        initLoadingDialog();
        final Intent intent = this.getIntent();
        fileName = intent.getStringExtra("FILENAME");
        Bitmap bm = FileUtil.readFileFromSdCard(fileName);
        ivResult.setImageResource(R.drawable.monkey);
        ClarifaiUtil util = new ClarifaiUtil();
        util.predictImage(FileUtil.convertBitmapToByteArray(bm), this);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaPlayer.start();
                Constant.FLAG = true;
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });

//        btnRead1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                speakOut(txtResult1);
//            }
//        });
//        btnRead2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                speakOut(txtResult2);
//            }
//        });
//        btnRead3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                speakOut(txtResult3);
//            }
//        });

        txtResult1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakOut(txtResult1);
                Intent learnIntent = new Intent(MainActivity.this, LearnActivity.class);
                startActivity(learnIntent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                initLoadingDialog();
                fileName = data.getStringExtra("FILENAME");
                Bitmap bm = FileUtil.readFileFromSdCard(fileName);
                ivResult.setImageBitmap(bm);
                ClarifaiUtil util = new ClarifaiUtil();
                util.predictImage(FileUtil.convertBitmapToByteArray(bm), this);
            }
        }
        if (requestCode == HISTORY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {


                CapturePicture pic = (CapturePicture) data.getSerializableExtra("PICTURE");
                Bitmap bm = FileUtil.readFileFromSdCard(pic.getName());
                ivResult.setImageBitmap(bm);
                txtResult1.setText(pic.getEng1());
                txtResult2.setText(pic.getEng2());
                txtResult3.setText(pic.getEng3());
                txtVietname1.setText(pic.getVie1());
                txtVietname2.setText(pic.getVie2());
                txtVietname3.setText(pic.getVie3());


            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mReceiverPicturePredict = new PicturePredictReceiver();
        mReceiverPicturePredict.registerObserver(this);
        registerReceiver(mReceiverPicturePredict, new IntentFilter("ACTION_PREDICT_SUCCESS"));

        mReceiverEnglishTranslate = new EnglishTranslateReceiver();
        mReceiverEnglishTranslate.registerObserver(this);
        registerReceiver(mReceiverEnglishTranslate, new IntentFilter("ACTION_TRANSLATE_SUCCESS"));

    }

    private void initView() {
        btnCamera = findViewById(R.id.btnCamera);
        txtResult1 = findViewById(R.id.txtResult1);
//        txtResult2 = findViewById(R.id.txtResult2);
//        txtResult3 = findViewById(R.id.txtResult3);
        txtVietname1 = findViewById(R.id.txtVienamese1);
//        txtVietname2 = findViewById(R.id.txtVietname2);
//        txtVietname3 = findViewById(R.id.txtVietname3);
        txtPronunciation = findViewById(R.id.txtPronunciation);
        ivResult = findViewById(R.id.ivResult);
//        btnRead1 = findViewById(R.id.btnRead1);
//        btnRead2 = findViewById(R.id.btnRead2);
//        btnRead3 = findViewById(R.id.btnRead3);
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
//            if (result == TextToSpeech.LANG_MISSING_DATA
//                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                Log.e("TTS", "This TranslateResult is not supported");
//            }
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnHistory:

                Intent intent = new Intent(MainActivity.this, ViewHistoryActivity.class);
                startActivityForResult(intent, HISTORY_REQUEST_CODE);
                return true;
            default:
                return true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaPlayer.release();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void initLoadingDialog() {
        mLoading = new LoadingFragment();
        mLoading.setCancelable(false);
        mLoading.show(getFragmentManager(), "Loading");
    }

    @Override
    public void getNotification(int type, String rs1, String rs2, String rs3) {
        if (type == Observerable.PICTURE_PREDICT) {
//            txtResult1.setText(rs1);
            txtResult1.setText("Monkey");
            txtPronunciation.setText("/'mʌŋki/ ");
//            txtResult2.setText(rs2);
//            txtResult3.setText(rs3);
        }
        if (type == Observerable.ENGLISH_TRANSLATE) {
//            txtVietname1.setText(rs1);
            txtVietname1.setText("Con Khỉ");
            speakOut(txtResult1);
//            txtVietname2.setText(rs2);
//            txtVietname3.setText(rs3);
            mLoading.dismiss();
            DBManager db = new DBManager(this);
            int id = db.getMaxId();
            CapturePicture pic = new CapturePicture(
                    id + 1,
                    fileName,
                    txtResult1.getText().toString(),
                    txtResult1.getText().toString(),
                    txtResult1.getText().toString(),
                    rs1,
                    rs2,
                    rs3,
                    System.currentTimeMillis());
            db.addPicture(pic);
        }
    }
}
