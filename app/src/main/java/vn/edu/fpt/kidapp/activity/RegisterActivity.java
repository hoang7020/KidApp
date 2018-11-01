package vn.edu.fpt.kidapp.activity;

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

import vn.edu.fpt.kidapp.R;
import vn.edu.fpt.kidapp.database.DBManagerAPI;
import vn.edu.fpt.kidapp.model.APIObjectJSON;
import vn.edu.fpt.kidapp.utils.PreferenceUtil;

public class RegisterActivity extends AppCompatActivity {

    private final String TAG = RegisterActivity.class.getSimpleName();
    private static RegisterActivity _instance = null;

    private EditText edtUsername, edtPassword, edtConfirmPassword, edtAddress;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        _instance = this;

        initView();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                String confirmPassword = edtConfirmPassword.getText().toString();
                String address = edtAddress.getText().toString();
                if (password.equals(confirmPassword)) {
                    DBManagerAPI dba = new DBManagerAPI(getApplicationContext());
                    dba.register(username, password, address);
                } else {
                    Log.e(TAG, "onClick: " + "Confirm Password does not match!!!");
                    return;
                }
            }
        });
    }

    BroadcastReceiver regReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DBManagerAPI.ACTION_REGISTER)) {
                String result = intent.getStringExtra("API_RESULT");
                Gson gson = new Gson();
                APIObjectJSON resultJSON = gson.fromJson(result, new TypeToken<APIObjectJSON>() {}.getType());
                Log.e(TAG, "onReceive: " + resultJSON.getData().getUsername());
                if (resultJSON.getStatus().getCode() == 200) {
                    PreferenceUtil.getInstance(context).putStringValue("Username", resultJSON.getData().getUsername());
                    PreferenceUtil.getInstance(context).putStringValue("Address", resultJSON.getData().getAddress());
                    Intent i = new Intent(context, BeginActivity.class);
                    startActivity(i);
                    getInstance().finish();
                }
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(DBManagerAPI.ACTION_REGISTER);
        registerReceiver(regReceiver, filter);
    }

    private void initView() {
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        edtAddress = findViewById(R.id.edtAddress);
        btnRegister = findViewById(R.id.btnRegister);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(regReceiver);
    }

    public static synchronized RegisterActivity getInstance() {
        return _instance;
    }
}
