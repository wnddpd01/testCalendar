package com.cal.uj.testcalendar;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

public class ScheduleActivity extends AppCompatActivity {
    EditText editText;
    TextView tv_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        editText = findViewById(R.id.popup_editText);
        tv_date = findViewById(R.id.popup_title);
        WindowManager.LayoutParams lp = getWindow().getAttributes( ) ;
        WindowManager wm = ((WindowManager)getApplicationContext().getSystemService(getApplicationContext().WINDOW_SERVICE)) ;
        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);
        lp.width =  (int)( size.x * 0.9 );
        lp.height = (int)( size.y * 0.8);
        getWindow().setAttributes( lp ) ;

        Intent intent = getIntent();
        String date = intent.getStringExtra("date");
        String schedule = intent.getStringExtra("schedule");
        tv_date.setText(date);
        editText.setText(schedule);
    }

    public void mOnClose(View v){
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    public void mOnOk(View v){
        Intent intent = new Intent();
        intent.putExtra("re_schedule", editText.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }
}
