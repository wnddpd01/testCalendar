package com.cal.uj.testcalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class IntroActivity extends Activity {
    private Handler intro_handler = new Handler();
    private Runnable intro_runnable = new Runnable() {
        @Override
        public void run() {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_intro);
    }

    @Override
    protected void onResume() {
        super.onResume();
        intro_handler.postDelayed(intro_runnable, 1300);
    }

    @Override
    protected void onPause() {
        super.onPause();
        intro_handler.removeCallbacks(intro_runnable);
    }
}
