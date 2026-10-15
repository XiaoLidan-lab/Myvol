package com.example.myvol.ui.my;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myvol.R;
import com.example.myvol.ui.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {
    private EditText etUsername, etPassword,etphone;
    private Button btnRegister;
    private DatabaseHelper databaseHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        etphone = findViewById(R.id.etphone);

        databaseHelper = new DatabaseHelper(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String phone = etphone.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
                } else {
                    if (phone.isEmpty() ){

                        Toast.makeText(RegisterActivity.this, "请输入电话号码", Toast.LENGTH_SHORT).show();
                    }

                    if (databaseHelper.checkUsernameExist(username)) {
                        Toast.makeText(RegisterActivity.this, "该用户名已存在，请选择其他用户名", Toast.LENGTH_SHORT).show();
                    } else {
                        if (databaseHelper.insertUser(username, password,phone)) {
                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            finish();
                            // 注册成功后的逻辑处理代码
                        } else {
                            Toast.makeText(RegisterActivity.this, "注册失败，请重试", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
}
