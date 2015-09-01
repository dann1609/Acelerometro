package com.ingdanielpadilla.acelerometro;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private SensorManager mSensorManager;
    private Sensor mAcelSensor;
    private TextView aX,aY,aZ;
    private Button bstart,bstop;
    private boolean swstart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aX = (TextView) findViewById(R.id.aX);
        aY = (TextView) findViewById(R.id.aY);
        aZ = (TextView) findViewById(R.id.aZ);
        bstart = (Button) findViewById(R.id.bstart);
        bstop=(Button) findViewById(R.id.bstop);

        aX.setText("");
        aY.setText("");
        aZ.setText("");
        swstart = false;

        bstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!swstart) {
                    startCapture(MainActivity.this);
                }
            }
        });
        bstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (swstart){
                    stopCapturing();
                }
            }
        });

    }
    public void startCapture(Context context) {
        swstart = true;

        mSensorManager = (SensorManager) getSystemService(
                context.SENSOR_SERVICE);

        mAcelSensor = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mSensorManager.registerListener(this, mAcelSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stopCapturing() {
        swstart = false;
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this, mAcelSensor);
        } else {
            Toast.makeText(getApplicationContext(),
                    "mSensorManager null", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save variables on screen orientation change. Save the user's current game state
        savedInstanceState.putBoolean("swstart", swstart);
        savedInstanceState.putString("aX", aX.getText().toString());
        savedInstanceState.putString("aY", aY.getText().toString());
        savedInstanceState.putString("aZ", aZ.getText().toString());

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore variables on screen orientation change. Restore state members from saved instance
        swstart = savedInstanceState.getBoolean("swstart");
        aX.setText(savedInstanceState.getString("aX"));
        aY.setText(savedInstanceState.getString("aY"));
        aZ.setText(savedInstanceState.getString("aZ"));
        if (swstart) {
            startCapture(MainActivity.this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        aX.setText(event.values[0] + "");
        aY.setText(event.values[1] + "");
        aZ.setText(event.values[2] + "");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
