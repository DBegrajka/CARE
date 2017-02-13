package com.example.android.care;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class login_activity extends AppCompatActivity implements View.OnClickListener{

    private DBHelper db;
    private Button login, register;
    private EditText etUserName, etPassword;
    private Session session;
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        session  = new Session(this);
        db = new DBHelper(this);
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        etUserName = (EditText) findViewById(R.id.user_name);
        etPassword = (EditText) findViewById(R.id.pass);
        login.setOnClickListener(this);
        register.setOnClickListener(this);

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                goMainScreen();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), R.string.cancel_login, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), R.string.error_login, Toast.LENGTH_SHORT).show();
            }
        });


        if (session.loggedin()){
            startActivity(new Intent(login_activity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.login:
                login();
                break;
            case R.id.register:
                startActivity(new Intent(login_activity.this, sign_up.class));
                break;
            case R.id.login_button:
                goMainScreen();
                break;
            default:

        }
    }

    public void goMainScreen(){

       session.setLoggedin(true);
        startActivity(new Intent(login_activity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
/*
    public void login_click(View v){
        Intent intent = new Intent(this, MainActivity.class);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void signup(View v){
        Intent intent = new Intent(this, sign_up.class);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
*/
    private void login(){
        String username = etUserName.getText().toString();
        String password = etPassword.getText().toString();

        if(db.getUser(username, password)){
            session.setLoggedin(true);
            startActivity(new Intent(login_activity.this, MainActivity.class));
            finish();
        }
        else {
            Toast.makeText(getApplicationContext(), "Wrong UserName/Password", Toast.LENGTH_SHORT).show();
        }
    }

}
