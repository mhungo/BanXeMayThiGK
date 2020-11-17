package vn.edu.stu.thigkbansmartphone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText txtUsername, txtPassword;
    Button btnLogin;
    String UsernameAdmin = "root", PasswordAdmin = "admin";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    private void loginUser() {

        String username, password;
        username = txtUsername.getText().toString();
        password = txtPassword.getText().toString();

        if (TextUtils.isEmpty(txtUsername.getText().toString())) {
            Toast.makeText(this, "Username is empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(txtPassword.getText().toString())) {
            Toast.makeText(this, "Password is empty", Toast.LENGTH_SHORT).show();
        } else if (username.equals(UsernameAdmin) && password.equals(PasswordAdmin)) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Login is susccessfull", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Login Failq", Toast.LENGTH_SHORT).show();
        }
    }
}