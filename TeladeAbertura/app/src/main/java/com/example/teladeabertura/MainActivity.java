package com.example.teladeabertura;

import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {

    private Button btnComecar;
    private MinhaThread minhaThread;
    private float alpha;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnComecar = (Button) findViewById(R.id.btn_comecar);

        // minhaThread = new MinhaThread();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                alpha = 0;
                btnComecar.setEnabled(false);

                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        String s = String.valueOf(alpha).substring(0, 3);
                        while(!s.equals("1.0")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    btnComecar.setAlpha(alpha);
                                    alpha += 0.1;
                                }
                            });
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            s = String.valueOf(alpha).substring(0, 3);
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run () {
                                btnComecar.setEnabled(true);
                            }
                        });
                    }
                }.start();

            }
        }, 3000);
        //minhaThread.start();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void telaDeAbertura(View view) {
        //if (minhaThread != null && !minhaThread.runFlag)
        Toast.makeText(this, R.string.tela_de_abertura_ok, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.teladeabertura/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.teladeabertura/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


    private class MinhaThread extends Thread {
        private boolean runFlag;


        @Override
        public synchronized void start() {
            super.start();
            runFlag = true;
            alpha = 0;
        }

        @Override
        public void run() {
            super.run();
        }

        @Override
        public void interrupt() {
            super.interrupt();
            runFlag = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (minhaThread != null && minhaThread.runFlag) {
            minhaThread.interrupt();
            minhaThread = null;
        }
    }
}
