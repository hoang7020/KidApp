package vn.edu.fpt.kidapp.activity;

import android.app.DialogFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Locale;

import vn.edu.fpt.kidapp.R;
import vn.edu.fpt.kidapp.database.DBManagerAPI;
import vn.edu.fpt.kidapp.fragment.LoadingFragment;
import vn.edu.fpt.kidapp.interfaces.Observer;
import vn.edu.fpt.kidapp.interfaces.Subject;
import vn.edu.fpt.kidapp.model.APIObjectJSON;
import vn.edu.fpt.kidapp.receiver.EnglishTranslateReceiver;
import vn.edu.fpt.kidapp.receiver.PicturePredictReceiver;
import vn.edu.fpt.kidapp.utils.ClarifaiUtil;
import vn.edu.fpt.kidapp.utils.FileUtil;
import vn.edu.fpt.kidapp.utils.PreferenceUtil;


public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener, Observer {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int CAMERA_REQUEST_CODE = 1111;
    private static final int HISTORY_REQUEST_CODE = 2222;

    private ImageView btnRead1, btnRead2, btnRead3, btnCamera;
    private TextView txtResult1, txtResult2, txtResult3, txtVietname1, txtVietname2, txtVietname3;
    private ImageView ivResult;
    private Toolbar mToolbar;
    private TextToSpeech tts;
    private MediaPlayer mMediaPlayer;
    private DialogFragment mLoading;
    private String fileName;
    private DBManagerAPI dbManagerAPI;
    private Gson gson;

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

        Intent intent = this.getIntent();
        if (intent.getIntExtra("TYPE", 0) == BeginActivity.CAMERA_REQUEST_CODE) {
            initLoadingDialog();
            fileName = intent.getStringExtra("FILENAME");
            Bitmap bm = FileUtil.readFileFromSdCard(fileName);
            ivResult.setImageBitmap(bm);
            ClarifaiUtil util = new ClarifaiUtil();
            util.predictImage(FileUtil.convertBitmapToByteArray(bm), this);
        }
        if (intent.getIntExtra("TYPE", 0) == BeginActivity.HISTORY_REQUEST_CODE) {
            APIObjectJSON.Picture pic = (APIObjectJSON.Picture) intent.getSerializableExtra("PICTURE");
            Bitmap bm = FileUtil.readFileFromSdCard(pic.getImageName());
            if (!FileUtil.isPictureExist(pic.getImageName())) {
                ivResult.setImageResource(R.drawable.camera);
            } else {
                ivResult.setImageBitmap(bm);
            }
            txtResult1.setText(pic.getEngSub().getEng1());
            txtResult2.setText(pic.getEngSub().getEng2());
            txtResult3.setText(pic.getEngSub().getEng3());
            txtVietname1.setText(pic.getVieSub().getVie1());
            txtVietname2.setText(pic.getVieSub().getVie2());
            txtVietname3.setText(pic.getVieSub().getVie3());
        }

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
                APIObjectJSON.Picture pic = (APIObjectJSON.Picture) data.getSerializableExtra("PICTURE");
                Bitmap bm = FileUtil.readFileFromSdCard(pic.getImageName());
                if (!FileUtil.isPictureExist(pic.getImageName())) {
                    ivResult.setImageResource(R.drawable.camera);
                } else {
                    ivResult.setImageBitmap(bm);
                }
                txtResult1.setText(pic.getEngSub().getEng1());
                txtResult2.setText(pic.getEngSub().getEng2());
                txtResult3.setText(pic.getEngSub().getEng3());
                txtVietname1.setText(pic.getVieSub().getVie1());
                txtVietname2.setText(pic.getVieSub().getVie2());
                txtVietname3.setText(pic.getVieSub().getVie3());


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

        IntentFilter filters = new IntentFilter();
//        filters.addAction(DBManagerAPI.ACTION_GET_ALL);
        filters.addAction(DBManagerAPI.ACTION_ADD_PICTURE);;
        registerReceiver(addReceiver, filters);

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
        dbManagerAPI = new DBManagerAPI(this);
        gson = new Gson();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mReceiverPicturePredict);
        unregisterReceiver(mReceiverEnglishTranslate);
        unregisterReceiver(addReceiver);
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
                Intent historyIntent = new Intent(MainActivity.this, ViewHistoryActivity.class);
                startActivityForResult(historyIntent, HISTORY_REQUEST_CODE);
                return true;
            case R.id.btnLogout:
                PreferenceUtil.getInstance(this).putStringValue("username", "");
                finish();
                Intent loginIntent = new Intent(this, LoginActivity.class);
                startActivity(loginIntent);
            case R.id.btnProfile:
                Intent profileIntent = new Intent(this, ProfileActivity.class);
                startActivity(profileIntent);
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
        if (type == Subject.PICTURE_PREDICT) {
            txtResult1.setText(rs1);
            txtResult2.setText(rs2);
            txtResult3.setText(rs3);
        }
        if (type == Subject.ENGLISH_TRANSLATE) {
            txtVietname1.setText(rs1);
            txtVietname2.setText(rs2);
            txtVietname3.setText(rs3);
            mLoading.dismiss();
            dbManagerAPI.addPicture(PreferenceUtil.getInstance(this).getStringValue("username", ""), fileName, System.currentTimeMillis(),
                    txtResult1.getText().toString(), txtResult2.getText().toString(), txtResult3.getText().toString(),
                    rs1, rs2, rs3);
        }
    }


    BroadcastReceiver addReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DBManagerAPI.ACTION_ADD_PICTURE)) {
                String result = intent.getStringExtra("API_RESULT");
                Log.e(TAG, "ADD PICTURE: " + result);
                APIObjectJSON resultJSON = gson.fromJson(result, new TypeToken<APIObjectJSON>(){}.getType());
                if (resultJSON.getStatus().getCode() == 200) {
                    Toast.makeText(context, resultJSON.getStatus().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, resultJSON.getStatus().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
}
