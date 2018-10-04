package vn.edu.fpt.kidapp.receiver;

import android.app.DialogFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.List;

import vn.edu.fpt.kidapp.JsonModel.JSONLanguage;
import vn.edu.fpt.kidapp.LoadingFragment;
import vn.edu.fpt.kidapp.utils.TranslateUtil;

public class EnglishTranslateReceiver extends BroadcastReceiver {

    private static final String TAG = EnglishTranslateReceiver.class.getSimpleName();

    private TextView txtVietnamese1, txtVietnamese2, txtVietnamese3;
    private DialogFragment mLoading;

    public EnglishTranslateReceiver(TextView vn1, TextView vn2, TextView vn3, DialogFragment lav) {
        this.txtVietnamese1 = vn1;
        this.txtVietnamese2 = vn2;
        this.txtVietnamese3 = vn3;
        this.mLoading = lav;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("ACTION_TRANSLATE_SUCCESS")) {
            String json = intent.getStringExtra("RESULT_JSON");
            Log.e(TAG, "onReceive: " + json);
            List<JSONLanguage> results = TranslateUtil.convertJSONToList(json);
            txtVietnamese1.setText(results.get(0).getTranslations().get(1).getText());
            txtVietnamese2.setText(results.get(1).getTranslations().get(1).getText());
            txtVietnamese3.setText(results.get(2).getTranslations().get(1).getText());
            mLoading.dismiss();
        }
    }
}
