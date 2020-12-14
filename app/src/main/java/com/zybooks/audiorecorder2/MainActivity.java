package com.zybooks.audiorecorder2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button bPlay;
    private Button bStop;
    private Button bRecord;
    //records from the microphone
    private MediaRecorder myMediaRecorder;
    private String myOutputFile;
    int REQUEST_WRITE_PERMISSION = 0;
    int REQUEST_RECORD_AUDIO_PERMISSION =1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bPlay = (Button) findViewById(R.id.button_Play);
        bStop = (Button) findViewById(R.id.button_stop);
        bRecord = (Button) findViewById(R.id.button_record);
        bStop.setEnabled(false);
        bPlay.setEnabled(false);
        //gets external file to save folder to
        myOutputFile = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/recording.3gp";
        myMediaRecorder = new MediaRecorder();
        //audio source file
        myMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        //extension on the folder
        myMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        //output format
        myMediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        //where is the file being saved too
        myMediaRecorder.setOutputFile(myOutputFile);

        bRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myMediaRecorder.stop();
                myMediaRecorder.release();
                myMediaRecorder = null;
                bRecord.setEnabled(true);
                bStop.setEnabled(false);
                bPlay.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Audio Recorder successfully",
                        Toast.LENGTH_LONG).show();
            }
        });

        bPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //plays media back
                MediaPlayer myMediaPlayer = new MediaPlayer();
                try {
                    myMediaPlayer.setDataSource(myOutputFile);
                    myMediaPlayer.prepare();
                    myMediaPlayer.start();
                    Toast.makeText(getApplicationContext(),"Playing Audio",
                            Toast.LENGTH_LONG).show();
                } catch (Exception e){
                    //make something
                }

            }
        });

    }
    private boolean hasWriteFilePermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
            return false;
        }
        return true;
    }
    private boolean hasRecordPermission (){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO_PERMISSION);
            return false;
        }
        return true;
    }
    private void startRecord(){
        if (hasRecordPermission()){
            String myOutputFile = getApplicationContext().getFilesDir().getAbsolutePath() + fileList();
                myMediaRecorder = new MediaRecorder();
            //audio source file
            myMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            //extension on the folder
            myMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            myMediaRecorder.setOutputFile(myOutputFile);
            Log.d("Start Recording()", "Start Recording: "+myOutputFile);
            myMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

            try {

                    myMediaRecorder.prepare();
                    myMediaRecorder.start();
                } catch(Exception e){
                Log.d("Start Recording", "Prepare failed");
                Log.e("Start Recording", e.toString());
            }


        }
    }
}