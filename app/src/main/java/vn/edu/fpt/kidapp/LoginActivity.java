package vn.edu.fpt.kidapp;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import vn.edu.fpt.kidapp.database.DBManagerAPI;
import vn.edu.fpt.kidapp.receiver.UserReceiver;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword;
    private Button btnLogin, btnRegister;

    private UserReceiver userReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

    @Override
    protected void onStart() {
        super.onStart();
        userReceiver = new UserReceiver();
        registerReceiver(userReceiver, new IntentFilter(DBManagerAPI.ACTION_LOGIN));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(userReceiver);
    }

    private void initView() {
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
    }
}
