package com.example.android.care;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.android.care.R.id.start_button;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;

    private SensorManager sensorManager ;
    boolean activityRunning;
    private TextView count;
    boolean on = false;
    Float start;
    Float stop;
    Float dollar = 1.0f;
    private Session session;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        count = (TextView) findViewById(R.id.count);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        session = new Session(this);
        if (!session.loggedin()){
            logout();
        }
        btnLogout = (Button)findViewById(R.id.logout);
        btnLogout.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View v){
               logout();
           }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        //Handle action bar Item
        switch (item.getItemId()){
            case R.id.nav_history:
                startActivity(new  Intent(MainActivity.this, history_activity.class));
                break;

            case R.id.nav_charity:

                break;
            case R.id.nav_status:

                break;

            case R.id.nav_help:

                break;

            case R.id.nav_logout:
                logout();
               // return true;
            default:
        }
        return super.onOptionsItemSelected(item);
        }

    public void start(View view){

        Button btn = (Button)findViewById(start_button);
     //   btn.setImageResource(R.drawable.stop);
        TextView tv = (TextView) findViewById(R.id.count);
        TextView dollarstr = (TextView) findViewById(R.id.dollar);

        if (on == false) {
    // buttonn is pressed and set to start
            btn.setText("STOP");
            TextView editText = (TextView) findViewById(R.id.count);
            String editTextStr = editText.getText().toString();
            Log.d("myTag", editTextStr);
            start= Float.parseFloat(editTextStr);
            Log.d("Integer",  "value: " + start);
            tv.setText("0.0");
           // btn.setBackgroundResource(R.drawable.red_background);
            on = true;
            Log.d("myTag", "inside if");
            dollarstr.setText("0.0");
            dollar = 1.0f;


        } else  if (on == true){

            TextView editText = (TextView) findViewById(R.id.count);
            String editTextStr2 = editText.getText().toString();
            Log.d("myTag2", editTextStr2);
            stop= Float.parseFloat(editTextStr2);
            Log.d("Integer",  "value: " + stop);
            Float total_meters = stop - start;
            dollar = dollar * total_meters ;
            Log.d("Total distance",  "value: " + total_meters);
            Log.d("Dollar",  "value: " + dollar);
            String mytext=Float.toString(dollar);
            dollarstr.setText(mytext);
            btn.setText("START");
            Log.d("myTag", "inside else");
            on = false;
        }


    }

    protected void onResume(){
        super.onResume();
        activityRunning = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null){
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);

        } else {
            Toast.makeText(this, "count Sensor not available!", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onPause(){
            super.onPause();
        activityRunning = false;
    }

    @Override
    public void onSensorChanged(SensorEvent event){
             if(activityRunning){
                 count.setText("0.0");
                 count.setText(String.valueOf(event.values[0]));
             }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }

    private void logout(){
        session.setLoggedin(false);
        finish();
        startActivity(new Intent(MainActivity.this, login_activity.class));
    }

}
