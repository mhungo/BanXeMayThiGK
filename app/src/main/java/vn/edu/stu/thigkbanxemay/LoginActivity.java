package vn.edu.stu.thigkbanxemay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText txtUsername, txtPassword;
    Button btnLogin;
    String UsernameAdmin = "root", PasswordAdmin = "admin";
    CheckBox chkRemember;

    final String COOKIE = "COOKIE_DATA";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        chkRemember = findViewById(R.id.chkRemember);

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

            SharedPreferences preferences = getSharedPreferences(COOKIE, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("username");
            editor.remove("pass");
            editor.apply();

        } else if (TextUtils.isEmpty(txtPassword.getText().toString())) {
            Toast.makeText(this, "Password is empty", Toast.LENGTH_SHORT).show();

            SharedPreferences preferences = getSharedPreferences(COOKIE, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("username");
            editor.remove("pass");
            editor.apply();

        } else if (username.equals(UsernameAdmin) && password.equals(PasswordAdmin)) {
            if (chkRemember.isChecked()) {

                SharedPreferences preferences = getSharedPreferences(COOKIE, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("username", username);
                editor.putString("pass", password);
                editor.apply();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(this, R.string.textview_login_susscessfull, Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(this, R.string.textview_login_susscessfull, Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, R.string.textview_login_fail, Toast.LENGTH_SHORT).show();

            SharedPreferences preferences = getSharedPreferences(COOKIE, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("username");
            editor.remove("pass");
            editor.apply();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences preferences = getSharedPreferences(COOKIE, MODE_PRIVATE);
        String username = preferences.getString("username", "");
        String password = preferences.getString("pass", "");
        txtUsername.setText(username);
        txtPassword.setText(password);

    }
}