package com.example.lock0134.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.content.Intent;
import android.view.View.OnClickListener;

public class LoginActivity extends Activity {

    protected static final String ACTIVITY_NAME = "login Activity";
    protected EditText loginText;
    protected Button button;
    protected Context context = LoginActivity.this;
    protected SharedPreferences sharedPref;
    protected SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginText = findViewById(R.id.loginEmail);
        Log.i("errorList", loginText.toString());
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPref.edit();

        loginText.setText(sharedPref.getString("DefaultEmail", null));

        button = findViewById(R.id.loginButton);

        Log.i(ACTIVITY_NAME, "In onCreate()");

    }

    protected void onResume(){
        super.onResume();

        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    protected void onStart() {
        super.onStart();
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("DefaultEmail", loginText.getText().toString());
                editor.commit();
                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }
}
