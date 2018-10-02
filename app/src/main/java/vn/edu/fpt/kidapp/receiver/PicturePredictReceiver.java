package vn.edu.fpt.kidapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.fpt.kidapp.utils.TranslateUtil;

public class PicturePredictReceiver extends BroadcastReceiver {

    private static final String TAG = PicturePredictReceiver.class.getSimpleName();

    private TextView txtResult1, txtResult2, txtResult3;

    public PicturePredictReceiver(TextView result1, TextView result2, TextView result3) {
        this.txtResult1 = result1;
        this.txtResult2 = result2;
        this.txtResult3 = result3;
    }

    String result1;

    @Override
    public void onReceive(Context context, final Intent intent) {
        if (intent.getAction().equals("ACTION_PREDICT_SUCCESS")) {
            result1 = intent.getStringExtra("result1");
            String result2 = intent.getStringExtra("result2");
            String result3 = intent.getStringExtra("result3");
            txtResult1.setText(result1);
            txtResult2.setText(result2);
            txtResult3.setText(result3);
            List<TranslateUtil.RequestBody> listEng = new ArrayList<>();
            listEng.add(new TranslateUtil.RequestBody(result1));
            listEng.add(new TranslateUtil.RequestBody(result2));
            listEng.add(new TranslateUtil.RequestBody(result3));
            TranslateUtil.translateEnglishToVietnamese(listEng, context);

        }
    }
}
