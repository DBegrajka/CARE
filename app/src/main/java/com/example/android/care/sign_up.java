package com.example.android.care;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class sign_up extends AppCompatActivity implements View.OnClickListener{

    private EditText etName, etEmail, etUserName, etPassword, etConfirmPassword;
    private Button reg;
    private TextView tvLogin;

    private DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        reg = (Button) findViewById(R.id.sign_up);
        tvLogin = (TextView) findViewById(R.id.back_to_login);
        etName = (EditText) findViewById(R.id.name);
        etEmail = (EditText) findViewById(R.id.email);
        etUserName = (EditText) findViewById(R.id.username);
        etPassword = (EditText) findViewById(R.id.password);
        etConfirmPassword = (EditText) findViewById(R.id.confirm_password);
        db = new DBHelper(this);
        reg.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
    }
/*
    public void signup_submit(View v){
        Intent intent = new Intent(this, login_activity.class);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
*/
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.sign_up:
                register();
                break;
            case R.id.back_to_login:
                startActivity(new Intent(sign_up.this, login_activity.class));
                break;
            default:
        }
    }

    private void register(){
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String username = etUserName.getText().toString();
        String password = etPassword.getText().toString();
        String confirm_password = etConfirmPassword.getText().toString();

        if (email.isEmpty() && name.isEmpty() && username.isEmpty() && password.isEmpty() && confirm_password.isEmpty()){
            displayToast("All the fields are not filled");
        }
/*        else if (password != confirm_password){
            displayToast("Password is not same as confirm password");
        }
  */      else {
            db.addUser(name, email, username, password, confirm_password);
            displayToast("User Registered");
            finish();
        }

    }

    private void displayToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
