package vn.edu.fpt.kidapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import vn.edu.fpt.kidapp.JsonModel.JSONLanguage;
import vn.edu.fpt.kidapp.utils.TranslateUtil;

public class EnglishTranslateReceiver extends BroadcastReceiver {

    private static final String TAG = EnglishTranslateReceiver.class.getSimpleName();

    private TextView txtVietnamese1, txtVietnamese2, txtVietnamese3;

    public EnglishTranslateReceiver(TextView vn1, TextView vn2, TextView vn3) {
        this.txtVietnamese1 = vn1;
        this.txtVietnamese2 = vn2;
        this.txtVietnamese3 = vn3;
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
        }
    }
}
