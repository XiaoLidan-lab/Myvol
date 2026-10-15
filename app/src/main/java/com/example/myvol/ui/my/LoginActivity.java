package com.example.myvol.ui.my;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myvol.MainActivity;
import com.example.myvol.R;
import com.example.myvol.ui.DatabaseHelper;

import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btnLogin, btnRegister;
    private DatabaseHelper databaseHelper;

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        databaseHelper = new DatabaseHelper(this);
        sp = getSharedPreferences("user", MODE_PRIVATE);
        editor = sp.edit();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                List<Map<String, String>> list = databaseHelper.selectUser(username, password);
                if (list.size() > 0) {
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    editor.putString("username", username);
                    editor.putString("phone", list.get(0).get("phone"));
                    editor.commit();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    // 在这里处理登录成功后的逻辑
                } else {
                    Toast.makeText(LoginActivity.this, "登录失败，请检查用户名和密码", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

//                String username = etUsername.getText().toString().trim();
//                String password = etPassword.getText().toString().trim();
//
//                if (username.isEmpty() || password.isEmpty()) {
//                    Toast.makeText(LoginActivity.this, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
//                } else {
//                    if (databaseHelper.checkUsernameExist(username)) {
//                        Toast.makeText(LoginActivity.this, "该用户名已存在，请选择其他用户名", Toast.LENGTH_SHORT).show();
//                    } else {
//                        if (databaseHelper.insertUser(username, password,;)) {
//                            Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
//                            // 注册成功后的逻辑处理代码
//                        } else {
//                            Toast.makeText(LoginActivity.this, "注册失败，请重试", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
            }
        });
    }
}







