package com.example.fitnessapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.util.Clock;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.SENSOR_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class Home extends Fragment implements Dialog.OnInputSelected {

    private TextView textV1, textV2,calories;
    private double prevMag = 0;
    private Integer stepCount = 0;
    private Integer runCount = 0;
    private float Cal = 0;
    private String Mygoal;
    Context context;
    private Button button;

    public TextView mSetGoal, displayGoal;

    public Home() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home
                , container, false);
        textV1 = v.findViewById(R.id.tv);
        textV2 = v.findViewById(R.id.tv1);
        calories = v.findViewById(R.id.cal);
        mSetGoal = v.findViewById(R.id.setGoal);
        displayGoal = v.findViewById(R.id.goal);
        button = v.findViewById(R.id.reset);


        mSetGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog =  new Dialog();
                dialog.setTargetFragment(Home.this,1);
                dialog.show(getFragmentManager(),"Dialog");
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder dailog = new AlertDialog.Builder(getContext());
                dailog.setTitle("Reset");
                dailog.setMessage("Surely want to reset steps");
                dailog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        textV1.setText("0");
                        textV2.setText("0");
                        calories.setText("0.00");
                        stepCount = runCount = 0;
                        Cal = 0;

                    }
                });
                dailog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                });
                dailog.setCancelable(false);
                dailog.show();
            }
        });

        return v;
    }
     @Override
     public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);

         SensorManager sensorManager =(SensorManager)getContext().getSystemService(Context.SENSOR_SERVICE);
         Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

         SensorEventListener steps = new SensorEventListener() {

             @Override
             public void onSensorChanged(SensorEvent sensorEvent) {
                 if (sensorEvent != null) {
                     float x_acc = sensorEvent.values[0];
                     float y_acc = sensorEvent.values[1];
                     float z_acc = sensorEvent.values[2];

                     double magnitute = Math.sqrt(x_acc * x_acc + y_acc * y_acc + z_acc * z_acc);
                     double MagDelta = magnitute - prevMag;
                     prevMag = magnitute;

                     if (MagDelta > 6 && MagDelta <= 10) {
                         stepCount++;
                         Cal+=0.04;
                         Math.round(Cal);
                     }
                     else if(MagDelta > 10){
                         runCount++;
                         Cal+=0.20;
                         Math.round(Cal);
                     }
                     textV1.setText(stepCount.toString());
                     textV2.setText(runCount.toString());
                     calories.setText(String.valueOf(Cal));
                 }
             }

             @Override
             public void onAccuracyChanged(Sensor sensor, int i) {

             }
         };

         sensorManager.registerListener(steps, sensor, SensorManager.SENSOR_DELAY_NORMAL);

     }
      @Override
        public void onPause() {
            super.onPause();

            SharedPreferences preferences = this.getActivity().getPreferences(context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.putInt("stepCount", stepCount);
            editor.putInt("runCount", runCount);
            editor.putFloat("Calories",Cal);
            editor.putString(Mygoal,displayGoal.getText().toString());
            editor.apply();
        }

    @Override
        public void onStop() {
            super.onStop();

            SharedPreferences preferences = this.getActivity().getPreferences(context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.putInt("stepCount", stepCount);
            editor.putInt("runCount", runCount);
            editor.putFloat("Calories",Cal);
            editor.putString(Mygoal,displayGoal.getText().toString());
            editor.apply();
        }

    @Override
        public void onResume() {
            super.onResume();
            SharedPreferences preferences = this.getActivity().getPreferences(context.MODE_PRIVATE);
            stepCount = preferences.getInt("stepCount", 0);
            runCount = preferences.getInt("runCount",0);
            Cal = preferences.getFloat("Calories",0);
            Mygoal = preferences.getString(Mygoal,displayGoal.getText().toString());
        }

    @Override
    public void sendInput(String input) {
        displayGoal.setText(input);
        Mygoal = input;
    }
}

