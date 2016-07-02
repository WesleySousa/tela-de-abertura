package com.example.teladeabertura;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnComecar;
    private MinhaThread minhaThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnComecar = (Button) findViewById(R.id.btn_comecar);

        minhaThread = new MinhaThread();
        minhaThread.start();

    }

    public void telaDeAbertura(View view) {
        if (minhaThread != null && !minhaThread.runFlag)
            Toast.makeText(this, R.string.tela_de_abertura_ok, Toast.LENGTH_SHORT).show();
    }


    private class MinhaThread extends Thread {
        private boolean runFlag;
        private float alpha;

        @Override
        public synchronized void start() {
            super.start();
            runFlag = true;
            alpha = 0;
        }

        @Override
        public void run() {
            super.run();
            if (runFlag) {
                String s = String.valueOf(alpha).substring(0,3);
                while (!s.equals("1.0")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btnComecar.setAlpha(alpha);
                            alpha += 0.1;
                        }
                    });
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    s = String.valueOf(alpha).substring(0,3);
                }


                runFlag = false;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnComecar.setEnabled(true);
                    }
                });
            }
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
