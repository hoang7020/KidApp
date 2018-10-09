package vn.edu.fpt.kidapp.receiver;

import android.app.DialogFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.List;
import java.util.Vector;

import vn.edu.fpt.kidapp.JsonModel.JSONLanguage;
import vn.edu.fpt.kidapp.LoadingFragment;
import vn.edu.fpt.kidapp.interfaces.Observer;
import vn.edu.fpt.kidapp.interfaces.Observerable;
import vn.edu.fpt.kidapp.utils.TranslateUtil;

public class EnglishTranslateReceiver extends BroadcastReceiver implements Observerable {

    private static final String TAG = EnglishTranslateReceiver.class.getSimpleName();

    private Vector<Observer> observers;

    public EnglishTranslateReceiver() {
        observers = new Vector<>();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("ACTION_TRANSLATE_SUCCESS")) {
            String json = intent.getStringExtra("RESULT_JSON");
            Log.e(TAG, "onReceive: " + json);
            List<JSONLanguage> results = TranslateUtil.convertJSONToList(json);
            String rs1 = results.get(0).getTranslations().get(1).getText();
            String rs2 = results.get(1).getTranslations().get(1).getText();
            String rs3 = results.get(2).getTranslations().get(1).getText();
            sendNotification(Observerable.ENGLISH_TRANSLATE, rs1, rs2, rs3);
        }
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void sendNotification(int type, String rs1, String rs2, String rs3) {
        for (int i = 0; i < observers.size(); i++) {
            Observer observer = observers.get(i);
            observer.getNotification(type, rs1, rs2, rs3);
        }
    }

}
