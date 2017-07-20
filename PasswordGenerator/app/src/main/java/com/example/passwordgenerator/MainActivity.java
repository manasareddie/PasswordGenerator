package com.example.passwordgenerator;

import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    ExecutorService threadPool;
    int tcount, x, acount,y;
    List<String> tpassList;
    List<String> tpassList2;
    final static String MY_LIST_KEY = "MyList";
    final static String MY_LIST_KEY_2 = "MyList2";
    ProgressDialog progressDialog;
     Handler handler;
    int curProgres=0;
    int count=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tpassList = new ArrayList<String>();
        tpassList2 = new ArrayList<String>();
        SeekBar threadcount = (SeekBar) findViewById(R.id.Thread_SeekBar);
        final TextView displayCount = (TextView) findViewById(R.id.ThreadCount_TexView);
        displayCount.setText("" + 1);
        threadcount.setMax(10);
        threadcount.setProgress(1);
        threadcount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                displayCount.setText("" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //-----------------
        SeekBar threadLength = (SeekBar) findViewById(R.id.ThreadLength_SeekBar);
        final TextView displayThreadLength = (TextView) findViewById(R.id.ThreadLengthDisplay_TextView);
        displayThreadLength.setText("" + 7);
        threadLength.setMax(23);
        threadLength.setProgress(7);
        threadLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                displayThreadLength.setText("" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //----------------------------
        SeekBar asyncPassword = (SeekBar) findViewById(R.id.AsyncPassword_SeekBar);
        final TextView asyncCount = (TextView) findViewById(R.id.AsyncCount_SeekBar);
        asyncCount.setText("" + 1);
        asyncPassword.setMax(10);
        asyncPassword.setProgress(1);
        asyncPassword.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                asyncCount.setText("" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //-----------------
        SeekBar asyncLength = (SeekBar) findViewById(R.id.AsyncLength_SeekBar);
        final TextView asyncLengthDisplay = (TextView) findViewById(R.id.AsyncLengthDisplay_TextView);
        asyncLengthDisplay.setText("" + 7);
        asyncLength.setMax(23);
        asyncLength.setProgress(7);
        asyncLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                asyncLengthDisplay.setText("" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //----------------------------
        threadPool = Executors.newFixedThreadPool(2);
        findViewById(R.id.GenerateButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                handler = new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        switch (msg.what) {
                            case DoWork.STATUS_START:
                                progressDialog = new ProgressDialog(MainActivity.this);
                                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                                progressDialog.setMax(tcount + acount);
                                progressDialog.setCancelable(false);
                                progressDialog.setMessage("Computing Progress");
                                progressDialog.show();
                                break;
                            case DoWork.STATUS_DONE:
                                if (progressDialog.getProgress() == (tcount + acount)) {
                                    progressDialog.dismiss();
                                    Intent i = new Intent(MainActivity.this, GeneratedPass.class);
                                    i.putExtra(MY_LIST_KEY, (Serializable) tpassList);
                                    i.putExtra(MY_LIST_KEY_2, (Serializable) tpassList2);
                                    startActivity(i);
                                }

                                break;
                            case DoWork.STATUS_STEP:
                                curProgres = progressDialog.getProgress();
                                progressDialog.setProgress(curProgres + 1);

                                break;
                        }

                        return false;
                    }
                });


                if (Integer.valueOf(displayCount.getText().toString()) < 1 || Integer.valueOf(displayThreadLength.getText().toString()) < 7 || Integer.valueOf(asyncCount.getText().toString()) < 1 || Integer.valueOf(asyncLengthDisplay.getText().toString()) < 7) {
                    Toast.makeText(getApplicationContext(), "min count is 1 and min length is 7", Toast.LENGTH_SHORT).show();
                } else {
                    tcount = Integer.valueOf(displayCount.getText().toString());
                    Log.d("demo", "--tcount--" + tcount);
                    x = Integer.parseInt(displayThreadLength.getText().toString());
                    acount = Integer.valueOf(asyncCount.getText().toString());
                    Log.d("demo", "--acount--" + acount);
                    y = Integer.valueOf(asyncLengthDisplay.getText().toString());


                    threadPool.execute(new DoWork());
                    Log.d("demo", "after thread pool");
                    new DoWork2().execute();
                    Log.d("demo", "after async");
                }
            }

        });
    }
//----------------threads DoWork Method-----------//
    class DoWork implements Runnable {

    static final int STATUS_START = 0x00;
    static final int STATUS_STEP = 0x01;
    static final int STATUS_DONE = 0x02;

    @Override
    public void run() {
        Looper.prepare();
        Message msg = new Message();
        msg.what = STATUS_START;
        handler.sendMessage(msg);

        for (int i = 0; i < tcount; i++) {
            String tpass = Util.getPassword(x);
            Log.d("demo","-"+tpass);
            tpassList.add(tpass);
            Message msg1 = new Message();
            msg1.what = STATUS_STEP;
            msg1.obj = i + 1;
            handler.sendMessage(msg1);
        }

        Message m = new Message();
        m.what = STATUS_DONE;
        Log.d("demo","done status");
        handler.sendMessage(m);


    }
}
//---------------------asyc
    class DoWork2 extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(progressDialog.getProgress()== (tcount+acount))
            {
                Log.d("demo","entering the loop");
                progressDialog.dismiss();
                Intent i = new Intent(MainActivity.this, GeneratedPass.class);
                i.putExtra(MY_LIST_KEY, (Serializable) tpassList);
                i.putExtra(MY_LIST_KEY_2, (Serializable) tpassList2);
                startActivity(i);
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            curProgres=progressDialog.getProgress();
            Log.d("demo","cur progress"+curProgres);
            progressDialog.setProgress(curProgres+1);
        }

        @Override
        protected Void doInBackground(Void... params) {
            for (int i = 0; i < tcount; i++) {
                String tpass = Util.getPassword(x);
                Log.d("demo","-"+tpass);
                tpassList2.add(tpass);
                publishProgress();
            }

            return  null;
        }
    }
}
