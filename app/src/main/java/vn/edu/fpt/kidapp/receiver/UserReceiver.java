package vn.edu.fpt.kidapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import vn.edu.fpt.kidapp.BeginActivity;
import vn.edu.fpt.kidapp.LoginActivity;
import vn.edu.fpt.kidapp.database.DBManagerAPI;
import vn.edu.fpt.kidapp.model.UserResultJSON;
import vn.edu.fpt.kidapp.utils.PreferenceUtil;

public class UserReceiver extends BroadcastReceiver {

    private static final String TAG = UserReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(DBManagerAPI.ACTION_LOGIN)) {
            String result = intent.getStringExtra("API_RESULT");
            Gson gson = new Gson();
            UserResultJSON resultJSON = gson.fromJson(result, new TypeToken<UserResultJSON>() {}.getType());
            Log.e(TAG, "onReceive: " + resultJSON.getData().getUsername());
            if (resultJSON.getStatus().getCode() == 200) {
                PreferenceUtil.getInstance(context).putStringValue("Username", resultJSON.getData().getUsername());
                PreferenceUtil.getInstance(context).putStringValue("Address", resultJSON.getData().getAddress());
                Intent i = new Intent(context, BeginActivity.class);
                context.startActivity(i);
                LoginActivity.getInstance().finish();
            }
        }
        if (intent.getAction().equals(DBManagerAPI.ACTION_REGISTER)) {
            String result = intent.getStringExtra("API_RESULT");
            Gson gson = new Gson();
            UserResultJSON resultJSON = gson.fromJson(result, new TypeToken<UserResultJSON>() {}.getType());
            Log.e(TAG, "onReceive: " + resultJSON.getData().getUsername());
            if (resultJSON.getStatus().getCode() == 200) {
                PreferenceUtil.getInstance(context).putStringValue("Username", resultJSON.getData().getUsername());
                PreferenceUtil.getInstance(context).putStringValue("Address", resultJSON.getData().getAddress());
                Intent i = new Intent(context, BeginActivity.class);
                context.startActivity(i);
                LoginActivity.getInstance().finish();
            }
        }
    }

}
