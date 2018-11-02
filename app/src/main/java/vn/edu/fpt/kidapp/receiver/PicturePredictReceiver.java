package vn.edu.fpt.kidapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import vn.edu.fpt.kidapp.interfaces.Observer;
import vn.edu.fpt.kidapp.interfaces.Observerable;
import vn.edu.fpt.kidapp.utils.TranslateUtil;

public class PicturePredictReceiver extends BroadcastReceiver implements Observerable {

    private static final String TAG = PicturePredictReceiver.class.getSimpleName();

    private Vector<Observer> observers;

    public PicturePredictReceiver() {
        observers = new Vector<>();
    }

    @Override
    public void onReceive(Context context, final Intent intent) {
        if (intent.getAction().equals("ACTION_PREDICT_SUCCESS")) {
            String rs1 = intent.getStringExtra("result1");
            String rs2 = intent.getStringExtra("result2");
            String rs3 = intent.getStringExtra("result3");
            sendNotification(Observerable.PICTURE_PREDICT, rs1, rs2, rs3);
            List<TranslateUtil.RequestBody> listEng = new ArrayList<>();
            listEng.add(new TranslateUtil.RequestBody(rs1));
            listEng.add(new TranslateUtil.RequestBody(rs2));
            listEng.add(new TranslateUtil.RequestBody(rs3));
            TranslateUtil.translateEnglishToVietnamese(listEng, context);

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
