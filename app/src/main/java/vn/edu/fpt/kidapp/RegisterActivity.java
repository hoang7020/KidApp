package vn.edu.fpt.kidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import vn.edu.fpt.kidapp.database.DBManagerAPI;

public class RegisterActivity extends AppCompatActivity {

    private final String TAG = RegisterActivity.class.getSimpleName();

    private EditText edtUsername, edtPassword, edtConfirmPassword, edtAddress;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                String confirmPassword = edtConfirmPassword.getText().toString();
                String address = edtAddress.getText().toString();
                if (password.equals(confirmPassword)) {
                    DBManagerAPI dba = new DBManagerAPI();
                    dba.register(username, password, address);
                } else {
                    Log.e(TAG, "onClick: " + "Confirm Password does not match!!!");
                    return;
                }
            }
        });
    }

    private void initView() {
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        edtAddress = findViewById(R.id.edtAddress);
        btnRegister = findViewById(R.id.btnRegister);
    }
}
