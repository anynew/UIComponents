package com.ui.anynew.uicomponents;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.ui.anynew.uicomponents.ui.CircleColorBar;
import com.ui.anynew.uicomponents.ui.CircleProgress;
import com.ui.anynew.uicomponents.ui.ClockView;
import com.ui.anynew.uicomponents.ui.CreditSesameRingView;
import com.ui.anynew.uicomponents.ui.HeartView;
import com.ui.anynew.uicomponents.ui.TestView;
import com.ui.anynew.uicomponents.ui.TextView;

public class MainActivity extends AppCompatActivity {
    private CreditSesameRingView mCreditSesameRingView;
    private TextView testView;
    private HeartView heartView;
    private ClockView clockView;
    private CircleColorBar circleColorBar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_testview);
//        circleColorBar = (CircleColorBar)findViewById(R.id.mCircle);
//        circleColorBar.startAnim();
//        clockView = (ClockView)findViewById(R.id.clockView);
//        clockView.startAnim();
        setContentView(R.layout.activity_main);
//        mCreditSesameRingView = (CreditSesameRingView)findViewById(R.id.creditSesameRingView);
//        mCreditSesameRingView.setSesameValues(750);
//        testView = (TextView)findViewById(R.id.textView);
//        testView.startAnim();
//        heartView = (HeartView) findViewById(R.id.heartView);
//        heartView.startPathAnim();
    }
}
