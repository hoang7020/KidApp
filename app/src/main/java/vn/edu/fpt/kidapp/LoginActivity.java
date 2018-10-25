package vn.edu.fpt.kidapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import vn.edu.fpt.kidapp.database.DBManagerAPI;
import vn.edu.fpt.kidapp.model.UserResultJSON;
import vn.edu.fpt.kidapp.receiver.UserReceiver;
import vn.edu.fpt.kidapp.utils.PreferenceUtil;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private static LoginActivity _instance = null;

    private EditText edtUsername, edtPassword;
    private Button btnLogin, btnRegister;

    private UserReceiver userReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _instance = this;

        if (checkLogin()) {
            Intent intent = new Intent(this, BeginActivity.class);
            startActivity(intent);
        }

        initView();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                DBManagerAPI dba = new DBManagerAPI(getApplicationContext());
                dba.login(username, password);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    BroadcastReceiver loginReciver = new BroadcastReceiver() {
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
                    startActivity(i);
                    finish();
                }
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        userReceiver = new UserReceiver();
        registerReceiver(loginReciver, new IntentFilter(DBManagerAPI.ACTION_LOGIN));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(loginReciver);
    }

    private void initView() {
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
    }

    private boolean checkLogin() {
        boolean result = false;
        String username = PreferenceUtil.getInstance(this).getStringValue("Username", "");
        if (username != "") {
            return true;
        }
        return result;
    }

    public static synchronized LoginActivity getInstance() {
        return _instance;
    }
}
