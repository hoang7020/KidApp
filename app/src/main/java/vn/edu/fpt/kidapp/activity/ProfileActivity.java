package vn.edu.fpt.kidapp.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import vn.edu.fpt.kidapp.R;
import vn.edu.fpt.kidapp.database.DBManagerAPI;
import vn.edu.fpt.kidapp.model.APIObjectJSON;
import vn.edu.fpt.kidapp.utils.PreferenceUtil;

public class ProfileActivity extends AppCompatActivity {

    public static final String TAG = ProfileActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private TextView txtUsername, txtAddress, txtDateCreated;
    private Button btnChangePassword;
    private EditText edtOldPassword, edtNewPassword;
    private DBManagerAPI dbManagerAPI;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initView();

        mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);

        txtUsername.setText("Username: " + PreferenceUtil.getInstance(this).getStringValue("username", ""));
        txtAddress.setText("Address: " + PreferenceUtil.getInstance(this).getStringValue("address", ""));
        txtDateCreated.setText("Date Created: " + PreferenceUtil.getInstance(this).getStringValue("date", ""));

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = edtOldPassword.getText().toString();
                String newPassword = edtNewPassword.getText().toString();
                dbManagerAPI.changePassword(PreferenceUtil.getInstance(getApplicationContext()).getStringValue("username", ""), oldPassword, newPassword);
            }
        });
    }

    BroadcastReceiver changePasswordReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DBManagerAPI.ACTION_CHANGE_PASSWORD)) {
                String result = intent.getStringExtra("API_RESULT");
                APIObjectJSON resultJSON = gson.fromJson(result, new TypeToken<APIObjectJSON>(){}.getType());
                if (resultJSON.getStatus().getCode() == 200) {
                    Toast.makeText(context, resultJSON.getStatus().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, resultJSON.getStatus().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(changePasswordReceiver, new IntentFilter(DBManagerAPI.ACTION_CHANGE_PASSWORD));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(changePasswordReceiver);
    }

    private void initView() {
        mToolbar = findViewById(R.id.my_toolbar);
        txtUsername = findViewById(R.id.txtUsername);
        txtAddress = findViewById(R.id.txtAddress);
        txtDateCreated = findViewById(R.id.txtDateCreated);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        edtOldPassword = findViewById(R.id.edtOldPassword);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        dbManagerAPI = new DBManagerAPI(this);
        gson = new Gson();
    }
}
