package vn.edu.fpt.kidapp.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import vn.edu.fpt.kidapp.R;
import vn.edu.fpt.kidapp.database.DBManagerAPI;
import vn.edu.fpt.kidapp.model.APIObjectJSON;
import vn.edu.fpt.kidapp.utils.PreferenceUtil;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private static LoginActivity _instance = null;

    private EditText edtUsername, edtPassword;
    private Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _instance = this;

        DBManagerAPI api = new DBManagerAPI(this);
        api.getAllPicture("hoang7020");
//        api.addPicture("hoang7020", "i1", System.currentTimeMillis(), "dog", "cat", "mouse", "cho", "meo", "chuot");
//        api.deltePicture("Image1");

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
                APIObjectJSON resultJSON = gson.fromJson(result, new TypeToken<APIObjectJSON>() {}.getType());
                if (resultJSON.getStatus().getCode() == 200) {
                    PreferenceUtil.getInstance(context).putStringValue("username", resultJSON.getData().getUsername());
                    PreferenceUtil.getInstance(context).putStringValue("address", resultJSON.getData().getAddress());
                    Intent i = new Intent(context, BeginActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(context, resultJSON.getStatus().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
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
        String username = PreferenceUtil.getInstance(this).getStringValue("username", "");
        if (username != "") {
            return true;
        }
        return result;
    }

    public static synchronized LoginActivity getInstance() {
        return _instance;
    }
}
